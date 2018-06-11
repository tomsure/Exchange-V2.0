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

<link rel="stylesheet" href="${oss_url}/static/front/css/vote/vote.css" type="text/css"></link>
</head>
<body>



<%@include file="../comm/header.jsp" %>

 



	<div class="container-full">
		<div class="container displayFlex">
		<div class="row">
			<div class="col-xs-12 padding-right-clear padding-left-clear rightarea">
				<div class="col-xs-12 rightarea-con">
					<div class="col-xs-12 assets-details-header">
						<div class="col-xs-3 text-center">
							
							
								<img alt="${vote.fcnName }" class="logo" src="${vote.furl }"></img>
							
						</div>
						<div class="col-xs-9 padding-left-clear">
						    <span class="project">${vote.fcnName }(${vote.fshortName })</span>
							<p class="vision">${vote.fenName }</p>
							<div id="supportOper" class="displayFlex">
								<button id="support" class="btn btn-nolike ">
									<i class="icon-like  "></i><span>${vote.fyes }</span>
								</button>
								<button id="nosupport" class="btn btn-nolike">
									<i class="icon-nolike  "></i><span>${vote.fno }</span>
								</button>
							</div>
						</div>
					</div>
					<div class="col-xs-12 padding-clear padding-top-30">
						<div class="panel panel-tips">
							<div class="panel-header text-center text-danger">
								<span class="panel-title">重要提示</span>
							</div>
							<div class="panel-body">
								<p>${requestScope.constant['coinvoteTips'] }</p>
							</div>
						</div>
					</div>
					<div class="col-xs-12 padding-clear padding-top-30">
						<div class="col-xs-12 padding-clear assets-details-border ">
							<ul class="nav nav-tabs rightarea-tabs">
								<li class="assets-details-tab active" data-showid="whitePaper" data-hideid="comments">
									<a href="javascript:void(0)">项目白皮书</a>
								</li>
							</ul>
							<div id="whitePaper" class="row assets-details-padding padding-top-30 padding-bottom-30">${vote.fdesc }</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>



<%@include file="../comm/footer.jsp" %>	
 	<script type="text/javascript" src="${oss_url}/static/front/js/vote/vote.js"></script>
<input type="hidden" id="fid" value="${vote.fid }">
<input type="hidden" id="islogin" value="${islogin }">
</body>
</html>