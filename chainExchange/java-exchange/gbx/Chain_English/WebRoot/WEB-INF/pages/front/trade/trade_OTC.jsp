<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link href="${oss_url}/static/front/css/trade/trade.css" rel="stylesheet" type="text/css" media="screen, projection" />
</head>
<body>
	
<style>	
.trade{ margin-top: 20px !important;}
.trade *{ box-sizing: border-box; margin-top: 0px !important; margin-bottom: 20px !important;}
.trade .trade-body{height:auto; overflow: hidden; float: left; width: 64.66%; padding-right: 10px;}
.trade table{ width: 100%;}
.trade table thead {
    background-color: #000000 !important;
}
.trade table thead tr th {
    background-color: black !important;
    color: white !important;
    text-align: center;
}
.trade table tr{ border-bottom: none;}
.trade td{
	text-align: center !important;
}
.trade .trade-body .trade-area {
	width: 100%;
	height: auto;
	float: left;
	padding: 25px 5px 22px 5px;
	background: #f6f9fc;
}

.trade .trade-body .trade-buysell {
	width: 47.5%;
	float: left;
}

.trade .trade-body .trade-disk {
	width: 5%;
	float: left;
	height: 10px;
}

.trade-buysell a {
	line-height: 31px;
}

.trade .trade-body .trade-buysell label {
	font-weight: normal;
}

.trade .trade-body .trade-group {
	margin-bottom: 10px;
	position: relative;
}

.trade .trade-body .trade-inputtips {
	position: absolute;
	left: 0;
	top: 0;
	padding-left: 30px;
	line-height: 45px;
	color: #b7b7b7;
	cursor: auto;
	font-size: 12px;
	margin-bottom: 0;
}

.trade .trade-body .trade-input {
	color: #333;
	border-radius: 0;
	text-align: right;
	padding-left: 100px;
	display: block;
	width: 100%;
	height: 45px;
	line-height: 45px;
	padding-right: 20px;
	outline: none;
	transition: all 0.3s;
	border: 1px solid #edf3f6;
	background: #fff;
}

.trade .trade-body .trade-input:hover, .trade .trade-body .trade-input:focus {
	box-shadow: 0 0 2px 0 rgba(98, 135, 175, 1);
}

.trade .trade-body .trade-error {
	height: 16px;
	display: block;
	position: relative;
	top: -8px;
}

.trade .trade-body .trade-btn {
	height: 35px;
	line-height: 35px;
	padding: 0;
}
.redtips {
    color: #c83935 !important;
}
.greentips {
    color: #009900 !important;
}

/*-------------------------------*/	
	
.trade-amount .datatime-sel, .trade-amount .datatime:hover {
    background: #41c7f9 none repeat scroll 0 0;
    color: #fff;
}
.trade-amount .databtn {
    cursor: pointer;
    display: inline-block;
    margin: 0 10px;
    padding: 5px;
    height: 55px;
    width:40px;
}
.panel-body table tr:first-child {
    border-bottom: 0px solid #dddddd;
}

.drop-down-menu {
	display: none;
	position: absolute;
	float: right;
	top: 46px;
	right: 11px;
	left: auto;
	margin: 0;
	padding: 5px;
	min-width: 84px;
	line-height: 14px;
	background-color: #fff;
	border: 1px solid #ccc;
	border: 1px solid rgba(0, 0, 0, 0.2);
	z-index: 1021
}

.drop-down-menu:before {
	display: inline-block;
	position: absolute;
	top: -7px;
	right: 12px;
	left: auto;
	border-left: 7px solid transparent;
	border-right: 7px solid transparent;
	border-bottom: 7px solid #ccc;
	border-bottom-color: rgba(0, 0, 0, 0.2);
	content: ''
}

.drop-down-menu:after {
	display: inline-block;
	position: absolute;
	top: -6px;
	left: auto;
	right: 13px;
	border-left: 6px solid transparent;
	border-right: 6px solid transparent;
	border-bottom: 6px solid white;
	content: ''
}

.drop-down-menu .drop-down-menu-item {
	display: inline-block;
	line-height: 20px;
	padding: 5px;
	white-space: nowrap;
	width: 100%;
	color: black;
	font-weight: normal;
	text-align: center;
	text-decoration: none
}

