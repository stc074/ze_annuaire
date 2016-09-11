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
                <h1>Créer uen catégorie</h1>
                <c:if test="${requestScope.categorie!=null}">
                    <c:set var="cat" value="${requestScope.categorie}" scope="page"></c:set>
                    <div id="form">
                        <c:if test="${cat.test==1}">
                            <div class="cadre">
                                <div class="info">Catégorie "<c:out value="${cat.categorie}"></c:out>" enregistrée !</div>
                            </div>
                            <p></p>
                        </c:if>
                        <c:if test="${not empty cat.errorMsg}">
                            <div class="erreur">
                                <div>ERREUR(S) :</div>
                                <p></p>
                                ${cat.errorMsg}
                            </div>
                            <p></p>
                        </c:if>
                            <form action="./creer-categorie.html#form" method="POST">
                                <fieldset>
                                    <legend>Nouvelle catégorie</legend>
                                    <p>
                                        <label for="categorie">Nouvelle catégorie :</label>
                                        <input type="text" name="categorie" id="categorie" value="" size="30" maxlength="100" />
                                        <br/>
                                        <input type="submit" value="Valider" name="kermit" />
                                        <br/>
                                    </p>
                                </fieldset>
                            </form>
                    </div>
                </c:if>
                <h2>Catégories déjà enregistrées</h2>
                <c:if test="${requestScope.categories!=null}">
                    <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                    <c:choose>
                        <c:when test="${cats.nb==0}">
                            <div class="cadre">
                                <div class="info">Aucune catégorie enregistrée.</div>
                            </div>
                        </c:when>
                        <c:when test="${cats.nb>0}">
                            <div class="cadre">
                                <div class="info"><c:out value="${cats.nb}"></c:out> catégorie(s) enregistrée(s) :</div>
                                <p></p>
                                <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                    <div><c:out value="${cats.categories[i]}"></c:out></div>
                                    <p></p>
                                </c:forEach>
                            </div>
                        </c:when>
                    </c:choose>
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
