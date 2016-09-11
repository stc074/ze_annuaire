<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Ze-annuaire - Ajouter son site</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Ze-annuaire - Inscrire son site dans l'annuaire."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="CSS/style.css" />
<%@include file="analytics.jsp" %>
    </head>
    <body>
        <%@include file="haut.jsp" %>
        <section>
            <c:catch var="ex">
                <h1>Ajouter son site</h1>
                <p>Pour ajouter votre site, rien de plus facile, en 2 minutes c'est fait grâce au formulaire ci-dessous.</p>
                <c:if test="${requestScope.site!=null}">
                    <c:set var="si" value="${requestScope.site}" scope="page"></c:set>
                    <c:if test="${si.test==0}">
                    <div id="form">
                        <c:if test="${not empty si.errorMsg}">
                            <div class="erreur">
                                <div>ERREUR(S) :</div>
                                <p></p>
                                ${si.errorMsg}
                            </div>
                            <p></p>
                        </c:if>
                            <form action="./ajouter-site.html#form" method="POST">
                                <p>
                                <label for="idCategorie">Choisissez la catégorie de votre site :</label>
                                <br/>
                                <select name="idCategorie" id="idCategorie">
                                    <option value="0-0"<c:if test="${si.idCat=='0-0'}"> selected="selected"</c:if>>Choisissez</option>
                                    <c:if test="${requestScope.categories!=null}">
                                        <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                                            <c:if test="${cats.nb>0}">
                                                <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
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
                                </p>
                                     <label for="url">Adresse de votre site (de la forme http://...) :</label>
                                    <br/>
                                    <input type="text" name="url" id="url" value="<c:out value="${si.url}"></c:out>" size="30" maxlength="80" />
                                    <br/>
                                    <label for="titre">Titre de votre site :</label>
                                    <br/>
                                    <input type="text" name="titre" id="titre" value="<c:out value="${si.titre}"></c:out>" size="30" maxlength="80" />
                                    <br/>
                                    <label for="description">Description de votre site :</label>
                                    <br/>
                                    <textarea name="description" id="description" rows="10" cols="80">${si.description}</textarea>
                                    <br/>
                                    </p>
                                    <div class="captcha"></div>
                                    <div class="captchaDroite">
                                    <label for="captcha">&rarr;COPIEZ LE CODE SVP&rarr;</label>
                                    <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                    </div>
                                    <p>
                                        <input type="submit" value="Valider" name="kermit" />
                                        <br/>
                                        <br/>
                                    </p>
                           </form>
                    </div>
                    </c:if>
                    <c:if test="${si.test==1}">
                        <div class="cadre">
                            <div class="info">Votre site vient d'etre enregistré.</div>
                            <br/>
                            <p>Il est visible ici :<a href="./" title="VOIR MON SITE EN PAGE D'ACCUEIL">VOTRE SITE EN PAGE D'ACCUEIL</a> ainsi que sur la page de la catégorie dont il dépend.</p>
                        </div>
                        <p></p>
                        <p>Si vous voulez ajouter un lien vers le répertoire (uniquement en page d'accueil sinon c pas grave) :</p>
                        <textarea name="codeRetour" rows="4" cols="50"><a href="http://www.ze-annuaire.net">ze-annuaire</a></textarea>
                        <p></p>
                        <div class="cadre">
                            <div class="info">D'autres outils pour référencer GRATUITEMENT votre site.</div>
                            <p></p>
                            <p><a href="http://www.echange-de-liens.eu" title="ÉCHANGE DE LIENS">échange de liens</a> &rarr; Site d'échange de liens Pagerank 3.</p>
                            <p><a href="http://www.echangedeliens.info" title="ÉCHANGE DE LIENS">échange de liens</a> &rarr; Autre site d'échange de liens Pagerank 3.</p>
                        </div>
                    </c:if>
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
        <%@include file="footer.jsp" %>
    </body>
</html>
