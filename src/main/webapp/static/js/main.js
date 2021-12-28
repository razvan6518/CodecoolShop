let content = document.querySelector("#products");
// const categories = document.querySelector("#categories");
// const suppliers = document.querySelector("#suppliers");

// for (let i=0; i<categories.children.length; i++){
//     categories.children[i].addEventListener("click", async evt => {
//         const values = await getValues(categories.children[i].getAttribute("value"), "products");
//         await addCards(values, categories.children[i].innerText);
//     })
// }
//
// for (let i=0; i<suppliers.children.length; i++){
//     suppliers.children[i].addEventListener("click", async evt => {
//         const values = await getValues(suppliers.children[i].getAttribute("value"), "suppliers");
//         await addCards(values, suppliers.children[i].innerText);
//     })
// }

// async function getValues(id, endpoint) {
//     const response = await fetch("/" + endpoint + "?id=" + id);
//     const values = await response.json();
//     return values;
// }


makeFilter();


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
                <img class="" src="/static/img/product_${prod['id']}.jpg" alt="" width="200" height="200"/>
                <div class="card-header">
                    <h4 class="card-title">${prod['name']}</h4>
                    <p class="card-text">${prod['description']}</p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead">${prod['defaultPrice']} ${prod['currencyString']}</p>
                    </div>
                    <div class="card-text">
                    <button class="btn btn-success" type="button" onclick="addToCart(${prod['id']})">Add to cart</button>
                    </div>
                </div>
            </div>`;
    return card;
}

function addToCart(id) {
    let itemsInCartCounter = document.querySelector("#lblCartCount");
    let newNrOfItemsInCart = parseInt(itemsInCartCounter.innerText)+1;
    document.querySelector("#lblCartCount").innerHTML = ` ${newNrOfItemsInCart} `;
    document.getElementById("lblCartCount").style.display = "inline-block";
    start();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", `/add?id=${id}`);
    xhttp.setRequestHeader("Content-type", "");
    xhttp.send();
}

const leftFilters = document.querySelector("#leftMenuBody");
leftFilters.innerHTML += `<div id="categoryFilter"></div>`;

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
    let categoryFilter = document.querySelector("#categoryFilter");
    console.log(categoryFilter);
    categoryFilter.innerHTML += `<div id="categoryFilters" class="text-white"><h3>Categories:</h3></div>`
    categories.forEach(category => {
        document.querySelector("#categoryFilters").innerHTML +=
            `<input type="checkbox" id="category_${category['id']}" name="category" value="${category['id']}" oninput="updatePage()">
             <label for="${category['name']}">${category['name']}</label><br>`;
    })
    categoryFilter.innerHTML += `<div id="supplierFilters" class="text-white"><hr><h3>Suppliers:</h3></div>`
    suppliers.forEach(supplier => {
        document.querySelector("#supplierFilters").innerHTML +=
            `<input type="checkbox" id="supplier_${supplier['id']}" name="supplier" value="${supplier['id']}" oninput="updatePage()">
             <label for="${supplier['name']}">${supplier['name']}</label><br>`;
    })
    categoryFilter.innerHTML += `<div id="priceFilters" class="text-white"><hr><h3>Max price: <b id="priceMax"></b></h3></div>`
    document.querySelector("#priceFilters").innerHTML += `
        <input type="range" name="price-max" id="price-max" value="1000" min="0" max="1000" onchange="updatePage()" oninput="updateSelectedMaxPrice()">`;
}

async function updatePage(){
    let filters = "";
    let checkBoxes = document.querySelectorAll("#categoryFilter input");
    checkBoxes.forEach(checkBox => {
        if (checkBox.checked)
            filters += checkBox.id+","
    })
    let maxPrice = document.querySelector("#price-max").value;
    document.querySelector("#priceMax").innerHTML = maxPrice;
    filters = filters.substr(0, filters.length-1) + "&maxPrice=" + maxPrice;
    const filteredValues = await getFilteredProducts(filters);
    await addCards(filteredValues, "Filtered");
}

function updateSelectedMaxPrice(){
    let maxPrice = document.querySelector("#price-max").value;
    document.querySelector("#priceMax").innerHTML = maxPrice;
}

function w3_open() {
    document.getElementById("main").style.marginLeft = "15%";
    document.getElementById("leftMenu").style.width = "15%";
    document.getElementById("leftMenu").style.display = "block";
    document.getElementById("openFilterMenu").style.display = 'none';
}
function w3_close() {
    document.getElementById("main").style.marginLeft = "0%";
    document.getElementById("leftMenu").style.display = "none";
    document.getElementById("openFilterMenu").style.display = "inline-block";
}

function w2_open() {
    document.getElementById("main").style.marginRight = "15%";
    document.getElementById("rightMenu").style.width = "15%";
    document.getElementById("rightMenu").style.display = "block";
    document.getElementById("openCartMenu").style.display = 'none';
    document.getElementById("lblCartCount").style.display = 'none';
}
function w2_close() {
    document.getElementById("main").style.marginRight = "0%";
    document.getElementById("rightMenu").style.display = "none";
    document.getElementById("openCartMenu").style.display = "inline-block";
    document.getElementById("lblCartCount").style.display = "inline-block";
}

function start() {
    document.getElementById('lblCartCount').classList.remove("count-items-anim");
    document.getElementById('lblCartCount').offsetWidth;
    document.getElementById('lblCartCount').classList.add("count-items-anim");
    // document.getElementById('lblCartCount').style.animation = "addItemAnimation forward";
    // document.getElementById('earthlogo').style.animation = "anim2 2s 2s forward";
}