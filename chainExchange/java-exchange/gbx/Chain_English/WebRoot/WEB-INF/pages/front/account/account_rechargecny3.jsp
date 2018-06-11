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
								<a href="/account/rechargeCny.html?type=1">人民币充值</a>
							</li>
							<c:forEach items="${requestScope.constant['allRechargeCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/rechargeBtc.html?symbol=${v.fid }">${v.fname } 充值</a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-40">

							<a class="recharge-type alipay  active" href="/account/rechargeCny.html?type=3"> </a>
						 	<a class="recharge-type wechat" href="/account/rechargeCny.html?type=4"></a>
							<a class="recharge-type bank" href="/account/rechargeCny.html?type=0"></a>
						</div>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="recharge-box clearfix ">
								<div class="col-xs-12 padding-clear padding-top-30">
									<div id="rechage1" class="col-xs-7 padding-clear form-horizontal">
										<div class="form-group ">
											<label for="diyMoney" class="col-xs-3 control-label">转账金额</label>
											<div class="col-xs-7">
												<input id="diyMoney" class="form-control" type="text" autocomplete="off">
												<input type="hidden" value="0.${randomMoney }" id="random">
												<label for="diyMoney" class="control-label randomtips">.${randomMoney }</label>
											</div>
										</div>
										<!-- <div class="form-group">
											<label for="sbank" class="col-xs-3 control-label">支付宝帐号</label>
											<div class="col-xs-7">
												<input id="accounts" class="form-control" type="text" autocomplete="off">
											</div>
										</div> -->
										<div class="form-group">
											<label for="sbank" class="col-xs-3 control-label">验证码</label>
											<div class="col-xs-7">
												<input id="imgcode" class="form-control" type="text" autocomplete="off">
												<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
											</div>
										</div>
										
										
										<div class="form-group">
											<label for="rechargebtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-7">
												<span class="text-danger" id="errortips"></span>
											</div>
										</div>
										<div class="form-group">
											<label for="rechargebtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-7">
												<button id="qrcoderechargebtn" class="btn btn-danger btn-block">确定充值</button>
											</div>
										</div>
									</div>
									<div class="col-xs-5 padding-clear text-center">
										<a target="_blank" href="/about/index.html?id=44" class="recharge-help"> </a>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30 padding-bottom-30">
								<div class="panel panel-tips">
									<div class="panel-header text-center text-danger">
										<span class="panel-title">充值须知</span>
									</div>
									<div class="panel-body">
									    <p>&lt ${requestScope.constant['rechageZFBDesc'] }</p>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel border">
									<div class="panel-heading">
										<span class="text-danger">人民币充值记录</span>
										<span class="pull-right recordtitle" data-type="0" data-value="0">收起 -</span>
									</div>
									<div  id="recordbody0" class="panel-body">
										<table class="table">
											<tr>
												<td>订单号</td>
												<td>充值时间</td>
												<td>充值方式</td>
												<td>充值金额(￥)</td>
												<td>状态</td>
											</tr>
											 <c:forEach items="${list}" var="v">
													<tr>
														<td class="gray">${v.fid }</td>
														<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td>${v.fremittanceType }</td>
														<td>${v.famount }</td>
														<td>${v.fstatus_s }</td>
									                 </tr>
									          </c:forEach>
											  <c:if test="${fn:length(list)==0 }">
												<tr>
													<td colspan="6" class="no-data-tips" align="center">
														<span>
															您暂时没有充值数据
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
	
<div class="modal modal-custom fade in" id="rechargeDialog" tabindex="-1" role="dialog" style="display: none; padding-right: 17px;">
		<div class="modal-dialog" role="document">
			<div class="modal-mark" style="height: 373px; width: 575px;"></div>
			<div class="modal-content" style="margin-top: 136px;">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title"> 支付宝转账 </span>
				</div>
				<div class="modal-body">
					<div class="recharge-dialog">
						<div class="col-xs-4">
							<img class="wechatqrcode" alt="" src="${requestScope.constant['zfbImages'] }">
						</div>
						<div class="col-xs-8">
							<span class="title">扫描左侧二维码<font color="red">(转账备注：<span id="payId"></span>)</font></span>
							<div class="content">
								<span>转账金额：<span class="text-danger" id="rechargeDialogAmount">xxx</span></span>
								 <span>姓名：<span>${requestScope.constant['zfbName'] }</span></span>
								  <span>支付宝账号：<span>${requestScope.constant['zfbNumber'] }</span></span>
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
<input type="hidden" id="refreshid" value="0">
<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.recharge.js"></script>
</body>
</html>
