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
		<title>注册成功</title>
		<link rel="stylesheet" href="/static/front/app/css/css.css" />
		<link rel="stylesheet" href="/static/front/app/css/style.css" />
		<script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
	</head>
	<body>
		<nav>
			<div class="register-banner">
				<!-- <a href="javascript:;" onClick="javascript :history.back(-1)"><img src="/static/front/app/images/back_home.png"></a> -->
				<p>注册</p>
			</div>
			<div class="step">
				<ul>
					<li>注册</li>
					<li>设置交易密码</li>
					<li>实名认证</li>
					<li class="active">成功</li>
				</ul>
			</div>
		</nav>
		<section>
			<div class="information">
				<div class="succeed">
					<div class="succeed-info clearfix">
						<h2 class="clearfix"><img src="/static/front/app/images/succeed.png">恭喜您，注册成功</h2>
						<p>注册账号：${fuser.floginName }</p>
						<p>认证姓名：${fuser.frealName }</p>
						<p>证件类型：身份证</p>
						<p>证件号码：${fuser.fidentityNo }</p>
					</div>
					<div class="succeed-btn clearfix">
						<ul>
							<li><a href="jiaoyizhongxin.html">交易中心</a></li>
							<li><a href="/m/user/login.html">马上登陆</a></li>
						</ul>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>