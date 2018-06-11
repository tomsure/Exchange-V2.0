<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html lang="ko" class=" js ">

<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="../comm/link.inc.jsp" %>
	<title>Korbit - Bitcoin, Ethereum, Remittance</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="format-detection" content="telephone=no">

	<link href="${oss_url}/static/front/images/bitbs/favicon.ico" rel="shortcut icon" type="image/vnd.microsoft.icon">

	<link href="${oss_url}/static/front/css/bitbs/application.css" media="all" rel="stylesheet">
	<link href="${oss_url}/static/front/css/bitbs/webpack-public-style.css" media="screen" rel="stylesheet">
</head>

<body class="static_view eng">

<%@include file="../comm/header.jsp" %>

<div id="main-content" style="background: #fcfcfc;">
	<div class="container">
		<div class="main-content-area no-submenus">
			<div class="span6 offset3 thumbnail">

				<div>
					<div class="user-pwd-recovevery-block">
						<form action="/" class="form-horizontal user-registrations text-center">
							<div class="controls">
								<style>
									.h3-tab{ display: block; box-sizing: border-box; width: 100%; padding: 0px 5%; font-size: 23px !important;}
									.h3-tab *{ margin: 0px 10px; color: #83181e; text-decoration: none; padding-bottom: 10px;}
									.h3-tab span{ border-bottom: 3px solid #83181e;}
									.h3-tab a{ color: #333;}
									.h3-tab a:hover{ color: #83181e; text-decoration: none;}
								</style>
								<h3 class="h3-tab">
									<a href="/user/registerSMS.html">Mobile</a>
									<span>Email</span>
								</h3>
							</div>
							<div class="form-controls">
								<div class="control-group ">
									<div class="controls">
										<input type="email" id="register-email" placeholder="Email" /></div>
								</div>
								<div class="control-group ">
									<div class="controls">
										<input type="text" id="register-imgcode" placeholder="Verification Code"/>
										<img class="controls-right btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>">
									</div>
								</div>
								<div class="control-group ">
									<div class="controls">
										<input type="text" id="register-email-code" placeholder="Email Verification Code"/>
										<a class="controls-right btn-sendemailcode" data-msgtype="3" data-tipsid="register-errortips" id="register-sendemail">Send</a>
									</div>
								</div>
								<div>
									<div class="control-group ">
										<div class="controls">
											<div class="password-input-block position-relative tooltip-component">
												<input type="password" id="register-password" class="password-input" placeholder="Password" /></div>
										</div>
									</div>
									<div class="control-group ">
										<div class="controls">
											<div class="password-input-block position-relative tooltip-component">
												<input type="password" id="register-confirmpassword" class="password-input" placeholder="Password Confirmation" /></div>
										</div>
									</div>
									<div class="control-group ">
										<div class="controls">
											<div class="password-input-block position-relative tooltip-component">
												<input id="register-intro" class="form-control login-ipt" type="text" value="${intro }" placeholder="Invitation Code">
											</div>
										</div>
								</div>
								<span id="register-errortips" class="text-danger"></span>
							</div>

							<div class="form-actions-light form-controls">
								<font style="vertical-align: inherit;">
									<font style="vertical-align: inherit;">
										<input type="button" id="register-submit" class="btn btn-large btn-default btn-block" value="Register" /></font>
								</font>
							</div>
							<div class="form-controls align-center">
								<p>
									<a href="#">
										<font style="vertical-align: inherit;">
											<a href="/user/login.html" style="vertical-align: inherit;">Sign In</a></font>
									</a>
								</p>
								<p>
									<a href="#">
										<font style="vertical-align: inherit;">
											<a href="/validate/reset.html" style="vertical-align: inherit;">Password Recovery</a></font>
									</a>
								</p>
							</div>
						</form>

					</div>
				</div>

			</div>
		</div>
		<!-- END div main-content-area -->
	</div>
</div>
<input id="agree" type="checkbox" checked="checked" style="display: none;">
<span id="register-phone-areacode" style="display: none;">+86</span>

<%@include file="../comm/footer.jsp" %>

<input id="regType" type="hidden" value="1">
<input id="intro_user" type="hidden" value="${intro }">
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/register.js"></script>
</body>

</html>