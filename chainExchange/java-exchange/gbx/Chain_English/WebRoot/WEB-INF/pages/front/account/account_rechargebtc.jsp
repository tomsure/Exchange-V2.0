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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/recharge.css" type="text/css"></link>
</head>
<body>
	



 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
		<div class="row">
		
			<%@include file="../comm/left_menu.jsp" %>
			
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea recharge">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li>
								<a href="/account/rechargeCny.html?type=1">USD Deposit</a>
							</li>
							<c:forEach items="${requestScope.constant['allRechargeCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/rechargeBtc.html?symbol=${v.fid }">${v.fShortName } Deposit</a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30 recharge-qrcodetext">
							<div class="col-xs-2 text-right">
								<span>Deposit address</span>
							</div>
							<div class="col-xs-7 recharge-qrcodecon address">
								
									<span class="code-txt" id="address">
									
									<c:if test="${fvirtualaddress.fadderess == null}">
									    <a class="getCoinAddress opa-link" href="javascript:void(0);" data-fid="${fvirtualcointype.fid }">Get address</a>
									</c:if>
									<c:if test="${fvirtualaddress.fadderess != null}">
									     ${fvirtualaddress.fadderess}
									</c:if>
									</span>
									<c:if test="${fvirtualaddress.fadderess != null}">
									<span class="code-box">
										<span class="qrcode" id="qrcode"></span>
									</span>
									</c:if>
								
							</div>
						</div>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="panel panel-tips">
								<div class="panel-header text-center text-danger">
									<span class="panel-title">Notice</span>
								</div>
								<div class="panel-body">
									<p>Kindly email deposit@gbx.gi for any use enquiries.</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="panel border">
								<div class="panel-heading">
									<span class="text-danger">${fvirtualcointype.fShortName } Deposit Record</span>
								</div>
								<div  id="recordbody0" class="panel-body">
									<table class="table">
										<tr>
											<td>Time</td>
											<td>Address</td>
											<td>Number</td>
											<td>Confirmation Number</td>
											<td>Status</td>
										</tr>
										<c:forEach items="${fvirtualcaptualoperations }" var="v" varStatus="vs">
											<tr>
											    <td width="200">${v.flastUpdateTime }</td>
												<td width="350">${v.recharge_virtual_address }</td>
												<td width="100"><fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
												<td width="100">${v.fconfirmations }</td>
												<td width="100">${v.fstatus_s }</td>
											</tr>
										</c:forEach>
										<c:if test="${fn:length(fvirtualcaptualoperations)==0 }">
											<tr>
													<td colspan="5" class="no-data-tips" align="center">
														<span>
															No Record.
														</span>
													</td>
												</tr>
										</c:if>
										
									</table>
									
									<input type="hidden" value="${cur_page }" name="currentPage" id="currentPage"></input>
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
	</div>
	</div>




 <%@include file="../comm/footer.jsp" %>


<input type="hidden" id="address" value="${fvirtualaddress.fadderess}">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.recharge.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
		 if (navigator.userAgent.indexOf("MSIE") > 0) {
				jQuery('#qrcode').qrcode({
					text : '${fvirtualaddress.fadderess}',
					width : "149",
					height : "143",
					render : "table"
				});
			} else {
				jQuery('#qrcode').qrcode({
					text : '${fvirtualaddress.fadderess}',
					width : "149",
					height : "143"
				});
			}
		});
	</script>
</body>
</html>
