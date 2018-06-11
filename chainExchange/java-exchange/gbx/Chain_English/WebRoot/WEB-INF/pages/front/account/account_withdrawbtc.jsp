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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/withdraw.css" type="text/css"></link>
</head>
<body>
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawbtc'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawbtc'] }">


 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			<div class="row">
<%@include file="../comm/left_menu.jsp" %>

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea withdraw">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="">
								<a href="/account/withdrawCny.html">USD Withdrawal</a>
							</li>
							<c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/withdrawBtc.html?symbol=${v.fid }">${v.fShortName } Withdrawal</a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="col-xs-7 padding-clear form-horizontal">
								<div class="form-group ">
									<label for="withdrawAmount" class="col-xs-3 control-label">Account Balance</label>
									<div class="col-xs-6">
										<span class="form-control border-fff"><fmt:formatNumber value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
									</div>
								</div>
								<div class="form-group ">
									<label for="withdrawAddr" class="col-xs-3 control-label">Address</label>
									<div class="col-xs-6">
										<select id="withdrawAddr" class="form-control">
											<c:forEach items="${fvirtualaddressWithdraws }" var="v">
												<option value="${v.fid }">${v.fremark}-${v.fadderess }</option>
											</c:forEach>
										</select>
										<a href="#" data-toggle="modal" data-target="#address">Add Wallet Address>></a>
									</div>
								</div>
								<div class="form-group ">
									<label for="withdrawAmount" class="col-xs-3 control-label">Amount</label>
									<div class="col-xs-6">
										<input id="withdrawAmount" class="form-control" type="text">
									</div>
								</div>
								<div class="form-group ">
									<label for="password" class="col-xs-3 control-label">Transaction Password</label>
									<div class="col-xs-6">
										<input id="tradePwd" class="form-control" type="password">
									</div>
								</div>
							
							<c:if test="${isBindTelephone == true }">		
									<div class="form-group">
										<label for="withdrawPhoneCode" class="col-xs-3 control-label">SMS Authentication Code</label>
										<div class="col-xs-6">
											<input id="withdrawPhoneCode" class="form-control" type="text">
											<button id="withdrawsendmessage" data-msgtype="5" data-tipsid="withdrawerrortips" class="btn btn-sendmsg">Send</button>
										</div>
									</div>
							</c:if>		
								
								<c:if test="${isBindGoogle ==true}">
									<div class="form-group">
										<label for="withdrawTotpCode" class="col-xs-3 control-label">Google Verification Code</label>
										<div class="col-xs-6">
											<input id="withdrawTotpCode" class="form-control" type="text">
										</div>
									</div>
								</c:if>	
								
								<div class="form-group">
									<label for="diyMoney" class="col-xs-3 control-label"></label>
									<div class="col-xs-6">
										<span id="withdrawerrortips" class="text-danger">
											
										</span>
									</div>
								</div>
								<div class="form-group">
									<label for="diyMoney" class="col-xs-3 control-label"></label>
									<div class="col-xs-6">
										<button id="withdrawBtcButton" class="btn btn-danger btn-block">Withdrawal </button>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel panel-tips">
									<div class="panel-header text-center text-danger">
										<span class="panel-title">Notice</span>
									</div>
									<div class="panel-body">
									    <p>Kindly email withdrawal@gbx.gi for any use enquiries.</p>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel border">
									<div class="panel-heading">
										<span class="text-danger">${fvirtualcointype.fShortName } Record</span>
										<span class="pull-right recordtitle" data-type="0" data-value="0">close -</span>
									</div>
									<div  id="recordbody0" class="panel-body">
										<table class="table">
											<tr>
												<td>
													Time
												</td>
												<td>
													Address
												</td>
												<td>
													Amount
												</td>
												<td>
													Fee
												</td>
												<td>
													Status
												</td>
												<td>
													Remark
												</td>
											</tr>
											
						<c:forEach items="${fvirtualcaptualoperations }" varStatus="vs" var="v">
							<tr>
								<td width="170"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td width="300">${v.withdraw_virtual_address }</td>
								<td width="120">${fvirtualcointype.fSymbol}${v.famount }</td>
								<td width="120">${fvirtualcointype.fSymbol}${v.ffees }</td>
								<td width="180"  class="opa-link">${v.fstatus_s }
								<c:if test="${v.fstatus==1 }">
								&nbsp;|&nbsp;
									<a class="cancelWithdrawBtc opa-link" href="javascript:void(0);" data-fid="${v.fid }">Cancel</a>
									</c:if>
								</td>
								<td width="100">
									${v.fid }
								</td>
							</tr>
						</c:forEach>
						
						<c:if test="${fn:length(fvirtualcaptualoperations)==0 }">
								<tr>
									<td colspan="6" class="no-data-tips">
										<span>
											You have no record of withdrawals.
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
	</div>
	<div class="modal modal-custom fade" id="address" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">Withdrawal Address</span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="withdrawBtcAddr" class="col-xs-3 control-label">Address</label>
						<div class="col-xs-8">
							<input id="withdrawBtcAddr" class="form-control" type="text">
						</div>
					</div>
					<div class="form-group ">
						<label for="withdrawBtcRemark" class="col-xs-3 control-label">User ID</label>
						<div class="col-xs-8">
							<input id="withdrawBtcRemark" class="form-control" type="" text>
						</div>
					</div>
					<div class="form-group ">
						<label for="withdrawBtcPass" class="col-xs-3 control-label">Transaction password</label>
						<div class="col-xs-8">
							<input id="withdrawBtcPass" class="form-control" type="password">
						</div>
					</div>

				<c:if test="${isBindTelephone == true &&  isBindGoogle ==true}">
					<div class="form-group">
						<label for="withdrawBtcAddrPhoneCode" class="col-xs-3 control-label">SMS authentication code</label>
						<div class="col-xs-8">
							<input id="withdrawBtcAddrPhoneCode" class="form-control" type="text">
							<button id="bindsendmessage" data-msgtype="8" data-tipsid="binderrortips" class="btn btn-sendmsg">Send</button>
						</div>
					</div>
				</c:if>
				<c:if test="${isBindTelephone != true ||  isBindGoogle !=true}">
					<c:if test="${isBindTelephone == true }">
						<div class="form-group">
							<label for="withdrawBtcAddrPhoneCode" class="col-xs-3 control-label">SMS authentication code</label>
							<div class="col-xs-8">
								<input id="withdrawBtcAddrPhoneCode" class="form-control" type="text">
								<button id="bindsendmessage" data-msgtype="8" data-tipsid="binderrortips" class="btn btn-sendmsg">Send</button>
							</div>
						</div>
					</c:if>

					<c:if test="${isBindGoogle ==true}">
						<div class="form-group">
							<label for="withdrawBtcAddrTotpCode" class="col-xs-3 control-label">Google verification code</label>
							<div class="col-xs-8">
								<input id="withdrawBtcAddrTotpCode" class="form-control" type="text">
							</div>
						</div>
					</c:if>
				</c:if>

					<div class="form-group">
						<label for="diyMoney" class="col-xs-3 control-label"></label>
						<div class="col-xs-8">
							<span id="binderrortips" class="text-danger"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="diyMoney" class="col-xs-3 control-label"></label>
						<div class="col-xs-8">
							<button id="withdrawBtcAddrBtn" class="btn btn-danger btn-block">Submit</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" id="isEmptyAuth" value="${isEmptyAuth }">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<input type="hidden" value="<fmt:formatNumber value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>" id="btcbalance" name="btcbalance">
	<input type="hidden" value="${fvirtualcointype.fShortName }" id="coinName" name="coinName">

 
<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.withdraw.js"></script>
</body>
</html>
