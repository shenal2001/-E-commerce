async function loadProductFeatures() {
    const response = await fetch(
            "loadProductfeatures"
            )
    if (response.ok) {

        const json = await response.json();
        const categories = json.categories;
        const colors = json.colors;
        const brands = json.brands;
        const sub_categories = json.sub_categories;
        const sizes = json.sizes;
        const category_list = document.getElementById("categorySelect");
        categories.forEach(category => {
            let optionTag = document.createElement("option");
            optionTag.value = category.id;
            optionTag.innerHTML = category.name
            category_list.appendChild(optionTag);
        })

        const sub_category_list = document.getElementById("subCategorySelect");
        sub_categories.forEach(sub_category => {
            let optionTag = document.createElement("option");
            optionTag.value = sub_category.id;
            optionTag.innerHTML = sub_category.name
            sub_category_list.appendChild(optionTag);
        })

    

        const brand_list = document.getElementById("brandSelect");
        brands.forEach(brand => {
            let optionTag = document.createElement("option");
            optionTag.value = brand.id;
            optionTag.innerHTML = brand.name
            brand_list.appendChild(optionTag);
        })

        const size_list = document.getElementById("sizeSelect");
        sizes.forEach(size => {
            let optionTag = document.createElement("option");
            optionTag.value = size.id;
            optionTag.innerHTML = size.name
            size_list.appendChild(optionTag);
        })

    const color_list = document.getElementById("colorSelect");
        colors.forEach(color => {
            let optionTag = document.createElement("option");
            optionTag.value = color.id;
            optionTag.innerHTML = color.name
            color_list.appendChild(optionTag);
        })

    }
}
