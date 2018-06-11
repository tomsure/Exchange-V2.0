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
<link rel="stylesheet" href="${oss_url}/static/front/css/finance/accountassets.css" type="text/css"></link>
</head>
<body>
	




  <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			<div class="row">
<%@include file="../comm/left_menu.jsp" %>

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea assets">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="">
								<a href="/financial/accountbank.html">Bank Card Management</a>
							</li>
							<!-- <li>
								<a href="/financial/accountalipay.html">支付宝管理</a>
							</li> -->
							
							<c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/financial/accountcoin.html?symbol=${v.fid }">${v.fShortName } Withdrawal Management</a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
							
							<c:forEach items="${alls }" var="v">
								<div class="col-xs-6">
									<div class="coin-item">
										<div class="coin-item-top">
											<div class="col-xs-11">
												<p>${v.fadderess }</p>
											</div>
											<div class="col-xs-1 text-center padding-clear">
												<span class="coin-item-del" data-fid="${v.fid }"></span>
											</div>
										</div>
										<div class="coin-item-bot">
											<div class="col-xs-12">
												<span style="z-index: 910;" class="coin-item-code">
													<span class="addresscode" data-text="${v.fadderess }" data-fid="${v.fid }">
														<span class="qrcode" id="qrcode${v.fid }"></span>
													</span>
													${v.fremark }
												</span>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>	
							
							<div class="col-xs-6">
								<div class="bank-add coin-add text-center" data-toggle="modal" data-target="#addCoinAddress">
									<span class="icon"></span>
									<br>
									<span>Add Address</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="modal modal-custom fade" id="addCoinAddress" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
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
	



<%@include file="../comm/footer.jsp" %>	

	<input type="hidden" id="symbol" value="${symbol }">
	<input type="hidden" value="${coinName }" id="coinName" name="coinName">
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.assets.js"></script>
</body>
</html>
