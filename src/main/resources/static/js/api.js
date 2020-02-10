export function getSupermodels(){
    return fetch("/supermodel/api/supermodels")
        .then(response => response.json())
        .catch(err => console.log(err));
}

export function getRunway(){
    return fetch("/supermodel/api/runway")
        .then(response => response.json())
        .catch(err => console.log(err));
}