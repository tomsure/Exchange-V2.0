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
		<style type="text/css">
			img{
				width: 100%;
			}
		</style>
	</head>
	<body>
		<nav>
			<div class="Personal-title">
				<span>
					<a href="javascript:;" onClick="javascript :history.back(-1)">
						<em>
							<i></i>
							<i></i>
						</em>
						<strong>返回</strong>
					</a>
				</span>
				${farticle.ftitle_short }
			</div>
		</nav>
		<section id="cont-padding">
			<div class="detailed">
				<h2>${farticle.ftitle }</h2>
				<div style="width: 100%">${farticle.fcontent }
				
				<h3>云数网</h3>
				<h3 style="margin-bottom: 40px;"><fmt:formatDate value="${farticle.flastModifyDate }" pattern="yyyy年MM月dd日"/></h3>
				</div>
			</div>
		</section>
		<jsp:include page="../comm/menu.jsp"></jsp:include>
	</body>
</html>
