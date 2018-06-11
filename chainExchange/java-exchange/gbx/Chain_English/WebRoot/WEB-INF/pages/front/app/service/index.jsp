<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../comm/include.inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<title>最新公告</title>
		<link rel="stylesheet" href="/static/front/app/css/css.css" />
		<link rel="stylesheet" href="/static/front/app/css/style.css" />
		<link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
		<script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
	</head>
	<body>
		<nav>
			<div class="Personal-title">
				最新公告
			</div>
		</nav>
		<section>
			<div class="affiche-tab">
				<ul>
					<c:forEach items="${farticletypes }" var="v" varStatus="vs">
						<li class="${vs.index==0?'active':'' }">${v.fname }</li>
					</c:forEach>
				</ul>
			</div>
			<c:forEach items="${arts }" var="v" varStatus="vs">
				<div class="affiche-cont" style="display: ${vs.index==0?'block':''};">
				<ul>
					<c:forEach items="${v }" var="vv" >
					<li>
						<p><a href="/m/service/article.html?id=${vv.fid }">${vv.ftitle_short }</a></p>
						<span><fmt:formatDate value="${vv.flastModifyDate }" pattern="yyyy-MM-dd"/></span>
					</li>
					</c:forEach>
				</ul>
			</div>
			</c:forEach>
		</section>
		<jsp:include page="../comm/menu.jsp"></jsp:include>
	</body>
</html>
<script>
	$(function(){
		$(".affiche-tab ul li").on('click',function(){
			$(this).addClass('active').siblings().removeClass('active');
	            var index =  $(".affiche-tab ul li").index(this);
	            $(".affiche-cont").eq(index).show().siblings('.affiche-cont').hide();
		});
	});
	$(function(){
		$(".munu ul li").on('click',function(){
			$(this).addClass('active').siblings().removeClass('active');
		});
	});
</script>