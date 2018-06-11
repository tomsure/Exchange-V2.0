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
		<title>登录</title>
		<link rel="stylesheet" href="/static/front/app/css/css.css" />
		<link rel="stylesheet" href="/static/front/app/css/style.css" />
		<script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
	</head>
	<body style="background: #fff;">
		<nav>
			<div class="register-banner">
				<a href="javascript:;" onClick="javascript :history.back(-1)"><img src="/static/front/app/images/back_home.png"></a>
				<p>登录</p>
				<div class="user-logo"><img src="/static/front/app/images/user_logo.png"></div>
			</div>
		</nav>
		<section>
				<div class="Personal-cont" style="display: block;">
						<ul class="clearfix" style="padding: 0;">
							<li>
								<p><input type="text" class="phone-code" value="" placeholder="账号" id="indexLoginName"/></p>
							</li>
							<li>
								<p><input type="password" class="password" value="" placeholder="密码" id="indexLoginPwd"/></p>
							</li>
						</ul>
				</div>
				<div class="tips_info clearfix">
					<p>还没有帐号？ <a href="/m/user/register.html">立即注册>></a></p>
					<span><a href="shoujizhaohuimima.html">忘记密码</a></span>
				</div>
				<div class="information">
					<div class="register_cont" style="display: block;">
						<div class="next next-active" onclick="javascript:loginIndexSubmit();">登录</div>
					</div>
				</div>
		</section>
		<script type="text/javascript" src="/static/front/js/comm/util.js"></script>
		<script type="text/javascript" src="/static/front/js/language/language_cn.js"></script>
		<script type="text/javascript" src="/static/front/js/layer/layer.js"></script>
		
		<script type="text/javascript">
			function loginIndexSubmit(){
		    var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		    var uName = document.getElementById("indexLoginName").value;
		    var pWord = document.getElementById("indexLoginPwd").value;
		    var longLogin = 0;
		    if (util.checkEmail(uName)) {
		        longLogin = 1;
		    }
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		    	layer.msg( language["comm.error.tips.1"]);
		        return
		    }
		    if (pWord == "") {
		    	layer.msg( language["comm.error.tips.2"]);
		        return;
		    } else if (pWord.length < 6) {
		    	layer.msg( language["comm.error.tips.3"]);
		        return;
		    }
		    var param = {
		        loginName: uName,
		        password: pWord,
		        type: longLogin
		    };
		    jQuery.post(url, param, function (data) {
		        if (data.code == 0) {
		            if (document.getElementById("forwardUrl") != null && document.getElementById("forwardUrl").value != "") {
		                var forward = document.getElementById("forwardUrl").value;
		                forward = decodeURI(forward);
		                window.location.href = forward;
		            } else {
		                var whref = document.location.href;
		                if (whref.indexOf("#") != -1) {
		                    whref = whref.substring(0, whref.indexOf("#"));
		                }
		                window.location.href = "/m/tradePwd.html";
		            }
		        } else if (data.code <0) {
		        	layer.msg( data.msg);
		            document.getElementById("indexLoginPwd").value = "";
		        }
		    },"json");
		}
		</script>
	</body>
</html>