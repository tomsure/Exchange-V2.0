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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/index.css" type="text/css"></link>

</head>
<body>

 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			<div class="row">
<%@include file="../comm/left_menu.jsp" %>

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con ">
						<div class="index-top-icon clearfix">
							<div class="col-xs-4 top-icon">
								<div class="col-xs-12">
									<span class="assets-icon"><i class="gross"></i>	Total Assets($)</span>
								</div>
								<div class="col-xs-12" style="margin-top: 20px;">
									<span class="text-danger font-size-30">
									<fmt:formatNumber
										value="${totalCapitalTrade }" pattern="#,###.##"
										maxIntegerDigits="20" maxFractionDigits="4" />
									</span>
								</div>
								<span class="split"></span>
							</div>
							
							<div class="col-xs-4 top-icon">
								<div class="col-xs-12">
									<span class="assets-icon"><i class="net"></i>Available USD($)</span>
								</div>
								<div class="col-xs-12" style="margin-top: 20px;">
								<span class="text-danger font-size-30">
								<fmt:formatNumber
										value="${fwallet.ftotal }" pattern="#,###.##"
										maxIntegerDigits="20" maxFractionDigits="4" />
								</span>
								</div>
								<span class="split"></span>
							</div>
							<div class="col-xs-4 text-center top-btn">
								<a href="/account/rechargeCny.html" class="btn btn-re">Deposit</a>
								<a href="/account/withdrawCny.html" class="btn ">Withdraw</a>
							</div>
						</div>
						<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped text-center">
								<tr class="bg-gary">
									<td class="border-top-clear">Currency</td>
									<td class="border-top-clear">Available</td>
									<td class="border-top-clear">Frozen</td>
									<td class="border-top-clear">Total</td>
									<td class="border-top-clear">Valuation($)</td>
									<td class="border-top-clear">Action</td>
									<td class="border-top-clear"></td>
								</tr>
						<tr>
							<td class="border-top-clear">USD</td>
							<td class="border-top-clear"><fmt:formatNumber value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear"><fmt:formatNumber value="${fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear"><fmt:formatNumber value="${fwallet.ftotal+fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear"><fmt:formatNumber value="${fwallet.ftotal+fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear">
		                        <a href="/account/rechargeCny.html"><span class="label label-success">Deposit</span></a>
								<a href="/account/withdrawCny.html"><span class="label label-success">Withdraw</span></a>
                            </td>
                            <td class="border-top-clear">
                            </td>
						</tr>		
						<c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
							<tr>
							<td class="border-top-clear">${v.value.fvirtualcointype.fShortName }</td>
							<td class="border-top-clear"><fmt:formatNumber value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear"><fmt:formatNumber value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear"><fmt:formatNumber value="${v.value.ftotal+v.value.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear"><fmt:formatNumber value="${(v.value.ftotal+v.value.ffrozen)*v.value.fprice }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></td>
							<td class="border-top-clear">
							<c:choose>
							<c:when test="${v.value.fvirtualcointype.fisrecharge==true}">
							<a href="/account/rechargeBtc.html?symbol=${v.value.fvirtualcointype.fid }"><span class="label label-success">Deposit</span></a>
							</c:when>
							<c:otherwise>
							<span class="label label-default">Pause Deposit</span>
							</c:otherwise>
							</c:choose>
		                    <c:choose>
							<c:when test="${v.value.fvirtualcointype.FIsWithDraw==true}">
							<a href="/account/withdrawBtc.html?symbol=${v.value.fvirtualcointype.fid }"><span class="label label-success">Withdraw</span></a>
							</c:when>
							<c:otherwise>
							<span class="label label-default">Pause Withdraw</span>
							</c:otherwise>
							</c:choose>    					
                            </td>
                            
                            <%-- <td class="border-top-clear">
                            <a class="btn market-trading" href="/trade/coin.html?coinType=${v.value.ftradempingid }&tradeType=0">Trade Now<i class="iconfont">&#xe64f;</i></a>
                            </td> --%>
							</tr>
						</c:forEach>
								
							</table>
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