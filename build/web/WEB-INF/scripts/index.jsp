<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Ze-annuaire le répertoire des sites de qualité</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Les meilleurs sites sont sur ze-annuaire le répertoire gratuit sans lien retour éxigé."/>
<link rel="icon" type="image/png" href="GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="CSS/style.css" />
<script type="text/javascript" src="scripts/scriptsJS.js"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="analytics.jsp" %>
    </head>
    <body>
<div id="fb-root"></div>
<script>
    (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
        <%@include file="haut.jsp" %>
        <section>
            <c:catch var="ex">
                <h1>Ze-annuaire répertoire de sites</h1>
                <p>Ze-annuaire est un outil pratique pour trouver des sites de qualité, de nombreuses catégories, des sites mis à jour.</p>
                <p>Si vous voulez inscrire votre site <a href="./ajouter-site.html" rel="nofollow" title="AJOUTER SON SITE">CLIQUEZ ICI</a> (Aucun lien retour n'est demandé).</p>
                <p></p>
                <div class="colonneGauche">
                    <c:if test="${requestScope.categories!=null}">
                        <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                        <nav>
                            <ul class="menu">
                                <li>CATÉGORIES</li>
                                <c:if test="${cats.nb>0}">
                                    <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                        <li class="item" onclick="javascript:window.location.href='<c:out value="${cats.urls[i]}"></c:out>';">
                                            <a href="<c:out value="${cats.urls[i]}"></c:out>" title="<c:out value="${cats.categories[i]}"></c:out>"><c:out value="${cats.categories[i]}"></c:out></a>
                                        </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>
                </div>
                <div class="colonneDroite">
            <p></p>
           <ul class="reseauxSoc">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
           </ul>
           <p></p>
                    <h2>Les derniers site inscrit</h2>
                    <c:if test="${requestScope.sites!=null}">
                        <c:set var="si" value="${requestScope.sites}" scope="page"></c:set>
                        <c:choose>
                            <c:when test="${si.nb==0}">
                                <div class="cadre">
                                    <div class="info">Aucune site inscrit.</div>
                                </div>
                                <p></p>
                               <div class="cadrePub">
<script type="text/javascript"><!--
google_ad_client = "ca-pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                                </div>
                                <p></p>
                            </c:when>
                            <c:when test="${si.nb>0}">
                                <div class="cadre">
                                    <div class="info"><c:out value="${si.nbSites}"></c:out> sites inscrits.</div>
                                </div>
                                <p></p>
                                <c:forEach var="i" begin="0" end="${si.nb-1}" step="1">
                                    <c:if test="${i==0||i==2||i==4}">
                               <div class="cadrePub">
<script type="text/javascript"><!--
google_ad_client = "ca-pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                                </div>
                                <p></p>
                                    </c:if>
                                 <div class="cadreSite">
                                    <div class="gauche">
                                        <p>
                                        <a href="<c:out value="${si.urls[i]}"></c:out>" target="_blank" title="<c:out value="${si.titres[i]}"></c:out>" onclick="javascript:clicSite(<c:out value="${si.ids[i]}"></c:out>);">
                                            <img src="http://open.thumbshots.org/image.aspx?url=<c:out value="${si.urls[i]}"></c:out>" style="border: none;" />
                                        </a>
                                        <br/>
                                        <img src="http://www.paidpr.com/pagerank/pagerank.php?style=0&url=<c:out value="${si.urls[i]}"></c:out>" style="border: none;" alt="pagerank gratuit" />
                                        </p>
                                    </div>
                                        <div class="droite">
                                            <h1>
                                                <a href="<c:out value="${si.urls[i]}"></c:out>" target="_blank" title="<c:out value="${si.titres[i]}"></c:out>" onclick="javascript:clicSite(<c:out value="${si.ids[i]}"></c:out>);"><c:out value="${si.titres[i]}"></c:out></a>
                                            </h1>
                                            <p>
                                                <c:out value="${si.lignes1[i]}"></c:out>
                                                <br/>
                                                ${si.lignes2[i]}
                                            </p>
                                            <p>${si.descriptions[i]}</p>
                                        </div>
                                </div>
                                        <p></p>
                                </c:forEach>
                                        <p></p>
                                        <p></p>
                             </c:when>
                        </c:choose>
                    </c:if>
                </div>
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
