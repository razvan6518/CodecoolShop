let content = document.querySelector("#products");
const categories = document.querySelector("#categories");
const suppliers = document.querySelector("#suppliers");

for (let i=0; i<categories.children.length; i++){
    categories.children[i].addEventListener("click", async evt => {
        const values = await getValues(categories.children[i].value, "products");
        await addCards(values);
    })
}

for (let i=0; i<suppliers.children.length; i++){
    suppliers.children[i].addEventListener("click", async evt => {
        const values = await getValues(suppliers.children[i].value, "suppliers");
        await addCards(values);
    })
}

async function getValues(id, endpoint) {
    const response = await fetch("/" + endpoint + "?id=" + id);
    const values = await response.json();
    return values;
}

async function addCards(values) {
    content.innerHTML = "";
    values.forEach(value => {
        content.appendChild(createCard(value));
    })
}

function createCard(prod){
    const card = document.createElement('div');
    card.className = "col col-sm-12 col-md-6 col-lg-4";
    card.innerHTML = `
            <div class="card">
                <img class="" src="/static/img/product_${prod['id']}.jpg" alt="" />
                <div class="card-header">
                    <h4 class="card-title">${prod['name']}</h4>
                    <p class="card-text">${prod['description']}</p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead">${prod['defaultPrice']} ${prod['currencyString']}</p>
                    </div>
                    <div class="card-text">
                        <a class="btn btn-success" href="/add?id=${prod['id']}">Add to cart</a>
                    </div>
                </div>
            </div>`;
    return card;
}
