// //get data from thymeleaf var (imported in html <script th:inline>)
// var data = JSON.parse(dataStr);

function renderData(data){
    //create location for supermodel imgs
    const modelsLoc = document.getElementById("modelsLocation");

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
}

//get data from api endpoint
var data;
fetch("/supermodel/api/supermodels").then((re) => {return re.json();}).then(function(re_json_promise){
    renderData(re_json_promise);
}).catch((err)=>{console.log(err)});

