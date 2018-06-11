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
							<li class="active">
								<a href="/account/rechargeCny.html?type=1">USD Deposit</a>
							</li>
							<c:forEach items="${requestScope.constant['allRechargeCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/rechargeBtc.html?symbol=${v.fid }">${v.fShortName } Deposit</a>
								</li>
							</c:forEach>
							
						</ul>
						
						<%--<div class="col-xs-12 padding-clear padding-top-40">--%>
	<%--<!-- 						<a class="recharge-type alipay" href="/account/rechargeCny.html?type=3"> </a>--%>
							<%--<a class="recharge-type wechat" href="/account/rechargeCny.html?type=4"></a> -->--%>
							<%--<a class="recharge-type bank active" href="/account/rechargeCny.html?type=0"></a>--%>
						<%--</div>--%>
						
						<div class="col-xs-12 padding-clear padding-top-30" style="display:none;">
							<div class="recharge-box clearfix padding-top-30">
								<span class="recharge-process">
									<span id="rechargeprocess1" class="col-xs-3 active">
										<span class="recharge-process-line"></span>
										<span class="recharge-process-icon">1</span>
										<span class="recharge-process-text">Fill in the Amount</span>
									</span>
									<span id="rechargeprocess2" class="col-xs-3">
										<span class="recharge-process-line"></span>
										<span class="recharge-process-icon">2</span>
										<span class="recharge-process-text">Redirect to Bank Operations</span>
									</span>
									<span id="rechargeprocess4" class="col-xs-3">
										<span class="recharge-process-line"></span>
										<span class="recharge-process-icon">3</span>
										<span class="recharge-process-text">Complete Deposit</span>
									</span>
								</span>
								<div class="col-xs-12 padding-clear padding-top-30">
									<div id="rechage1" class="col-xs-7 padding-clear form-horizontal">
										<div class="form-group ">
											<label for="diyMoney" class="col-xs-3 control-label">USD Amount</label>
											<div class="col-xs-6">
												<input id="dollarMoney" class="form-control" type="text" onblur="addCNY();" onkeyup="addCNY();">
												<%--Current exchange rate:1$=${requestScope.constant['rate'] } ¥--%>
											</div>
										</div>
										<div class="form-group ">
											<label for="diyMoney" class="col-xs-3 control-label">Payment amount</label>
											<div class="col-xs-6">
												<input id="diyMoney" class="form-control" type="text" readonly="readonly">
												<input type="hidden" value=".${randomMoney }" id="random">
												<label for="diyMoney" class="control-label randomtips">.${randomMoney }</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-3 control-label"></label>
											<div class="col-xs-8">
												<font color="orange">
													* For quick arrival, please remit the amount according to the amount mentioned above, including two decimal places.
												</font>
											</div>
										</div>
										<div class="form-group">
											<label for="sbank" class="col-xs-3 control-label">Select bank</label>
											<div class="col-xs-6">
												<select id="sbank" class="form-control">
													<c:forEach items="${bankInfo }" var="v">
														<option value="${v.fid }">${v.fbankName }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label for="rechargebtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-6">
												<button id="rechargebtn" class="btn btn-danger btn-block">Deposit</button>
											</div>
										</div>
									</div>
									<div id="rechage2" class="col-xs-6 padding-clear form-horizontal" style="display:none;">
										<div class="form-group">
											<span>Please login your bank online banking, choose transfer remittance, or go to the bank counter for remittance, fill in the bank remittance information form according to the following content.</span>
										</div>
										<div class="form-group">
										<div class="recharge-infobox">
												<div class="form-group">
													<span>Payee：</span> <span id="fownerName">xx</span>
												</div>
												<div class="form-group">
													<span>Collection account：</span> <span id="fbankNumber">--</span>
												</div>
												<div class="form-group">
													<span>Bank account：</span> <span id="fbankAddress">--</span>
												</div>
												<div class="form-group">
													<span>Payment amount：</span> <span id="bankMoney" class="text-danger font-size-16">--</span>
												</div>
												<div class="form-group">
													<span>Note / postscript / abstract：</span> <span id="bankInfo" class="text-danger font-size-16">--</span>
												</div>
												<div class="form-group margin-bottom-clear">
													<span class="control-label text-left text-danger rechage-tips margin-bottom-clear" style="border-top-color:#fff0e4;line-height: 18px;padding-left: 0;display: inline-block;">
													<i class="icon"></i> Transfer must be completed <span id="bankInfotips">--</span>
													</span>
												</div>
											</div>
										 </div>	
										<div class="form-group">
										<label for="diyMoney" class="col-xs-3 control-label"></label>
											<div class="col-xs-6 padding-left-clear">
												<button id="rechargenextbtn" class="btn btn-danger btn-block">I have finished recharge, next step</button>
											</div>
										</div>
									</div>
									<div id="rechage3" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
										<div class="form-group ">
											<label for="fromBank" class="col-xs-3 control-label">Your remitting bank</label>
											<div class="col-xs-6">
												<select id="fromBank" class="form-control">
													<option value="-1">
														Choose Bank
													</option>
													<c:forEach items="${bankTypes }" var="v">
														<option value="${v.key }">${v.value }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group ">
											<label for="fromAccount" class="col-xs-3 control-label">Bank account</label>
											<div class="col-xs-6">
												<input id="fromAccount" class="form-control" type="text">
											</div>
										</div>
										<div class="form-group ">
											<label for="fromPayee" class="col-xs-3 control-label">Account name</label>
											<div class="col-xs-6">
												<input id="fromPayee" class="form-control" type="text">
											</div>
										</div>
										<div class="form-group ">
											<label for="fromPhone" class="col-xs-3 control-label">Phone</label>
											<div class="col-xs-6">
												<input id="fromPhone" class="form-control" type="text" value="">
											</div>
										</div>
										<div class="form-group">
											<label for="rechargesuccessbtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-6">
												<button id="rechargesuccessbtn" class="btn btn-danger btn-block">Submit</button>
											</div>
										</div>
									</div>
									<div id="rechage4" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
										<span class="rechare-success">
											<span class="success-icon">Submitted successfully！</span>
											<a href="/account/rechargeCny.html?type=0">Continue recharge >></a>
											<p>We will receive the remittance as soon as possible for you to deal with, please be patient.</p>
										</span>
									</div>
									<%--<div class="col-xs-5 padding-clear text-center">--%>
										<%--<a target="_blank"  href="/about/index.html?id=44" class="recharge-help"> </a>--%>
									<%--</div>--%>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel panel-tips">
									<div class="panel-header text-center text-danger">
										<span class="panel-title">Deposit Notes</span>
									</div>
									<div class="panel-body">
									    <p>&lt ${requestScope.constant['rechageBankDesc'] }</p>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel border">
									<div class="panel-heading">
										<span class="text-danger">USD Deposit Record</span>
										<span class="pull-right recordtitle" data-type="0" data-value="0">close -</span>
									</div>
									<div  id="recordbody0" class="panel-body">
										<table class="table">
											<tr>
												<td>Order</td>
												<td>Time</td>
												<td>Receive</td>
												<td>Type</td>
												<td>Amount($)</td>
												<td>Status</td>
												<td>Action</td>
											</tr>
											 <c:forEach items="${list}" var="v">
													<tr>
														<td class="gray">${v.fid }</td>
														<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td>${v.systembankinfo.fownerName }<br/>
																${v.systembankinfo.fbankNumber }<br/>
																${v.systembankinfo.fbankAddress}
														</td>
														<td>${v.fremittanceType }</td>
														<td>${v.famount }</td>
														<td>${v.fstatus_s }</td>
														<td class="opa-link">
														<c:if test="${(v.fstatus==1 || v.fstatus==2)}">
														<a class="rechargecancel opa-link" href="javascript:void(0);" data-fid="${v.fid }">Cancel</a>
														<c:if test="${v.fstatus==1}">
														&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link" href="javascript:void(0);" data-fid="${v.fid }">Submit</a>
														</c:if>
														</c:if>
														<c:if test="${(v.fstatus==3 || v.fstatus==4)}">
														--
														</c:if>
														</td>
									                 </tr>
									          </c:forEach>
											  <c:if test="${fn:length(list)==0 }">
												<tr>
													<td colspan="6" class="no-data-tips" align="center">
														<span>
															You don't have recharge data for the moment.
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
	<input type="hidden" value="${type }" name="finType" id="finType"></input>
	<input id="minRecharge" value="${minRecharge }" type="hidden">
	<input type="hidden" value="0" name="desc" id="desc"></input>


<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.recharge.js"></script>
	<script type="text/javascript">
		function addCNY()
		{
		    var rate = '${requestScope.constant['rate'] }';
		    var dollar = $("#dollarMoney").val();
		    var cny = dollar * rate;
            cny = cny.toFixed(0);
		    $("#diyMoney").val(cny);
		}
	</script>
</body>
</html>
