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
		<div class="container reset">
		<div class="row">
			<div class="col-xs-12 ">
				<span class="reset-process">
					<span class="col-xs-3 col-xs-offset-3 " id="resetprocess1">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">1</span>
						<span class="reset-process-text">Fill in account name</span>
					</span>
					<span class="col-xs-3 active" id="resetprocess2">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">2</span>
						<span class="reset-process-text">Setting login password</span>
					</span>
					<span class="col-xs-3" id="resetprocess3">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">3</span>
						<span class="reset-process-text">Success</span>
					</span>
				</span>
			</div>
			<div class="col-xs-12 reset padding-top-30">
				<div class="col-xs-8 col-xs-offset-2 reset-body">
					<div class="col-xs-12">
						<span class="reset-title">
							You are passing
							<span>Mobile</span>
							Retrieve login password
						</span>
					</div>
					
						
							<div class="col-xs-7 form-horizontal padding-top-30" id="secondstep">
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-loginname">Login</label>
									<div class="col-xs-8">
										<span class="form-control border-fff" id="reset-loginname">${fuser.ftelephone }</span>
									</div>
								</div>
								
								
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-newpass">New password</label>
									<div class="col-xs-8">
										<input type="password" class="form-control" id="reset-newpass">
									</div>
								</div>
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-confirmpass">Confirm password</label>
									<div class="col-xs-8">
										<input type="password" class="form-control" id="reset-confirmpass">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-4 control-label" for="reset-success-errortips"></label>
									<div class="col-xs-8">
										<span class="text-danger" id="reset-success-errortips"></span>
									</div>
								</div>
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-imgcode"></label>
									<div class="col-xs-8">
										<button class="btn btn-danger btn-block" id="btn-email-success">Next</button>
									</div>
								</div>
							</div>
							<div style="display:none" class="col-xs-12 padding-top-30 successstep text-center" id="successstep">
								<div>
									<i class="successstep-icon"></i>
									Congratulations, reset the password successfully
								</div>
								<a class="btn btn-danger btn-find" href="/user/login.html">Login</a>
							</div>
						
						
					
				</div>
			</div>
		</div>
	</div>
	</div>

<input type="hidden" id="fid" value="${fuser.fid}"/>
<input type="hidden" id="ev_id" value="0"/>
<input type="hidden" id="newuuid" value="0"/>
<input type="hidden" id="mtype" value="1"/>
<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/reset.js"></script>
</body>
</html>
