
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
        console.log(json);

        var searchContainer = document.getElementById("searchItems");
        var searchItem = document.getElementById("searchItem");

searchContainer.innerHTML = "";

        json.productList.forEach(product => {
            let product_clone = searchItem.cloneNode(true)

            product_clone.querySelector("#button").setAttribute("href", "singleProductView.html?pid=" + product.id);
            product_clone.querySelector("#image").setAttribute("src", "product-images/" + product.id + "/image1.png");
            product_clone.querySelector("#title").innerHTML = product.name;
            product_clone.querySelector("#brand").innerHTML = product.brand.name;
            product_clone.querySelector("#button").innerHTML="Explore";
            product_clone.querySelector("#button").className="btn btn-dark";
            product_clone.querySelector("#holder").className="card";



            product_clone.querySelector("#price").innerHTML ="Rs "+ new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(product.price);

            searchContainer.appendChild(product_clone);


        })

    }
}

