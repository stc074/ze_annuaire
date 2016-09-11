<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:catch var="ex">
    <c:set var="tagTitle" value="Annuaire de sites internet" scope="page"></c:set>
    <c:set var="tagDescription" value="ze-annuaire - Annuaire de sites internet de qualité." scope="page"></c:set>
    <c:if test="${requestScope.sousCategorie!=null}">
        <c:set var="sousCat" value="${requestScope.sousCategorie}" scope="page"></c:set>
        <c:set var="tagTitle" value="${sousCat.tagTitle}" scope="page"></c:set>
        <c:set var="tagDescription" value="${sousCat.tagDescription}" scope="page"></c:set>
    </c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><c:out value="${tagTitle}"></c:out></title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<c:out value="${tagDescription}"></c:out>"/>
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
            <div class="colonneGauche">
                <c:if test="${sousCat!=null&&requestScope.sousCategories!=null}">
                    <c:set var="sousCats" value="${requestScope.sousCategories}" scope="page"></c:set>
                    <nav>
                        <ul class="menu">
                            <li class="item" onclick="javascript:window.location.href='./';">
                                <a href="./" title="RETOUR">RETOUR</a>
                            </li>
                            <li><c:out value="${sousCat.categorie}"></c:out> - <c:out value="${sousCat.sousCategorie}"></c:out></li>
                            <c:if test="${sousCats.nb>0}">
                                <c:forEach var="i" begin="0" end="${sousCats.nb-1}" step="1">
                                    <li<c:choose><c:when test="${sousCat.id==sousCats.ids[i]}"> class="actuel"</c:when><c:otherwise> class="item"</c:otherwise></c:choose> onclick="javascript:window.location.href='<c:out value="${sousCats.urls[i]}"></c:out>';">
                                        <a href="<c:out value="${sousCats.urls[i]}"></c:out>" title="<c:out value="${sousCats.sousCategories[i]}"></c:out>"><c:out value="${sousCats.sousCategories[i]}"></c:out></a>
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
            </div>
            <div class="colonneDroite">
                <c:if test="${sousCat!=null&&requestScope.sites!=null}">
                    <c:set var="si" value="${requestScope.sites}" scope="page"></c:set>
                    <h2>Répertoire - catégorie <c:out value="${sousCat.categorie}"></c:out> - <c:out value="${sousCat.sousCategorie}"></c:out></h2>
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
           <c:choose>
               <c:when test="${si.nbSitesPage==0}">
                   <div class="cadre">
                       <div class="info">Aucun site dans cette catégorie.</div>
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
               </c:when>
                                <c:when test="${si.nbSitesPage>0}">
                                    <div class="cadre">
                                        <div class="info"><c:out value="${si.nbSites}"></c:out> site(s) dans cette catégorie :</div>
                                    </div>
                                    <p></p>
                                    <c:forEach var="i" begin="0" end="${si.nbSitesPage-1}" step="1">
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
                                                    <a href="<c:out value="${si.urls[i]}"></c:out>" title="<c:out value="${si.titres[i]}"></c:out>" target="_blank" onclick="javascript:clicSite(<c:out value="${si.ids[i]}"></c:out>);">
                                                        <img src="http://open.thumbshots.org/image.aspx?url=<c:out value="${si.urls[i]}"></c:out>" style="border: none;" />
                                                    </a>
                                                    <br/>
                                                    <img src="http://www.paidpr.com/pagerank/pagerank.php?style=0&url=<c:out value="${si.urls[i]}"></c:out>" style="border: none;" alt="pagerank gratuit" />
                                                </p>
                                            </div>
                                                <div class="droite">
                                                    <h1>
                                                        <a href="<c:out value="${si.urls[i]}"></c:out>" title="<c:out value="${si.titres[i]}"></c:out>" target="_blank" onclick="javascript:clicSite(<c:out value="${si.ids[i]}"></c:out>);"><c:out value="${si.titres[i]}"></c:out></a>
                                                    </h1>
                                                    <p>
                                                        <c:out value="${si.lignes1[i]}"></c:out>
                                                        <br/>
                                                        ${si.lignes2[i]}
                                                        <br/>
                                                    </p>
                                                    <p>${si.descriptions[i]}</p>
                                                </div>
                                        </div>
                                                    <p></p>
                                    </c:forEach>
                                                    <div class="pages">
                                                        <span>Pages de sites : </span>
                                                        <c:forEach var="i" begin="${si.prem}" end="${si.der}" step="1">
                                                            <c:choose>
                                                                <c:when test="${si.page==i}">
                                                                    <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span>[<a href="./sous-categorie-page-<c:out value="${si.idCategorie}"></c:out>-<c:out value="${si.idSousCategorie}"></c:out>-<c:out value="${i}"></c:out>-<c:out value="${sousCat.encodedSousCategorie}"></c:out>.html" title="PAGE #<c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </div>
                                                    <p></p>
                                                    <p></p>
                                 </c:when>
           </c:choose>
                </c:if>
            </div>
        </section>
        <%@include file="footer.jsp" %>
    </body>
</html>
</c:catch>
<c:if test="${not empty ex}">
    <html>
        <head>
            <title>ERREUR !</title>
        </head>
        <body>
            <section>
                <div class="erreur">
                    <div>ERREUR :</div>
                    <p></p>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </section>
        </body>
    </html>
</c:if>
