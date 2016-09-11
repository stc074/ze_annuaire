<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Ze-Annuaire - ASMINISTRATION</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="../GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="../CSS/style.css" />
<%@include file="../../scripts/analytics.jsp" %>
    </head>
    <body>
        <%@include file="haut.jsp" %>
        <section>
            <c:catch var="ex">
                <h1>Liste des sites</h1>
                <c:if test="${requestScope.sites!=null}">
                    <c:set var="si" value="${requestScope.sites}" scope="page"></c:set>
                    <div id="form">
                        <c:if test="${not empty si.errorMsg}">
                            <div class="erreur">
                                <div>ERREUR(S) :</div>
                                <p></p>
                                ${si.errorMsg}
                            </div>
                            <p></p>
                        </c:if>
                            <form action="./liste-sites.html#form" method="POST">
                                <p>
                                    <label for="idCat">Catégorie :</label>
                                    <select name="idCat" id="idCat" onchange="javascript:window.location.href='./liste-sites-1-'+this.value+'.html';">
                                        <option value="0-0"<c:if test="${si.idCat=='0-0'}"> selected="selected"</c:if>>Toutes</option>
                                        <c:if test="${requestScope.categories!=null}">
                                            <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                                                <c:if test="${cats.nb>0}">
                                                    <c:forEach var="i" begin="0" end="${cats.nb-1}">
                                                        <optgroup label="<c:out value="${cats.categories[i]}"></c:out>">
                                                            <option value="<c:out value="${cats.ids21[i]}"></c:out>"<c:if test="${si.idCat==cats.ids21[i]}"> selected="selected"</c:if>><c:out value="${cats.categories[i]}"></c:out></option>
                                                            <c:forEach var="j" begin="0" end="${cats.nbs[i]-1}" step="1">
                                                                <option value="<c:out value="${cats.ids22[i][j]}"></c:out>"<c:if test="${si.idCat==cats.ids22[i][j]}"> selected="selected"</c:if>>${cats.categories2[i][j]}</option>
                                                            </c:forEach>
                                                        </optgroup>
                                                    </c:forEach>
                                                </c:if>
                                        </c:if>
                                    </select>
                                        <br/>
                                        <label for="index1">Index N°1 :</label>
                                        <input type="text" name="index1" id="index1" value="<c:out value="${si.index1}"></c:out>" size="6" maxlength="10" />
                                        <br/>
                                        <label for="index2">Index N°2 :</label>
                                        <input type="text" name="index2" id="index2" value="<c:out value="${si.index2}"></c:out>" size="6" maxlength="10" />
                                        <br/>
                                        <input type="submit" value="Valider" name="kermit" />
                                        <br/>
                                </p>
                            </form>
                    </div>
                                        <div class="colonneDroite">
                                            <c:choose>
                                                <c:when test="${si.nb==0}">
                                                    <div class="cadre">
                                                        <div class="info">Aucun site pour cette recherche.</div>
                                                    </div>
                                                </c:when>
                                                <c:when test="${si.nb>0}">
                                                    <div class="cadre">
                                                        <div class="info"><c:out value="${si.nb}"></c:out> site(s) trouvé(s) pour cette recherche :</div>
                                                    </div>
                                                    <p></p>
                                                    <c:forEach var="i" begin="0" end="${si.nb-1}" step="1">
                                                        <div class="cadreSite">
                                                            <div class="gauche"></div>
                                                            <div class="droite">
                                                            <h1>
                                                                <a href="<c:out value="${si.urls[i]}"></c:out>"><c:out value="${si.titres[i]}"></c:out></a>
                                                            </h1>
                                                            <p>${si.descriptions[i]}</p>
                                                            <p>
                                                                <a href="./efface-site-<c:out value="${si.ids[i]}"></c:out>.html" rel="nofollow" title="ÉFFACER CE SITE">ÉFFACER CE SITE</a>
                                                            </p>
                                                            </div>
                                                        </div>
                                                        <p></p>
                                                    </c:forEach>
                                                </c:when>
                                            </c:choose>
                                        </div>
                </c:if>
            </c:catch>
            <c:if test="${not empty ex}">
                <div class="erreur">
                    <div>ERREUR :</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </c:if>
        </section>
    </body>
</html>
