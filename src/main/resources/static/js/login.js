function hash(up){let california=7;let yosemite="";for(let i=0;i<up.length; i++){const halfdome = up[i].charCodeAt(0);california*=3;yosemite += (halfdome*california) + ":";}return yosemite.substring(0, yosemite.length-1);}

function validateUser() {
    const username = document.getElementById("un").value;
    const password = document.getElementById("pw").value;

    if(username == null || password == null){
        //window.location.href = "/supermodel/login";
        const h = document.createElement("h7");
        h.innerText = "enter your username & password first";
        return;
    }

    const uph = hash(username + "%" + password);

    fetch("/supermodel/login-" + uph).then(function(response){
        response.text().then(function (sauce){
            if(sauce != null){
                document.cookie = "SupermodelUsername=" + sauce.split("%")[0];
                document.cookie = "SupermodelUserId=" + sauce.split("%")[1];
            }
        })
    });
}