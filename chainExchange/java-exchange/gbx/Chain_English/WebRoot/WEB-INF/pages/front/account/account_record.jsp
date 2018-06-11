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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/accountrecord.css" type="text/css"></link>
</head>
<body>
	




  <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			<div class="row">
			<%@include file="../comm/left_menu.jsp" %>
			
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea record">
					<div class="col-xs-12 rightarea-con">
						<div class="panel-heading padding-bottom-clear">
							<div class="form-group">
								<span >Begin：</span>
								<input class="databtn datainput" id="begindate" value="${begindate }" readonly="readonly"></input>
								<span class="databtn datatips">To</span>
								<input class="databtn datainput" id="enddate" value="${enddate }" readonly="readonly"></input>
								<span class="databtn datatime ${datetype==1?'datatime-sel':'' }" data-type="1">Today</span>
								<span class="databtn datatime ${datetype==2?'datatime-sel':'' }" data-type="2">7 Days</span>
								<span class="databtn datatime ${datetype==3?'datatime-sel':'' }" data-type="3">15 Days</span>
								<span class="databtn datatime ${datetype==4?'datatime-sel':'' }" data-type="4">30 Days</span>
								<input type="hidden" id="datetype" value="${datetype }">
							</div>
							<div class="form-group">
								<span>Type：</span>
								<select class="form-control typeselect" id="recordType">
									<c:forEach items="${filters }" var="v">
									 	<c:choose>
									 		<c:when test="${select==v.value}">
												<option value="${v.key }" selected="selected">${v.value }</option>
									 		</c:when>
									 		<c:otherwise>
												<option value="${v.key }">${v.value }</option>
									 		</c:otherwise>
									 	</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div  id="fentrustsbody0">
							<table class="table table-striped">
								<tr class="bg-gary">
									<th width="200">
										Time
									</th>
									<th width="160">
										Type
									</th>
									<th width="120">
										Amount
									</th>
									<th width="120">
										Fee
									</th>
									<th width="120">
										Status
									</th>
								</tr>
								
								<c:choose>
						<c:when test="${recordType==1 || recordType==2 }">
							<%--人民币充值、提现--%>
							<c:forEach items="${list }" var="v">
							<tr>
								<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${select }</td>
								<td class="red">$<fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
								<td>$<fmt:formatNumber value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
								<td>${v.fstatus_s }</td>
							</tr>
							</c:forEach>
						</c:when>
						<c:when test="${recordType==3 || recordType==4 }">
							<%--充值、提现--%>
							<c:forEach items="${list }" var="v">
							<tr>
								<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${select }</td>
								<td class="red">${v.fvirtualcointype.fSymbol }<fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
								<td>${v.fvirtualcointype.fSymbol }<fmt:formatNumber value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
								<td>${v.fstatus_s }</td>
							</tr>
							</c:forEach>
						</c:when>
						<%-- <c:otherwise>
							交易
							<c:forEach items="${list }" var="v">
							<tr>
								<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${select }</td>
								<td class="red">${v.fvirtualcointype.fSymbol }<fmt:formatNumber value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
								<td class="red">￥<fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
								<td>
								<c:choose>
								<c:when test="${v.fentrustType==0 }">
								${v.fvirtualcointype.fSymbol }<fmt:formatNumber value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
								</c:when>
								<c:otherwise>
								￥<fmt:formatNumber value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
								</c:otherwise>
								</c:choose>
								</td>
								<td>
								￥<fmt:formatNumber value="${((v.fcount-v.fleftCount)==0)?0:(v.famount/(v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
								</td>
								<td>${v.fstatus_s }</td>
							</tr>
							</c:forEach>
						</c:otherwise> --%>
					    </c:choose>
									
								
						<c:if test="${fn:length(list)==0 }">		
							<tr>
								<td colspan="5" class="no-data-tips">
									<span >
										You don't have a bill for the moment.
									</span>
								</td>
							</tr>
						</c:if>
						
								
								</tbody>
							</table>
							
						</div>
						
<c:if test="${!empty(pagin) }">
<div class="text-right">
${pagin }
</div>
</c:if>						
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
 
<%@include file="../comm/footer.jsp" %>	


	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.record.js"></script>
</body>
</html>
