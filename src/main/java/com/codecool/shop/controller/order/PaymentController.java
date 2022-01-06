package com.codecool.shop.controller.order;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;
import com.codecool.shop.util.StripeApi;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

// TODO: Refactor !!!

@WebServlet(name = "PaymentController", urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        User user = (User) request.getSession().getAttribute("user");
        List<PaymentMethod> paymentMethods = UserDaoJdbc.getInstance().getPaymentMethods(user.getId());
        BigDecimal totalPrice = Order.getInstance().getTotalCartPrice();

        context.setVariable("payment_methods", paymentMethods);
        context.setVariable("total_price", totalPrice);

        PaymentIntent paymentIntent = StripeApi.createPaymentIntent(
                totalPrice.setScale(0, RoundingMode.UP).intValueExact(),
                "USD",
                user.getCustomerId());
        request.getSession().setAttribute("paymentIntent", paymentIntent);

        engine.process("payment/payment.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        BigDecimal totalPrice = Order.getInstance().getTotalCartPrice();

        context.setVariable("total_price", totalPrice);

        User user = (User) request.getSession().getAttribute("user");
        PaymentMethod paymentMethod = null;
        List<PaymentMethod> paymentMethods = UserDaoJdbc.getInstance().getPaymentMethods(user.getId());
        if (paymentMethods.size() == 0){
            String paymentMethodId = StripeApi.createPaymentMethod(
                    request.getParameter("card_number"),
                    Integer.parseInt(request.getParameter("exp_month")),
                    Integer.parseInt(request.getParameter("exp_year")),
                    request.getParameter("cvc")
            );
            String updatedPaymentMethodId = StripeApi.attachesPaymentMethodToCustomer(user.getCustomerId(), paymentMethodId).getId();
            // TODO remove Dao
            UserDaoJdbc.getInstance().addPaymentMethod(user.getId(), updatedPaymentMethodId);
            paymentMethods = UserDaoJdbc.getInstance().getPaymentMethods(user.getId());
        }else{
            paymentMethod = paymentMethods.stream().filter(payMethod -> payMethod.getId().equals(request.getParameter("card_id"))).findFirst().get();
        }

        context.setVariable("payment_methods", paymentMethods);

        if (paymentMethod != null){
            HttpSession httpSession = request.getSession();
            PaymentIntent paymentIntent = (PaymentIntent) httpSession.getAttribute("paymentIntent");
            PaymentIntent updatedPaymentIntent = StripeApi.tryToConfirmPayment(paymentIntent, paymentMethod.getId());
            if (updatedPaymentIntent.getStatus().equals("succeeded")){
                engine.process("payment/succeeded.html", context, response.getWriter());
            }else{
                engine.process("payment/payment.html", context, response.getWriter());
            }
        }else{
            engine.process("payment/payment.html", context, response.getWriter());
        }
    }
}
