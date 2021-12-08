// console.log("js");
let productsShow = document.querySelector("#products");
const categories = document.querySelector("#categories");
for (let i=0; i<categories.children.length; i++){
    categories.children[i].addEventListener("click", evt => {
        console.log(categories.children[i]);
        productsShow.innerHTML = `aaaaa`;
    })
}
