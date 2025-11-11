async function signIn() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const user_dto = {
        email: email,
        password: password
    }

    const response = await fetch(
            "signin",
            {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers:{
                    "Content-Type" :"application/json"
                }
            }
    )
    
    if(response.ok){
        const json=await response.json();
        document.getElementById("warningLogin").innerHTML=json.content;
        
        if(json.content=="Login Success"){
            window.location="index.html";
        }else if(json.content=="Not a Verified User"){
            window.location="verification.html"
        }
        
    }
}





