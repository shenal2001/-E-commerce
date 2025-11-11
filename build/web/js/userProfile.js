async function loadData(){
      const response = await fetch("cart");


    if (response.ok) {
        const json = await response.json();
    }
}