<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/reset.css" type="text/css"></link>
</head>
<body>
	




 


<%@include file="../comm/header.jsp" %>


	<div class="container-full ">
		<div class="container reset">
		<div class="row">
			<div class="col-xs-12 ">
				<p class="choose-title">Please select password recovery mode</p>
			</div>
			<div class="col-xs-12 reset padding-top-30">
				<div class="col-xs-5 col-xs-offset-1 text-center">
					<span class="choose-body">
						<span class="choose-icon choose-icon-email"></span>
						<h3>Retrieve through email</h3>
						<%--<h4>You need to log in to get your password</h4>--%>
						<a href="validate/resetEmail.html" class="btn btn-danger btn-find">Click reset</a>
					</span>
				</div>
				<div class="col-xs-5  text-center">
					<span class="choose-body">
						<span class="choose-icon choose-icon-phone"></span>
						<h3 class="">Retrieve by mobile phone</h3>
						<%--<h4>Need mobile phone authentication to retrieve password</h4>--%>
						<a href="validate/resetPhone.html" class="btn btn-danger btn-find">Click reset</a>
					</span>
				</div>
			</div>
		</div>
	</div>
</div>


	<%@include file="../comm/footer.jsp" %>	

</body>
</html>
