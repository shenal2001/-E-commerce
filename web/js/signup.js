async function signup() {
    const first_name = document.getElementById("first_name").value;
    const last_name = document.getElementById("last_name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

const user={
    firstName:first_name,
    lastName:last_name,
    email:email,
    password:password
}

   const response=await fetch(
       "signUp",
       {
           method:"POST",
           body:JSON.stringify(user),
           headers:{
               "Content_Type":"application/json"
           }
           
       }
   )
   
   if(response.ok){
       const json=await response.json();
       document.getElementById("errorMessageSignUp").innerHTML=json.content;
       
       if(json.content=="Registration Success. Please Check Your email to verify your account"){
           window.location="verification.html"
       }
   }

}

