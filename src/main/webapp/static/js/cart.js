let total = document.querySelector("#total");
let content = document.querySelector('#products');

start();

async function start(){
    const values = await getValues();
    await addCards(values);
    let totalPrice = 0;
    for (let i=0; i<values.length; i++)
        totalPrice += values[i]['item']['defaultPrice'] * values[i]['quantity'];
    total.innerHTML = `<h2>Total price: ${totalPrice}</h2>`;
}

async function getValues() {
    const response = await fetch("/get_cart");
    const values = await response.json();
    return values;
}

async function addCards(values) {
    content.innerHTML = "";
    values.forEach(value => {
        content.appendChild(createCard(value['item'], value['quantity']));
    })
}

function createCard(prod, quantity){
    const card = document.createElement('div');
    let subtotal = prod['defaultPrice'] * quantity;
    card.className = "col col-sm-12 col-md-6 col-lg-4";
    card.innerHTML = `
            <div class="card">
                <img class="" src="/static/img/product_${prod['id']}.jpg"/>
                <div class="card-header">
                    <h4 class="card-title">${prod['name']}</h4>
                    <p class="card-text">${prod['description']}</p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead">${prod['defaultPrice']} ${prod['currencyString']}</p>
                    </div>
                    <div class="card-text">
                        <a href="/change_cart?id=${prod['id']}&quantity=6">aaaaa</a>
                        <form action="/change_cart" method="get">
                            <label for="quantity">quantity</label>
                            <input type="hidden" id="custId" name="id" value="${prod['id']}">
                            <input name="quantity" class="form-control" type="text" value="${quantity}"/>
                            <input type="submit" value="Change">
                        </form>
                        <strong>subtotal price: ${subtotal}</strong>
                    </div>
                </div>
            </div>`;
    return card;
}