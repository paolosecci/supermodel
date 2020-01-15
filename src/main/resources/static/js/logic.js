// //get data from thymeleaf var (imported in html <script th:inline>)
// var data = JSON.parse(dataStr);

function renderData(data){
    //create location for supermodel imgs
    const modelsLoc = document.getElementById("modelsLocation");

    for(let i = 0; i < data.length; i++){

        //get data for this sm
        const sm = data[i];

        //build img container a
        const a = document.createElement("a");
        a.href = "/supermodel/" + sm['name'];
        a.height = 200;

        //build img element
        const img = document.createElement("IMG");
        img.src = sm['imgUrl'];
        img.height = 200;

        //add img to a & a to div
        a.appendChild(img);
        modelsLoc.appendChild(a);
    }
}

//get data from api endpoint
fetch("/supermodel/api/supermodels")
    .then(response => response.json())
    .then(supermodels => renderData(supermodels))
    .catch(err => console.log(err));
