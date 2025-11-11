async function loadCartItems() {
    const response = await fetch("cart");


    if (response.ok) {
        const json = await response.json();

        console.log(json.length)
        if (json.length != 0) {

            let cartItemContainer = document.getElementById("cartItemContainer");
            let cartItemRow = document.getElementById("cartItemRow");
            let cartRight = document.getElementById("total_cart");

            cartItemContainer.innerHTML = "";

            let totalQty = 0;
            let total = 0;

            json.forEach(item => {


                let itemSubtotal = item.product.price * item.qty;
                total += itemSubtotal;

                totalQty += item.qty;

                let cartItemRowClone = cartItemRow.cloneNode(true);
                cartItemRowClone.querySelector("#checkoutButton").href = "single-product.html?pid=" + item.product.id;
                cartItemRowClone.querySelector("#image").src = "product-images/" + item.product.id + "/image1.png";
                cartItemRowClone.querySelector("#title").innerHTML = "Title : " + item.product.name;
                cartItemRowClone.querySelector("#price").innerHTML = "Price : " + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(item.product.price);

                cartItemRowClone.querySelector("#qty").innerHTML = "| Qty : " + item.qty;
                cartItemRowClone.querySelector("#subTotal").innerHTML = "| Sub Total : " + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format((itemSubtotal));
                cartItemContainer.appendChild(cartItemRowClone);
                
            });

            document.getElementById("#total_qty").innerHTML = "Cart Total: " + totalQty;
            document.getElementById("#total_price").innerHTML = "Number of Items : " + new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format((total));
        }

    } else {
        popup.error({
            message: "Unable to process your request"
        });
    }

}