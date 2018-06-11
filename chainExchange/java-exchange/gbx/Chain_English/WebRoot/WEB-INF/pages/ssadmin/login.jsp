<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${requestScope.constant['webinfo'].ftitle }</title>
<link href="${oss_url}/static/ssadmin/js/themes/css/login.css" rel="stylesheet" type="text/css" />
	<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<script type="text/javascript">
<c:if test="${error != null}">
alert("${error}") ;
</c:if>
</script>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="/"><img src="${oss_url}/static/ssadmin/js/themes/default/images/login_logo.gif" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">

				</div>
				<h2 class="login_title"><img src="${oss_url}/static/ssadmin/js/themes/default/images/login_title.png" width="220px;"/></h2>
			</div>
		</div>
		<script type="text/javascript">
			function validate(f){
				f.src = "/servlet/ValidateImageServlet?"+Math.random() ;
			}
		</script>
		<div id="login_content">
			<div class="loginForm">
				<form action="/ssadmin/submitLogin_zblt.html" method="post">
					<p>
						<label>userName</label>
						<input type="text" name="name" size="20" class="login_input" />
					</p>
					<p>
						<label>password</label>
						<input type="password" name="password" size="20" class="login_input" />
					</p>
					<p>
						<label>Google</label>
						<input type="text" name="google" size="20" class="login_input" />
					</p>
					<p>
						<label>Code</label>
						<input class="code" type="text" size="5" name="vcode" />
						<span><img src="/servlet/ValidateImageServlet" alt="" width="75" height="24" onclick="validate(this);"/></span>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="${oss_url}/static/ssadmin/js/themes/default/images/login_banner.jpg" width="920px;" height="270px;"/></div>
			<div class="login_main">
				<ul class="helpList">

				</ul>
				<div class="login_inner">
					<p>Real-time reminder by text message ensure your account and funds safety.</p>
					<p>Multi-layer encryption for bitcoin wallet and offline storage protect your funds safety.</p>
					<p>HTTPS is adopted for encrypted transfer of all your materials,effectively preventing leakage via the network. ……</p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			${requestScope.constant['webinfo'].fcopyRights }
		</div>
	</div>
</body>
</html>
