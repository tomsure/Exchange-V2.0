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
<div class="col-xs-12 article-main">
	  <div class="cols-xs-12 text-left">
			<p>
            <a href="/">Home</a> /
            <a href="/service/ourService.html?id=${atype.fid }">${atype.fnameEn }</a>
			</p>
		</div>
      <ul class="list-group">
      <c:forEach items="${farticles }" var="v">
      <li class="list-group-item">
          <a class="pull-left" href="/service/article.html?id=${v.fid }">${v.ftitleEn } </a>
          <span class="pull-right">${v.fcreateDate }</span>
           </li>
      </c:forEach>     
           
           </ul>
      <div class="page pull-right">
      		<ul>
      		${pagin }
            </ul>
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