.drop-down-menu .drop-down-menu-item:hover {
	background-color: #47C5D5;
	color: white
}

.drop-down-menu .drop-down-menu-item .drop-down-menu-item-description {
	font-style: italic;
	color: #ccc;
	font-size: 12px;
}

#b-menu{ position: relative;}
#b-menu:hover .drop-down-menu {
	display: inline-block
}

.col-xs-12{ padding-left: 0px !important;}

.text-color1{ color: #e85600 !important;}
.trade .trade-body .trade-bar .marker-top {background: #029500 !important;}
.trade .trade-body .trade-bar .marker .circle {color: #029500 !important;}
.btn-danger { background-color: #e85600 !important; border: none !important;}
.btn-danger:hover{ border: none !important;}

.text-color2{ color: #029500 !important;}
.trade .trade-bar .sell-marker .marker-top { background: #e85600 !important;}
.trade .trade-body .trade-bar .sell-marker .circle{ color: #e85600 !important;}
.btn-success{ background-color: #029500 !important; border: none !important;}
.btn-success:hover{ border: none !important;}

</style>

<jsp:include page="../comm/header.jsp"></jsp:include>


	<div class="container-full">
		<div class="container">


			<div class="row">
	            <div class="col-xs-12 padding-clear">
		            <div class="now_box" id="now_box" data-cid="16" data-hid="">
						<div class="now_part" style="cursor: pointer;" id="b-menu">
							<b>
								<img src="${ftrademapping.fvirtualcointypeByFvirtualcointype2.furl }">
								 ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName } &#8594 ${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }
							</b>
							<b class="caret" style="vertical-align: middle;"></b>
							<div class="drop-down-menu">
	                            <c:forEach var="v" varStatus="vs" items="${ftrademappings }">
	                            	<a href="/trade/coin.html?coinType=${v.fid }&tradeType=0" class="drop-down-menu-item ${v.fid==ftrademapping.fid?'active':'' }">
										<i class="deal_list_pic">
											<img src="${v.fvirtualcointypeByFvirtualcointype2.furl }" />
										</i>
										${v.fvirtualcointypeByFvirtualcointype1.fShortName } &#8594 ${v.fvirtualcointypeByFvirtualcointype2.fShortName }
	                            	</a>
	                             </c:forEach>
	                        </div>
						</div>
						<div class="now_part"><span>Last</span>
					    <b id="lastprice">-</b></div>
					    <div class="now_part"><span>Low</span> <b id="now_low">-</b></div>
					    <div class="now_part"><span>High</span> <b id="now_high">-</b></div>
					    <div class="now_part"><span>Vol</span> <b id="now_total">-</b></div>
				    </div>
	            </div>
	        </div>
	
	
	
			<div class="row">
	
				<div class="col-xs-12 padding-right-clear" style="padding-left: 0px !important;">
	
					<div class="row" style="display: none;">
						<div class="alert alert-danger">
							<a href="#" class="close" data-dismiss="alert">×</a>
							<strong>tips</strong>:${ftrademapping.ftradedesc }<br>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 mkline padding-clear">
						<div class="panel panel-default">
							<div id="collapseOne" class="panel-collapse collapse in">
								<div class="panel-body" style="padding:0px;">
									<div style="float: right"><a class="pull-right" href="/trademarket.html?symbol=${coinType }" style="font-weight:bold;line-height:40px;font-size:18px;" >Pro. Version&gt;&gt;</a></div>
								<div id="graphbox" data-highcharts-chart="0" style="position: relative; overflow: hidden; width: 100%; height: 475px;">
								<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen1" src="/kline/fullstart.html?symbol=${ftrademapping.fid }&themename=dark"></iframe>
								</div>
								</div>
							</div>
						</div>
						</div>
					</div>
	
	
					<div class="trade" style="overflow: hidden;">
						<div class="trade-body ">
							<div class="trade-area">
								<div class="trade-buysell">
									<div class="trade-group">
										<span>
											Balance
										</span>
										<span id="toptradecnymoney" class="text-color2">0</span>
										<span class="text-color2">${coin1.fShortName }</span>
									</div>
									<div class="trade-group">
										<label for="tradebuyprice" class="trade-inputtips">
											Bid ${coin2.fShortName }/${coin1.fShortName }</label>
										<input id="tradebuyprice" class="trade-input" value="${recommendPrizebuy }">
										<span>Max. Available ${coin2.fShortName } <font class="text-color2" id="maxBuy">0.0000</font></span>
									</div>
									<div class="trade-group">
										<label for="tradebuyamount" class="trade-inputtips">
											${coin2.fShortName }</label>
										<input id="tradebuyamount" class="trade-input">
									</div>
									<div class="trade-group">
										<div id="buyBar" class="trade-bar">
											<div class="trade-barbox">
												<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete" data-param-type="0" data-param-markertop="marker-top">
													<div class="complete" style="width: 2px;"></div>
												</div>
												<div class="slider-points">
													<div class="proportioncircle proportion0" data-points="0">0%</div>
													<div class="proportioncircle proportion1" data-points="20">20%</div>
													<div class="proportioncircle proportion2" data-points="40">40%</div>
													<div class="proportioncircle proportion3" data-points="60">60%</div>
													<div class="proportioncircle proportion4" data-points="80">80%</div>
													<div class="proportioncircle proportion5" data-points="100">100%</div>
												</div>
											</div>
										</div>
									</div>
									<div class="trade-group">
										<span class="trade-input">
											<label class="trade-inputtips">
												Volume
											</label>
											<span id="tradebuyTurnover">0</span>
											${coin1.fShortName }
											<br/>Fee ${ftrademapping.ftradedesc }
										</span>
									</div>
									<div class="trade-group">
										<br/>
										<button id="buybtn" class="btn btn-success btn-block trade-btn">
											Buy ${coin2.fShortName }</button>
										<span id="buy-errortips" class="text-color2 trade-error"></span>
									</div>
									<div class="trade-group">
										<c:choose>
											<c:when test="${coin1.ftype==0 }">
												<a href="/account/rechargeCny.html" class="text-color2">Deposit<i class="arrow-icon-small text-color2"></i></a>
											</c:when>
											<c:otherwise>
												<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="text-color1">Deposit<i class="arrow-icon-small text-color1"></i></a>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="trade-disk"></div>
								<div class="trade-buysell">
									<div class="trade-group">
										<span>
											 Balance
										</span>
										<span id="toptrademtccoin" class="text-color1">0</span>
										<span class="text-color1">${coin2.fShortName }</span>
									</div>
									<div class="trade-group">
										<label for="tradesellprice" class="trade-inputtips">
											Ask ${coin2.fShortName }/${coin1.fShortName }</label>
										<input id="tradesellprice" class="trade-input" value="${recommendPrizesell }">
										<span>Max. Available ${coin1.fShortName } <font class="text-color1" id="maxSell">0.0000</font></span>
									</div>
									<div class="trade-group">
										<label for="tradesellamount" class="trade-inputtips">
											${coin2.fShortName }</label>
										<input id="tradesellamount" class="trade-input">
									</div>
									<div class="trade-group">
										<div id="buyBar" class="trade-bar">
											<div class="trade-barbox">
												<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker" data-param-complete="complete" data-param-type="1" data-param-markertop="marker-top"><div class="complete" style="width: 2px;"></div></div>
												<div class="slider-points">
													<div class="proportioncircle proportion0" data-points="0">0%</div>
													<div class="proportioncircle proportion1" data-points="20">20%</div>
													<div class="proportioncircle proportion2" data-points="40">40%</div>
													<div class="proportioncircle proportion3" data-points="60">60%</div>
													<div class="proportioncircle proportion4" data-points="80">80%</div>
													<div class="proportioncircle proportion5" data-points="100">100%</div>
												</div>
											</div>
										</div>
									</div>
									<div class="trade-group">
										<div class="trade-input">
											<label class="trade-inputtips">
												Volume
											</label>
											<span id="tradesellTurnover">0</span>
											${coin1.fShortName }
											<br/>Fee ${ftrademapping.ftradedesc }
										</div>
									</div>
									<div class="trade-group">
										<br/>
										<button id="sellbtn" class="btn btn-danger btn-block trade-btn">
											Sell ${coin2.fShortName }</button>
										<span id="sell-errortips" class="text-color1 trade-error"></span>
									</div>
									<div class="trade-group">
											<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="text-color1">Deposit<i class="arrow-icon-small text-color1"></i></a>
										</a>
									</div>
								</div>
							</div>
						</div>
						
						
						<div id="coinBoxbuybtc" style="width: 33.33%; float: right;">
							<c:if test="${isLimittrade == true}">
							    <span class="trade-depth">
							           Surged Limit：<span style="color:red;">${coin1.fSymbol }<fmt:formatNumber
											value="${upPrice }" pattern="##.##" maxIntegerDigits="15"
											maxFractionDigits="${ftrademapping.fcount2}" /> </span>, Decline Limit：<span  style="color:red;">${coin1.fSymbol }<fmt:formatNumber
											value="${downPrice }" pattern="##.##" maxIntegerDigits="15"
											maxFractionDigits="${ftrademapping.fcount1}" /> </span>
							    </span>
							</c:if>
							<div class="trade" style="margin-bottom: 0px !important;">
								<table class="table2 table-striped table-hover table-condensed border-light-grey trading-orderbook" style="margin-bottom: 0px !important;">
									<thead>
										<tr class="row-head row-narrow row-align-right regular-font-size">
											<th width="10%" style="text-align: right !important;">Buy/Sell</th>
											<th width="20%" style="text-align: right !important;">Price</th>
											<th width="35%" style="text-align: right !important;">Quantity</th>
											<th width="35%" style="text-align: right !important;">Amount</th>
										</tr>
									</thead>
								</table>
							</div>
							<ul id="sellbox" class="list-group first-child" style="line-height: 5px;">
							</ul>
							<ul id="buybox" class="list-group " style="line-height: 5px;">
							</ul>
 								
 							<style>
 								#logbox,#sellbox,#buybox{ box-sizing: border-box !important; padding: 0px 10px !important; margin: 0px !important;}
 									#logbox li,#sellbox li,#buybox li{ height: 28px !important; line-height: 28px !important; margin: 0px !important;}
 									#logbox li *,#sellbox li *,#buybox li *{ margin: 0px !important;}
 							</style>
							<h4 style="margin-top: 20px !important;">
								<font style="vertical-align: inherit;">
								<font style="vertical-align: inherit;">Trading Record</font></font>
							</h4>
							<ul id="logbox" class="list-group " style="line-height: 5px;">
 												
							</ul>
						</div>

						<div id="entrustInfo" style="width: 64%; float: left;"></div>
					</div>


				
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">Transaction password</span>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="password" class="form-control" id="tradePwd" placeholder="Please enter the transaction password">
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalbtn" type="button" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="entrustsdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"></span>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">close</button>
				</div>
			</div>
		</div>
	</div>
	<input id="coinshortName" type="hidden" value="${coin2.fShortName }">
	<input id="symbol" type="hidden" value="${ftrademapping.fid }">
	<input id="isopen" type="hidden" value="${needTradePasswd }">
	<input id="tradeType" type="hidden" value="0">
	<input id="userid" type="hidden" value="${userid }">

	<input id="tradePassword" type="hidden" value="${isTradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">


    <input id="coinCount1" type="hidden" value="${ftrademapping.fcount1 }">
    <input id="coinCount2" type="hidden" value="${ftrademapping.fcount2 }">
    <input id="minBuyCount" type="hidden" value="<fmt:formatNumber
									value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15"
									maxFractionDigits="8" />">
    <input id="limitedType" type="hidden" value="0">
<input id="fhasRealValidate" type="hidden" value="${fuser.fhasRealValidate }">

	<jsp:include page="../comm/footer.jsp"></jsp:include>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/trade/tradeEng.js?r=<%=new java.util.Date().getTime() %>"></script>
<script>
    function maxBuy() {
        var toptradecnymoney = $("#toptradecnymoney").html();
        var tradebuyprice = $("#tradebuyprice").val();
        var maxBuy = (toptradecnymoney/tradebuyprice).toFixed(4);
        $("#maxBuy").html(maxBuy);
    }
    function maxSell() {
        var toptrademtccoin = $("#toptrademtccoin").html();
        var tradesellprice = $("#tradesellprice").val();
        var maxSell = (toptrademtccoin*tradesellprice).toFixed(4);
        $("#maxSell").html(maxSell);
    }
</script>
</body>
</html>

