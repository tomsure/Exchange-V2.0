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

<link rel="stylesheet" href="${oss_url}/static/front/css/market/trademarket.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/market/loader.css" type="text/css"></link>

</head>
<body style="background: #1e1e1e;">
	<div id="loader-wrapper">
		<div class="loader-inner ball-scale-ripple-multiple">
			<div></div>
			<div></div>
			<div></div>
		</div>
		<div class="loader-section section-left"></div>
		<div class="loader-section section-right"></div>
	</div>
	<div class="container-full market-head">
		<h1 class="head-logo">
			<a class="marketplist login" href="/"></a>
		</h1>
		<div class="head-nav">
		  <div name="tradeType1" id="tradeType1" class="tradeType">
			<div>All</div>
			<ul class="list-unstyled">
			<c:forEach items="${ftrademappings }" var="v">
			<c:if test="${v.ftype ==1 }">
					<li class="tradeTypeItem">
						<a href="/trademarket.html?symbol=${v.fid }">
						${v.fvirtualcointypeByFvirtualcointype1.fShortName }&#8594${v.fvirtualcointypeByFvirtualcointype2.fShortName }</a>
					</li>
			</c:if>		
			</c:forEach>	
			</ul>
		</div>

		<%--<div name="tradeType1" id="tradeType1" class="tradeType">--%>
			<%--<div>币创板</div>--%>
			<%--<ul class="list-unstyled">--%>
			<%--<c:forEach items="${ftrademappings }" var="v">--%>
			<%--<c:if test="${v.ftype ==2 }">--%>
					<%--<li class="tradeTypeItem">--%>
						<%--<a href="/trademarket.html?symbol=${v.fid }">${v.fvirtualcointypeByFvirtualcointype2.fname }/${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a>--%>
					<%--</li>--%>
			<%--</c:if>		--%>
			<%--</c:forEach>		--%>
			<%--</ul>--%>
		<%--</div>--%>
		<%----%>
		<%--<div name="tradeType2" id="tradeType2" class="tradeType">--%>
			<%--<div>考察区</div>--%>
			<%--<ul class="list-unstyled">--%>
			<%--<c:forEach items="${ftrademappings }" var="v">--%>
			<%--<c:if test="${v.ftype ==3 }">--%>
					<%--<li class="tradeTypeItem">--%>
						<%--<a href="/trademarket.html?symbol=${v.fid }">${v.fvirtualcointypeByFvirtualcointype2.fname }/${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a>--%>
					<%--</li>--%>
			<%--</c:if>		--%>
			<%--</c:forEach>		--%>
			<%--</ul>--%>
		<%--</div>--%>

		<span class="tradeTypeStatus">
			current：${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }
		</span>
		</div>

		<c:if test="${sessionScope.login_user == null }">
			<div class="head-login">
				<input id="login_acc" placeholder="Email/Mobile">
				<input id="login_pwd" placeholder="Password" type="password">
				<button id="login_sub">Sign in</button>
		    </div>
		</c:if>
		
		<c:if test="${sessionScope.login_user != null }">
		<div class="head-login">
				<span>Hello，${login_user.fnickName}</span>
				<span>|</span>
				<a id="login_logout" href="/user/logout.html" style="color:#fff;">Logout</a>
		</div>
		</c:if>
	</div>
		<div class="container-full clearfix">
		<div id="marketLeft" class="market-left">
			<div id="marketStart" class="market-start">
				<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/fullstart.html?symbol=${symbol }&themename=dark"></iframe>
			</div>
			
				<div id="marketEntruts" class="market-entruts">
					<div id="entrutsHead" class="entruts-head">
						<span class="entruts-head-nav-full" data-show="entrutsCur" data-hide="entrutsHis">Current entrustment</span><span class="entruts-head-nav-full" data-show="entrutsHis" data-hide="entrutsCur">Historical entrustment</span>
					</div>
					<div class="entruts-data" id="entrutsCur">
						<div class="entruts-data-head">
							<span class="col-1">Time</span>
							<span class="col-2">Type</span>
							<span class="col-3">Price</span>
							<span class="col-3">Quantity</span>
							<span class="col-3">Qty. Transacted</span>
							<span class="col-3">Status</span>
							<span class="col-2">Action</span>
						</div>
						<div class="entruts-data-data" id="entrutsCurData"></div>
					</div>
					<div class="entruts-data" id="entrutsHis">
						<div class="entruts-data-head">
							<span class="col-1">Time</span>
							<span class="col-2">Type</span>
							<span class="col-3">Price</span>
							<span class="col-3">Quantity</span>
							<span class="col-3">Qty. Transacted</span>
							<span class="col-3">Status</span>
						</div>
						<div class="entruts-data-data" id="entrutsHisData"></div>
					</div>
				</div>
			
		</div>
		<div class="market-right">
			<div id="marketData" class="market-data">
				<div class="market-depth">
					<div class="market-depth-head">
						<span class="depth-des">&nbsp;</span>
						<span class="depth-price">Price($)</span>
						<span class="depth-amount">Quantity</span>
					</div>
					<div class="market-depth-data" id="marketDepthData">
						<div class="market-depth-price">
							<div class="market-depth-sell" id="marketDepthSell"></div>
							<div class="depth-price text-left">
								Latest<span id="marketPrice" class="market-font-sell">0.0000</span>
							</div>
							<div class="depth-price right">
								Rise<span class="market-font-sell" id="marketRose">0%</span>
							</div>
							<div class="market-depth-buy" id="marketDepthBuy"></div>
						</div>
					</div>

				</div>
				<div class="market-success">
					<div class="market-success-head">
						<span class="success-time">Time</span>
						<span class="success-price">Price($)</span>
						<span class="success-amount">Total</span>
					</div>
					<div class="market-success-data" id="marketSuccessData"></div>
				</div>
			</div>
			
				<div id="marketTrade" class="market-trade">
					<div class="trade-table left">
						<div class="trade-tr">
							<span>Available：<span class="market-font-buy" id="totalCny">0</span>&nbsp${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }
							</span>
						</div>
						<div class="trade-tr">
							<label for="buy-price" class="tr-tips">Buy Price ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</label>
							<input id="buy-price" />
						</div>
						<div class="trade-tr">
							<label for="buy-amount" class="tr-tips">Buy Quantity ${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</label>
							<input id="buy-amount" />
						</div>
						<div class="trade-tr tr-boder tr-slider">
							<span id="buyBar" class="col-xs-12 buysellbar">
								<div class="buysellbar-box">
									<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker buy-marker" data-param-complete="complete"
										data-param-type="0" data-param-markertop="marker-top"></div>
									<div class="slider-points">
										<div class="proportioncircle proportion0" data-points="0">0%</div>
										<div class="proportioncircle proportion1" data-points="20">20%</div>
										<div class="proportioncircle proportion2" data-points="40">40%</div>
										<div class="proportioncircle proportion3" data-points="60">60%</div>
										<div class="proportioncircle proportion4" data-points="80">80%</div>
										<div class="proportioncircle proportion5" data-points="100">100%</div>
									</div>
								</div>
							</span>
						</div>
						<div class="trade-tr tr-boder">
							<span class="tr-tips">Quota：</span><span class="tr-right"><span class="market-font-buy" id="buy-limit">0.0000</span>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
						</div>
						<div class="trade-tr tr-btn">
							<button id="buy_sub" class="buy">Buy</button>
						</div>
					</div>
					<div class="trade-table right">
						<div class="trade-tr">
							<span>Available：<span class="market-font-sell" id="totalCoin">0</span>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }
							</span>
						</div>
						<div class="trade-tr">
							<label for="sell-price" class="tr-tips">Sell Price ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</label>
							<input id="sell-price" />
						</div>
						<div class="trade-tr">
							<label for="sell-amount" class="tr-tips">Sell Quantity ${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</label>
							<input id="sell-amount" />
						</div>
						<div class="trade-tr tr-boder tr-slider">
							<span id="sellBar" class="col-xs-12 buysellbar">
								<div class="buysellbar-box">
									<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker"
										data-param-complete="complete" data-param-type="1" data-param-markertop="marker-top"></div>
									<div class="slider-points">
										<div class="proportioncircle proportion0" data-points="0">0%</div>
										<div class="proportioncircle proportion1" data-points="20">20%</div>
										<div class="proportioncircle proportion2" data-points="40">40%</div>
										<div class="proportioncircle proportion3" data-points="60">60%</div>
										<div class="proportioncircle proportion4" data-points="80">80%</div>
										<div class="proportioncircle proportion5" data-points="100">100%</div>
									</div>
								</div>
							</span>
						</div>
						<div class="trade-tr tr-boder">
							<span class="tr-tips">Quota：</span><span class="tr-right"><span class="market-font-sell" id="sell-limit">0.0000</span>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
						</div>
						<div class="trade-tr tr-btn">
							<button id="sell_sub" class="sell">Sell</button>
						</div>
					</div>
				</div>
			
		</div>
	</div>
	
	
			<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span class="modal-title" id="exampleModalLabel">Transaction password</span>
					</div>
					<div class="modal-body form-horizontal">
						<div class="form-group">
							<div class="col-xs-3 control-label">
								<span>Transaction password：</span>
							</div>
							<div class="col-xs-6 padding-clear">
								<input type="password" class="form-control" id="tradePwd" placeholder="Transaction password">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6 padding-clear col-xs-offset-3">
								<span id="errortips" class="error-msg text-danger"></span>
							</div>
						</div>
						<div class="form-group margin-bottom-clear">
							<div class="col-xs-6 padding-clear col-xs-offset-3">
								<button id="modalbtn" type="button" class="btn btn-danger btn-block">Submit</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<input type="hidden" id="sellShortName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }">
	<input type="hidden" id="coinshortName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }">
	
	<input id="userid" type="hidden" value="${userid }">	
	<input type="hidden" id="cnyDigit" value="${ftrademapping.fcount1 }">
	<input type="hidden" id="coinDigit" value="${ftrademapping.fcount2 }">
	<input type="hidden" id="symbol" value="${ftrademapping.fid }">
	<input type="hidden" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }" id="coinshortName">
	<input id="minBuyCount" type="hidden" value="<ex:DoubleCut value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>">
    <input id="limitedType" type="hidden" value="0">
    <input id="lastprice" type="hidden" value="0">
	<input id="isopen" type="hidden" value="${needTradePasswd }">
	<input id="tradeType" type="hidden" value="${tradeType }">
	<input id="login" type="hidden" value="${login }">
	<input id="tradePassword" type="hidden" value="${tradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">
<input id="fhasRealValidate" type="hidden" value="${fuser.fhasRealValidate }">


<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_en.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js"></script>

	<script type="text/javascript" src="${oss_url}/static/front/js/market/trademarket.js?t=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>