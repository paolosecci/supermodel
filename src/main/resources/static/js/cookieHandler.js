export function getCookieByName(key){
    const cookieStr = document.cookie;
    const cookies = cookieStr.split(";");

    for(let i = 0; i < cookies.length; i++){
        if(cookies[i].split("=")[0] == key){
            return cookies[i];
        }
    }

    console.log("cookie not found");
    return null;
}

export function getRunwaySupermodels(){
    let runwaySupermodels = [];
    const cookieStr = document.cookie;
    const cookies = cookieStr.split(";");

    for(let i = 0; i < cookies.length; i++){
        if(cookies[i].substr(0,17).trim()==="RunwaySupermodel"){
            const key = cookies[i].split("=")[0];
            const val = cookies[i].split("=")[1];

            const id = key.substr(17, key.length - 1);
            const name = val.split(",")[0];
            const imgUrl = val.split(",")[1];

            runwaySupermodels.push({
                "name": name,
                "imgUrl": imgUrl,
                "id": parseInt(id)
            });
        }
    }

    return runwaySupermodels;
}