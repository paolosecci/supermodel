//get data from thymeleaf var (imported in html <script th:inline>)
var data = JSON.parse(dataStr);

//create location for supermodel imgs
var modelsLoc = document.getElementById("modelsLocation");

for(var i = 0; i < data.length; i++){

    //get data for this sm
    var sm = data[i];

    //build img container a
    var a = document.createElement("a");
    a.href = "/supermodel/" + sm['name'];
    a.height = 200;

    //build img element
    var img = document.createElement("IMG");
    img.src = sm['imgUrl'];
    img.height = 200;

    //add img to a & a to div
    a.appendChild(img);
    modelsLoc.appendChild(a);
}