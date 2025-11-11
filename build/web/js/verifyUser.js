async function verifyUser() {
    const code = document.getElementById("verifyUser").value;
        const email = document.getElementById("email").value;


    const verify = {
        verification_code: code,
        email:email
    }

    const response = await fetch(
            "verifyUser",
            {
                method: "POST",
                body: JSON.stringify(verify),
                headers: {
                    "Content-Type": "application/json"
                }
            }

    )
    
    if(response.ok){
        const json=await response.json();
        if(json.content=="Success"){
            window.location="login.html"
        }else{
            document.getElementById("message").innerHTML=json.content;
        }
    }


}
