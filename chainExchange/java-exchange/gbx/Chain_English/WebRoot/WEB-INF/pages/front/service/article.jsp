<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getHeader("X-Forwarded-Proto") + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>

<link rel="stylesheet" href="${oss_url}/static/front/css/service/service.css"
	type="text/css"></link>
</head>
<body>




	<%@include file="../comm/header.jsp"%>



<div class="container">
<div class="row">
<div class="col-xs-9 article-main">
        <div class="cols-xs-12 text-left">
			<p>
            <a href="/">Home</a>
                <%--/--%>
            <%--<a href="/service/ourService.html?id=${farticle.farticletype.fid }">${farticle.farticletype.fname}</a>--%>
			</p>
		</div>
        <div class="cols-xs-12">
            <h4 class="text-center"><strong>${farticle.ftitleEn }</strong></h4>
        </div>
        <div class="cols-xs-12 text-right article-info">
            <span><i class="iconfont"> </i> ${farticle.fcreateDate }</span>
            <span><i class="iconfont"> </i> ${requestScope.constant['webName']}</span>
        </div>
        <div class="cols-xs-12 article-content">
        ${farticle.fcontentEn }
        </div>
</div>
    <div class="col-xs-3 padding-right-clear">
          <div class="panel panel-default">
                <div class="panel-heading">
                     <span class="text-left"><i class="iconfont">㐺</i> <strong></strong></span>
                     <%--<a class="pull-right" href="/service/ourService.html?id=${farticle.farticletype.fid }">More&gt;&gt;</a>--%>
                </div>
                <table class="table table-hover">
                  <tbody class="newlist">
              <c:forEach items="${hotsArticles }" var="v">
                  <tr><td>
               <a href="/service/article.html?id=${v.fid }">
               <span class="coin_news_title left">${v.ftitleEn }</span>
               </a>
               <span class="pull-right"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd" /></span>
               </td></tr>
               </c:forEach>
                </tbody></table>
         </div>
    </div>
</div>
</div>





	<%@include file="../comm/footer.jsp"%>

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
</body>
</html>
