function renderData(data){
    const modelsLoc = document.getElementById("modelsLocation");

    for(let i = 0; i < data.length; i++){

        const sm = data[i];

        const a = document.createElement("a");
        a.href = "/supermodel/" + sm['name'];
        a.height = 200;

        const img = document.createElement("IMG");
        img.src = sm['imgUrl'];
        img.height = 200;

        a.appendChild(img);
        modelsLoc.appendChild(a);
    }
}
function getSupermodels(){
    return fetch("/supermodel/api/supermodels")
        .then(response => response.json())
        .catch(err => console.log(err));
}
import * as cookieHandler from "./cookieHandler.js";

let username = cookieHandler.getCookieByName("SupermodelUsername").split("=")[1];

document.getElementById("browseCatalogTitle").innerText = username;

getSupermodels().then(supermodels => renderData(supermodels));