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
							<li class="active">
								<a href="javascript:void(0)">My ICO</a>
							</li>
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped">
								<tr class="bg-gary">
									<td>Project Nmae</td>
								    <td>Status</td>
								    <td>Payment</td>
									<td>Amount</td>
									<td>Frozen</td>
									<td>Unit Price</td>
									<td>Total</td>
									<td class="text-right">Time</td>
								</tr>
								
								<c:forEach items="${subscriptionlogs }" var="v">
									<tr>
										<td>
											<a href="/crowd/view.html?fid=${v.fsubscription.fid }" target="_blank" style="color:#333;">${v.fsubscription.ftitle }</a>
										</td>
										<td>
											<font color="red">${v.fstatus_s }</font>
										</td>
										<td>
											${v.fsubscription.symbol }
										</td>
										<td>
											<fmt:formatNumber value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
										</td>
										<td>
											<fmt:formatNumber value="${v.flastcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
										</td>
										<td>
											<fmt:formatNumber value="${v.fprice }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
										</td>
										<td>
											<fmt:formatNumber value="${v.ftotalCost }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
										</td>
										<td class="text-right">${v.fcreatetime }</td>
									</tr>
								</c:forEach>
								
								<c:if test="${fn:length(subscriptionlogs)==0 }">
								<tr>
										<td colspan="10" class="no-data-tips">
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