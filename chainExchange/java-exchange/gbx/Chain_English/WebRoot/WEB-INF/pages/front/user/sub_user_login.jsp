<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getHeader("X-Forwarded-Proto") + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<!DOCTYPE html>
<html lang="ko" class=" js ">

<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="../comm/link.inc.jsp"%>
	<link href="${oss_url}/static/front/images/bitbs/favicon.ico" rel="shortcut icon" type="image/vnd.microsoft.icon">

	<link href="${oss_url}/static/front/css/bitbs/application.css" media="all" rel="stylesheet">
	<link href="${oss_url}/static/front/css/bitbs/webpack-public-style.css" media="screen" rel="stylesheet">
</head>

<body class="static_view eng">

<%@include file="../comm/header.jsp"%>

<div id="main-content" style="background: #fcfcfc;margin-top: 20px;">
	<div class="container">
		<div class="main-content-area no-submenus">
			<div class="span6 offset3 thumbnail">

				<div>
					<div class="user-pwd-recovevery-block">
						<form action="/" class="form-horizontal user-registrations text-center">
							<div class="controls">
								<h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Sign In</font></font></h3>
							</div>

							<div class="form-controls">
								<div class="control-group ">
									<label class="control-label">
										<font style="vertical-align: inherit;">
											<font style="vertical-align: inherit;">Email or Mobile</font>
										</font>
									</label>
									<div class="controls">
										<input type="email" id="login-account" maxlength="80" placeholder="" />
									</div>
								</div>
								<div class="control-group ">
									<label class="control-label" for="user_password">
										<font style="vertical-align: inherit;">
											<font style="vertical-align: inherit;">Password</font>
										</font>
									</label>
									<div class="controls">
										<div class="password-input-block position-relative tooltip-component" data-valid="false" data-score="0">
											<input type="password" class="password-input" id="login-password" />
										</div>
										<span id="login-errortips" class="text-danger"></span>
									</div>
								</div>
								<div class="control-group " style="display: none" id="verificationDiv">
									<div class="controls">
										<input type="text" id="register-imgcode" placeholder="Verification Code"/>
										<img class="controls-right btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>">
									</div>
								</div>
								<div class="control-group " style="display: none" id="smsDiv">
									<div class="controls">
										<input type="text" id="register-msgcode" placeholder="SMS authentication code"/>
										<a class="controls-right loginbtn-sendmsg" data-msgtype="16" data-tipsid="register-errortips" id="register-sendmessage">Send</a>
									</div>
								</div>
								<div class="control-group " style="display: none" id="googleDiv">
									<label class="control-label">
										<font style="vertical-align: inherit;">
											<font style="vertical-align: inherit;">Google</font>
										</font>
									</label>
									<div class="controls">
										<input id="googleCode" value="0" class="form-control" type="text" placeholder="Google verification code"/>
									</div>
								</div>
								<div class="text-right">
									<a href="/users/password/new">
										<font style="vertical-align: inherit;">
											<a href="/validate/reset.html" style="vertical-align: inherit;">Password Recovery</a>
										</font>
									</a><br>
								</div>
							</div>
							<div class="form-actions-light form-controls">
								<font style="vertical-align: inherit;">
									<font style="vertical-align: inherit;">
										<input type="button" id="login-submit" class="btn btn-large btn-default btn-block" value="Sign In" />
									</font>
								</font>
							</div><br>
							<div class="text-left form-controls">
								<p class="bottom-margin">
											<span class="text-grey">
												<font style="vertical-align: inherit;">
													<font style="vertical-align: inherit;">Not a member yet?</font>
												</font>
											</span>
									&nbsp;
									<a href="/users/sign_up">
										<font style="vertical-align: inherit;">
											<a href="/user/register.html" style="vertical-align: inherit;">Register</a>
										</font>
									</a>
								</p>
							</div>
						</form>
					</div>
				</div>
				<input id="forwardUrl" type="hidden" value="${forwardUrl }">
			</div>
		</div>
		<!-- END div main-content-area -->
	</div>
</div>

<%@include file="../comm/footer.jsp" %>
<script type="text/javascript" src="${oss_url}/static/front/js/user/login.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/register.js"></script>
</body>

</html>
