<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../comm/include.inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../comm/header.jsp"></jsp:include>
		<meta charset="utf-8" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<title>设置交易密码</title>
		<link rel="stylesheet" href="/static/front/app/css/css.css" />
		<link rel="stylesheet" href="/static/front/app/css/style.css" />
		<script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
	</head>
	<body>
		<nav>
			<div class="register-banner">
				<a href="javascript:;" onClick="javascript :history.back(-1)"><img src="images/back_home.png"></a>
				<p>注册</p>
			</div>
			<div class="step">
				<ul>
					<li>注册</li>
					<li class="active">设置交易密码</li>
					<li>实名认证</li>
					<li>成功</li>
				</ul>
			</div>
		</nav>
		<section>
			<div class="information">
				<div class="register_cont" style="display: block;">
					<div class="hint"><img src="images/hint.png">为保障您的资金安全，请设置交易密码，请务必牢记</div>
					<div class="information-cont">
						<ul>
							<li>
								<input type="password" class="password" value="" placeholder="请输入密码" id="password1" />
								<em>
									<img src="images/password_off.png" class="off" style="display: block;" />
									<img src="images/password_on.png" class="on" />
								</em>
							</li>
							<li>
								<input type="password" class="password" value="" placeholder="请再次输入密码"  id="password2"/>
								<em>
									<img src="images/password_off.png" class="off" style="display: block;" />
									<img src="images/password_on.png" class="on" />
								</em>
							</li>
						</ul>
					</div>
					<div class="next next-active">下一步</div>
				</div>
			</div>
		</section>
	</body>
</html>
<script type="text/javascript" src="/static/front/js/comm/util.js"></script>
		<script type="text/javascript" src="/static/front/js/language/language_cn.js"></script>
		<script type="text/javascript" src="/static/front/js/layer/layer.js"></script>
<script>
	$(function(){
		
		$(".information-cont .off").on('click',function(){
			$(this).hide();
			$(this).next().show();
			$(this).parent().siblings('input.password').attr('type','text');
		});
		
		$(".information-cont .on").on('click',function(){
			$(this).hide();
			$(this).prev().show();
			$(this).parent().siblings('input.password').attr('type','password');
		});
		
		$(".next").on('click',function(){
			var password1 = $("#password1").val() ;
			var password2 = $("#password2").val() ;
			if(password1!=password2){
				layer.msg('两次密码输入不一致') ;
				return ;
			}
			if(password1.length<6){
				layer.msg('密码长度必须不小于6位') ;
					return ;
			}
			
			$.post('/m/json/tradePwd.html',{password1:password1,password2:password2},function(data){
				var code = data.code ;
				var msg = data.msg ;
				if(code == 0 ){
					window.location.href = '/m/realId.html';
				}
				layer.msg(msg) ;
			},'json') ;
			
		});
		
	});
</script>