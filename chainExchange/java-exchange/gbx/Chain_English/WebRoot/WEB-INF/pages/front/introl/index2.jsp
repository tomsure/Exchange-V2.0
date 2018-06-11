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
							<li>
								<a href="/introl/mydivide.html?type=1">普通推广记录</a>
							</li>
							<li class="active">
								<a href="javascript:void(0)">收益记录</a>
							</li>
						</ul>
						
						<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped">
								<tr class="bg-gary">
									<td class="col-xs-6">收益内容</td>
									<td class="col-xs-6 text-right">收益日期</td>
								</tr>
								
								<c:forEach items="${fintrolinfos }" var="v" >
								<tr>
									<td>${v.ftitle }</td>
									<td class="text-right"><fmt:formatDate
									value="${v.fcreatetime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
								</c:forEach>
								
								<c:if test="${fn:length(fintrolinfos)==0 }">
								<tr>
										<td colspan="2" class="no-data-tips">
											<span> 暂无记录 </span>
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