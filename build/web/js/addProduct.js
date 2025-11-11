async function addProduct() {
    const productName = document.getElementById("productName");
    const brandSelect = document.getElementById("brandSelect");
    const categorySelect = document.getElementById("categorySelect");
    const colorSelect = document.getElementById("colorSelect");
    const subCategorySelect = document.getElementById("subCategorySelect");
    const sizeSelect = document.getElementById("sizeSelect");
    const price = document.getElementById("price");
    const quantity = document.getElementById("qty");
    const description = document.getElementById("description");
    const image1 = document.getElementById("image1");
    const image2 = document.getElementById("image2");
    const image3 = document.getElementById("image3");


    const data = new FormData();

    data.append("productName", productName.value);
    data.append("brandSelect", brandSelect.value);
    data.append("categorySelect", categorySelect.value);
    data.append("colorSelect", colorSelect.value);
    data.append("subCategorySelect", subCategorySelect.value);
    data.append("sizeSelect", sizeSelect.value);
    data.append("description", description.value);
    data.append("price", price.value);
    data.append("quantity", quantity.value);
    data.append("image1", image1.files[0]);
    data.append("image2", image2.files[0]);
    data.append("image3", image3.files[0]);

    const response = await fetch(
            "addProduct",
            {
                method: "POST",
                body: data
            }
    );

    if (response.ok) {
        const json = await response.json();

        const message = document.getElementById("message");

        message.innerHTML = json.content;

        if (json.content == "Success") {
            location.reload();
//            categorySelectTag.value = 0;
//            modelSelectTag.value = 0;
//            titleTag.value = "";
//            descriptionTag.value = "";
//            storageSelectTag.value = 0;
//            colorSelectTag.value = 0;
//            conditionSelectTag.value = 0;
//            priceTag.value = "";
//            quantityTag.value = 1;
//            image1Tag.value = null;
//            image2Tag.value = null;
//            image3Tag.value = null;



        } else {

        }

    }
}




