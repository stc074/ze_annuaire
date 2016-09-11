<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <h1>Créer une sous-categorie.</h1>
                <c:if test="${requestScope.sousCategorie!=null}">
                    <c:set var="sousCat" value="${requestScope.sousCategorie}" scope="page"></c:set>
                    <div id="form">
                        <c:if test="${sousCat.test==1}">
                            <div class="cadre">
                                <div class="info">Sous catégorie "<c:out value="${sousCat.sousCategorie}"></c:out>" enregistrée !</div>
                            </div>
                            <p></p>
                        </c:if>
                        <c:if test="${not empty sousCat.errorMsg}">
                            <div class="erreur">
                                <div>ERREUR(S) :</div>
                                <p></p>
                                ${sousCat.errorMsg}
                            </div>
                            <p></p>
                        </c:if>
                            <form action="./creer-sous-categorie.html#form" method="POST">
                                <fieldset>
                                    <legend>Nouvelle Sous-Catégorie</legend>
                                    <p>
                                        <label for="idCategorie">Catégorie :</label>
                                        <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./creer-sous-categorie-1-'+this.value+'.html#form';">
                                            <option value="0"<c:if test="${sousCat.idCategorie==0}"> selected="selected"</c:if>>Choisissez</option>
                                            <c:if test="${requestScope.categories!=null}">
                                                <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                                                <c:if test="${cats.nb>0}">
                                                    <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                                        <option value="<c:out value="${cats.ids[i]}"></c:out>"<c:if test="${sousCat.idCategorie==cats.ids[i]}"> selected="selected"</c:if>><c:out value="${cats.categories[i]}"></c:out></option>
                                                    </c:forEach>
                                                </c:if>
                                            </c:if>
                                        </select>
                                            <br/>
                                            <label for="sousCategorie">Nouvelle Sous-catégorie :</label>
                                            <input type="text" name="sousCategorie" id="sousCategorie" value="" size="30" maxlength="100" />
                                            <br/>
                                            <input type="submit" value="Valider" name="kermit" />
                                            <br/>
                                    </p>
                                </fieldset>
                            </form>
                    </div>
                                            <p></p>
                                            <c:if test="${requestScope.sousCategories!=null}">
                                                <c:set var="sousCats" value="${requestScope.sousCategories}" scope="page"></c:set>
                                                <c:if test="${sousCats.idCategorie!=0}">
                                                    <h2>Sous-catégories de la catégorie "<c:out value="${sousCats.categorie}"></c:out>"</h2>
                                                    <c:choose>
                                                        <c:when test="${sousCats.nb==0}">
                                                            <div class="cadre">
                                                                <div class="info">Aucune sous-catégorie enregistrée.</div>
                                                            </div>
                                                        </c:when>
                                                        <c:when test="${sousCats.nb>0}">
                                                            <div class="cadre">
                                                                <div class="info"><c:out value="${sousCats.nb}"></c:out> sous-catégorie(s) enregistrée(s).</div>
                                                                <p></p>
                                                            <c:forEach var="i" begin="0" end="${sousCats.nb-1}" step="1">
                                                                <div><c:out value="${sousCats.sousCategories[i]}"></c:out></div>
                                                                <p></p>
                                                            </c:forEach>
                                                            </div>
                                                        </c:when>
                                                    </c:choose>
                                                </c:if>
                                            </c:if>
                </c:if>
            </c:catch>
            <c:if test="${not empty ex}">
                <div class="erreur">
                    <div>ERREUR :</div>
                    <p></p>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </c:if>
        </section>
    </body>
</html>
