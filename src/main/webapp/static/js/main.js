let content = document.querySelector("#products");
const categories = document.querySelector("#categories");
const suppliers = document.querySelector("#suppliers");

for (let i=0; i<categories.children.length; i++){
    categories.children[i].addEventListener("click", async evt => {
        const values = await getValues(categories.children[i].getAttribute("value"), "products");
        await addCards(values, categories.children[i].innerText);
    })
}

for (let i=0; i<suppliers.children.length; i++){
    suppliers.children[i].addEventListener("click", async evt => {
        const values = await getValues(suppliers.children[i].getAttribute("value"), "suppliers");
        await addCards(values, suppliers.children[i].innerText);
    })
}

async function getValues(id, endpoint) {
    const response = await fetch("/" + endpoint + "?id=" + id);
    const values = await response.json();
    return values;
}

async function addCards(values, title) {
    document.querySelector("#title").innerHTML = `<strong>${title}</strong>`;
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

function openLeftMenu() {
    document.getElementById("leftMenu").style.display = "block";
}
function closeLeftMenu() {
    document.getElementById("leftMenu").style.display = "none";
}
function openRightMenu() {
    document.getElementById("rightMenu").style.display = "block";
}

function closeRightMenu() {
    document.getElementById("rightMenu").style.display = "none";
}

const leftFilters = document.querySelector("#leftMenu");
leftFilters.innerHTML += `<div id="categoryFilter"></div>`;

makeFilter();

async function getCategories() {
    const response = await fetch("/categories");
    const values = await response.json();
    return values;
}

async function getSuppliers() {
    const response = await fetch("/suppliers");
    const values = await response.json();
    return values;
}

async function getFilteredProducts(filterBy) {
    const response = await fetch("/filter_products?by="+filterBy);
    const values = await response.json();
    return values;
}

async function makeFilter(){
    const categories = await getCategories();
    const suppliers = await getSuppliers();
    categories.forEach(category => {
        document.querySelector("#categoryFilter").innerHTML +=
            `<input type="checkbox" id="category_${category['id']}" name="category" value="${category['id']}" oninput="updatePage()">
             <label for="${category['name']}">${category['name']}</label><br>`;
    })
    suppliers.forEach(supplier => {
        document.querySelector("#categoryFilter").innerHTML +=
            `<input type="checkbox" id="supplier_${supplier['id']}" name="supplier" value="${supplier['id']}" oninput="updatePage()">
             <label for="${supplier['name']}">${supplier['name']}</label><br>`;
    })
}

async function updatePage(){
    let filters = "";
    let checkBoxes = document.querySelectorAll("#categoryFilter input");
    checkBoxes.forEach(checkBox => {
        if (checkBox.checked)
            filters += checkBox.id+","
    })
    const filteredValues = await getFilteredProducts(filters.substr(0, filters.length-1));
    await addCards(filteredValues, "Filtered");
}