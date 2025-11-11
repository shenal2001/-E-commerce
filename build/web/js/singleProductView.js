async function loadProduct() {
    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("pid")) {
        const pid = parameters.get("pid");
        const response = await fetch("singleProduct?pid=" + pid);

        if (response.ok) {

            const json = await response.json();

            document.getElementById("name").innerHTML = json.product.name;
            document.getElementById("brand").innerHTML = "Brand Name : " + json.product.brand.name;
            document.getElementById("category").innerHTML = "Category : " + json.product.category.name;
            document.getElementById("color").innerHTML = "Color : " + json.product.color.name;
            document.getElementById("size").innerHTML = "Size : " + json.product.size.name;
            document.getElementById("price").innerHTML = "Price : Rs. " + json.product.price + ".00";
            document.getElementById("quantity").innerHTML = json.product.qty + " Stocks are available";
            document.getElementById("img1").setAttribute("src", "product-images/" + json.product.id + "/image1.png");
            document.getElementById("img2").setAttribute("src", "product-images/" + json.product.id + "/image2.png");
            document.getElementById("img3").setAttribute("src", "product-images/" + json.product.id + "/image3.png");

            document.getElementById("productToCart").setAttribute("onclick", "addToCart(" + json.product.id + ")");
            document.getElementById("checkout").href = "checkout.html?id=" + json.product.id;

        }


    }

}

let currentSlide = 0;

function moveSlide(direction) {
    const slides = document.querySelectorAll('.slide');
    const slider = document.querySelector('.slider');

    currentSlide += direction;

    if (currentSlide < 0) {
        currentSlide = slides.length - 1;
    } else if (currentSlide >= slides.length) {
        currentSlide = 0;
    }

    const offset = -currentSlide * 100 / slides.length;
    slider.style.transform = `translateX(${offset}%)`;
}


async function searchProducts() {
    const category_name = document.getElementById("categorySelect").value;
    const brand_name = document.getElementById("brandSelect").value;
    const size = document.getElementById("sizeSelect").value;
    const sub_category = document.getElementById("subCategorySelect").value;


    const data = {
        category_name: category_name,
        brand_name: brand_name,
        size: size,
        sub_category: sub_category,
    };

    const response = await fetch("search", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    });

    if (response.ok) {
        const json = await response.json();

        var searchContainer = document.getElementById("searchItems");
        var searchItem = document.getElementById("searchItem");

        searchContainer.innerHTML = "";

        json.productList.forEach(product => {
            let product_clone = searchItem.cloneNode(true)

            product_clone.querySelector("#button").setAttribute("href", "singleProductView.html?pid=" + product.id);
            product_clone.querySelector("#image").setAttribute("src", "product-images/" + product.id + "/image1.png");
            product_clone.querySelector("#title").innerHTML = product.name;
            product_clone.querySelector("#brand").innerHTML = product.brand.name;
            product_clone.querySelector("#button").innerHTML = "Explore";
            product_clone.querySelector("#button").className = "btn btn-dark";
            product_clone.querySelector("#holder").className = "card";



            product_clone.querySelector("#price").innerHTML = "Rs " + new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(product.price);

            searchContainer.appendChild(product_clone);


        })

    }
}


async function addToCart(id) {

    var pid = id
    const qty = document.getElementById("addedQty").value

    const response = await fetch("AddToCart?pid=" + pid + "&qty=" + qty);


    if (response.ok) {
        const json = await response.json();
        if (json.success) {
           alert("Product Added to the cart")
        } else {
    alert("Something went wrong")
        }
    } else {
        popup:error({
            message: "Unable to process your request"
        });
    }

}

