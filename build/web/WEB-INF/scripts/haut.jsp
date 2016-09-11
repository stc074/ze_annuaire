<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="obj" class="classes.Objet" scope="page"></jsp:useBean>
<header>
    <div class="pagerank">
        <span>Pagerank : </span>
        <img src="http://www.paidpr.com/pagerank/pagerank.php?style=1&amp;url=<c:out value="${obj.URL_ROOT}"></c:out>" style="border: none;" alt="pagerank gratuit" />
    </div>
    <div class="logo" onclick="javascript:window.location.href='./';"></div>
    <nav>
        <ul class="menu">
            <li><a href="./" title="ACCUEIL">ACCUEIL</a></li>
            <li><a href="./ajouter-site.html" rel="nofollow">AJOUTER SON SITE</a></li>
            <li><a href="./top.html" title="LE TOP #<c:out value="${obj.NB_SITES_TOP}"></c:out>">LE TOP <c:out value="${obj.NB_SITES_TOP}"></c:out></a></li>
        </ul>
    </nav>
</header>