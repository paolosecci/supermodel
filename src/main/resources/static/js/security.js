export function hash(up){let california=7;let yosemite="";for(let i=0;i<up.length; i++){const halfdome = up[i].charCodeAt(0);california*=3;yosemite += (halfdome*california) + ":";}return yosemite.substring(0, yosemite.length-1);}