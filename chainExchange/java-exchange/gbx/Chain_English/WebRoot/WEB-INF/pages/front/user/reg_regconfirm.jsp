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

	<div class="container-full">
		<div class="container text-center validate">
		<div class="row">
			<div class="col-xs-12 clearfix padding-clear padding-top-30 text-center">
				<div class="validate-online">
					
						<c:if test="${validate == false }">
							<span class="validate-success failure">
								<span class="validate-text failure">The activation link has expired.</span>
							</span>
						</c:if>
						<c:if test="${validate == true }">
						<span class="validate-success">
								<span class="validate-text">Congratulations！ Activated successfully.</span>
							</span>
						</c:if>	
					
					<div class="form-group">
						<a class="btn btn-danger btn-block" href="/index.html">Homepage</a>
					</div>
					<div class="form-group">
						<a class="btn btn-danger btn-block" href="/user/login.html">Sign In</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>

<%@include file="../comm/footer.jsp" %>	



</body>
</html>
