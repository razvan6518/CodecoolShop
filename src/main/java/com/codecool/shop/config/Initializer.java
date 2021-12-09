package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier nokia = new Supplier("Nokia", "Phones");
        supplierDataStore.add(nokia);
        Supplier samsung = new Supplier("Samsung", "Phones");
        supplierDataStore.add(samsung);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phones = new ProductCategory("Phones", "Hardware", "phone description");
        productCategoryDataStore.add(phones);
        ProductCategory headphones = new ProductCategory("Headphones", "Hardware", "Headphones description");
        productCategoryDataStore.add(headphones);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Nokia 3160", new BigDecimal("101"), "USD", "Greatest phone on market.", phones, nokia));
        productDataStore.add(new Product("Logitech G432", new BigDecimal("78"), "USD", "Wired Gaming Headset 7.1 Surround Sound, DTS Headphone: X 2.0, boom microphone with flip mute, ear pads with artificial leather, PC / Mac / Xbox One / PS4 / Nintendo Switch, Black", headphones, amazon));
        productDataStore.add(new Product("Sennheiser PC 8 USB Headset", new BigDecimal("26"), "USD", "Noise cancelling clarity, due to a noise cancelling microphone, your voice is easily understood without you having to shout ", headphones, amazon));
        productDataStore.add(new Product("Logitech G PRO X", new BigDecimal("78"), "USD", "gaming headset (with Blue VO! CE, DTS Headphone: X 7.1 and PRO-G 50 mm speakers, for PC, PS4, Switch, Xbox One, VR) black", headphones, amazon));
        productDataStore.add(new Product("Galaxy Z Fold3 5G", new BigDecimal("899"), "USD", "Experience all screen, all the time with an expansive folding display and our first-ever Under Display Camera.", phones, samsung));
    }
}
