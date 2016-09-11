/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function clicSite(idSite) {
    option=0;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./clic-site-"+idSite+".html");
    }        
}
function sendReq(xhr, file) {
    xhr.onreadystatechange=traiteReponse;
    xhr.open("GET", file, true);
    xhr.send(null);
}
function traiteReponse() {
    if(xhr.readyState==4&&(xhr.status==200||xhr.status==0)) {
        switch(option) {
            case 0:
                var resultat=xhr.responseText;
                break;
        }
    }
}
function getXMLHttpRequest() {
    var xhr=null;
    if(window.XMLHttpRequest||window.ActiveXObject) {
        if(window.ActiveXObject) {
            try {
                xhr=new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
                xhr=new ActibeXObject("Microsoft.XMLHTTP");
            }
        } else {
            xhr=new XMLHttpRequest();
        }
    } else {
        return null;
    }
    return xhr;
}

