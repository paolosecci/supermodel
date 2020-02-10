function addContinueBrowsingButton() {

    let button = document.getElementById("runwayButton");
    button.innerText = "Continue Browsing";
    button.onclick = function(){window.location.href = "/supermodel/browse";}
}

function addToRunway(){

    const name = document.getElementById("supermodelName").innerText;
    const url = document.getElementById("supermodelUrl").src;
    const id = document.getElementById("smId").getAttribute("src");

    document.cookie = "RunwaySupermodel" + id + "=" + name + "," + url;

    document.getElementById("logSpot").innerText = "added.";

    addContinueBrowsingButton();
}