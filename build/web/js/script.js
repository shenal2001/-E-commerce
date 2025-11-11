async function exploreAll(){
    alert()
    const response=await fetch(
            "selectAll"
            
            )
    
    if(response.ok){
        const json=await response.json;
        console.log(json)
    }
    
}

