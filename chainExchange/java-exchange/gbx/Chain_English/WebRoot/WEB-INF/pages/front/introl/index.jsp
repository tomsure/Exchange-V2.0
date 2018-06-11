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
<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
</head>
<body>
	

 
<%@include file="../comm/header.jsp" %>




	<div class="container-full">
		<div class="container displayFlex">
			<div class="row">
			<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="active">
								<a href="javascript:void(0)">Invitation History</a>
							</li>
							<%--<li>--%>
								<%--<a href="/introl/mydivide.html?type=2">收益记录</a>--%>
							<%--</li>--%>
						</ul>
						
						<div class="user-top-icon">
							<div class="col-xs-2 text-center">
								<i class="top-icon intro"></i>
							</div>
							<div class="col-xs-10 padding-left-clear">
								<div>
									<h5 class="top-title5" style="font-size:14px; color: #e06924;padding-top: 10px;">Invitation Code:${fn:substring(login_user.fid*110033, 0, 6)}${login_user.fid}</h5>
								</div>
							</div>
						</div>
						
						<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped">
								<tr class="bg-gary">
									<td class="col-xs-4">UID</td>
									<td class="col-xs-4">Time</td>
									<%--<td class="col-xs-4 text-right">是否实名认证</td>--%>
								</tr>
								
								<c:forEach items="${fusers }" var="v" >
								<tr>
									<td>${v.fid }</td>
									<td><fmt:formatDate
									value="${v.fregisterTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<%--<td  class="text-right">${v.fisValid ==true?'已实名认证':'用户未实名认证' }</td>--%>
								</tr>
								</c:forEach>
								
								<c:if test="${fn:length(fusers)==0 }">
								<tr>
										<td colspan="3" class="no-data-tips">
											<span> No Record </span>
										</td>
									</tr>
								</c:if>	
							</table>
							
								<div class="text-right">
									${pagin }
								</div>
							
						</div>
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>


<%@include file="../comm/footer.jsp" %>	


</body>
</html>