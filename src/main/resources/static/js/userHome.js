import * as cookieHandler from "./cookieHandler.js";

let username = cookieHandler.getCookieByName("SupermodelUsername").split("=")[1];
console.log(username);

document.getElementById("title").innerText = username;
document.getElementById("studioLabel").innerText = username + "'s Supermodel Studio";