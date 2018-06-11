<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../comm/include.inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<title>云数网</title>
		<link rel="stylesheet" href="/static/front/app/css/css.css" />
		<link rel="stylesheet" href="/static/front/app/css/style.css" />
		<link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
		<script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
		<style>
			.swiper-pagination-bullet{ width: 6px; height: 6px; border-radius: 3px;}
			.swiper-pagination-bullet-active{ width: 1.25rem; height: 6px; border-radius: 3px;}
			.swiper-slide a{ display: inline-block; width: 100%; height: 100%;}
			.list_cont ul li{ position:relative;}
			.list_cont ul li a{ position:absolute; left:0;
				top:0; width: 100%; height: 100%; z-index: 2;}
		</style>
	</head>
	<body>
		<nav>
			<div class="top clearfix">
				<div class="fl"><a href="javascript:;"><img src="${requestScope.constant['applogoImage'] }"></a></div>
				<c:choose>
					<c:when test="${sessionScope.login_user == null}">
						<div class="fr clearfix">
							<a href="/m/user/login.html">登录</a>
							<a href="/m/user/register.html" class="active">注册</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="fr clearfix" style="">
							<a href="/m/account/rechargeCny.html" class="active">充值</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="banner">
				<div class="swiper-container">
			        <div class="swiper-wrapper">
			            <div class="swiper-slide"><a href="${requestScope.constant['appbigImageURL1'] }"><img src="${requestScope.constant['appbigImage1'] }"></a></div>
			            <div class="swiper-slide"><a href="${requestScope.constant['appbigImageURL2'] }"><img src="${requestScope.constant['appbigImage2'] }"></a></div>
			            <div class="swiper-slide"><a href="${requestScope.constant['appbigImageURL3'] }"><img src="${requestScope.constant['appbigImage3'] }"></a></div>
			            <div class="swiper-slide"><a href="${requestScope.constant['appbigImageURL4'] }"><img src="${requestScope.constant['appbigImage4'] }"></a></div>
			            <div class="swiper-slide"><a href="${requestScope.constant['appbigImageURL5'] }"><img src="${requestScope.constant['appbigImage5'] }"></a></div>
			            <div class="swiper-slide"><a href="${requestScope.constant['appbigImageURL6'] }"><img src="${requestScope.constant['appbigImage6'] }"></a></div>
			    </div>
				<!-- Add Pagination -->
				<div class="swiper-pagination"></div>
				<script>
			    var swiper1 = new Swiper('.swiper-container', {
			        pagination: '.swiper-pagination',
			        paginationClickable: true,
			        centeredSlides: true,
			        autoplay: 2500,
			        autoplayDisableOnInteraction: false,
        			loop: true
			    });
			    </script>
			</div>
			</div>
			<div class="radio clearfix">
				<div class="notice_active">
					<ul>
					<c:forEach items="${requestScope.constant['news']}" var="v">
						<li class="notice_active_ch"><a href="/m${v.url }">${v.ftitle }</a></li>
					</c:forEach>
					</ul>
				</div>
				<div class="more"><a href="/m/service/ourService.html?id=1">更多</a></div>
			</div>
			<div class="Amount clearfix">
				<p>
					<em id="totalUser">0</em>
					<span>注册会员(位) </span>
				</p>
				<p>
					<em id="totalAmount">0</em>
					<span>今日交易(CNY) </span>
				</p>
			</div>
		</nav>
		<section class="cont-padding">
			<div class="list_bar clearfix">
				<span class="active">币主板</span>
				<span>币创板<img src="/static/front/app/images/news.png"></span>
				<span>考察区</span>
				<p><img src="/static/front/app/images/top5.png">TOP5</p>
			</div>
			
			<c:forEach var="ty" items="${types }"  varStatus="tyvs">
				<c:forEach var="vv" items="${fMap }" varStatus="vn">
				<div class="list_conton" style="display: ${tyvs.index==0?'block':'none'};">
					<div class="navigation">
						<span>币种</span>
						<ul>
							<li class="active" data-forward="down" data-name="1"><em>最新价格</em><img src="/static/front/app/images/sjx_black.png" class="off"><img src="/static/front/app/images/sjx_blue.png" class="on"></li>
							<li data-forward="down" data-name="2"><em>24H成交量</em><img src="/static/front/app/images/sjx_black.png" class="off"><img src="/static/front/app/images/sjx_blue.png" class="on"></li>
							<li data-forward="down" data-name="3"><em>24H涨跌</em><img src="/static/front/app/images/sjx_black.png" class="off"><img src="/static/front/app/images/sjx_blue.png" class="on"></li>
						</ul>
					</div>
					<div class="list_cont">
						<ul  class="list_contul">
					<c:forEach items="${vv.value }" var="v" varStatus="vs">
							<c:if test="${v.ftype==ty }">
									<li class="tradeClick" data-id="${v.fid }">
										<a href="/m/trade/coin.html?coinType=${v.fid }&tradeType=0"></a>
										<span><img src="${v.fvirtualcointypeByFvirtualcointype2.furl }">${v.fvirtualcointypeByFvirtualcointype2.fname }</span>
										<ul>
											<li id="${v.fid }_price">0</li>
											<li id="${v.fid }_total">0</li>
											<li id="${v.fid }_rose" class="Minus">0</li>
										</ul>
									</li>
							</c:if>
						</c:forEach>
						</ul>
					</div>
					<c:if test="${'1'== ty}">
						<div class="risk-warning">
							<h2>币主板风险提示</h2>
							<p>不投入超过风险承受能力的资金，不投资不了解的数字资产，不听信任何以云数网名义推荐买币投资的宣传，坚决抵制传销、电信诈骗和洗钱套汇等违法行为。</p>
							<p>违法行为举报邮箱：market@yunshu333.com</p>
						</div>
					</c:if>
					<c:if test="${'2'== ty}">
						<div class="risk-warning">
							<h2>币创区风险提示</h2>
							<p>不投入超过风险承受能力的资金，不投资不了解的数字资产，不听信任何以云数网名义推荐买币投资的宣传，坚决抵制传销、电信诈骗和洗钱套汇等违法行为。</p>
							<p>违法行为举报邮箱：market@yunshu333.com</p>
						</div>
					</c:if>
					<c:if test="${'3'== ty}">
						<div class="risk-warning">
							<h2>考察区风险提示</h2>
							<p>不投入超过风险承受能力的资金，不投资不了解的数字资产，不听信任何以云数网名义推荐买币投资的宣传，坚决抵制传销、电信诈骗和洗钱套汇等违法行为。</p>
							<p>违法行为举报邮箱：market@yunshu333.com</p>
						</div>
					</c:if>

				</div>
				</c:forEach>
			</c:forEach>


			<div class="tel_service">
				<ul>
					<li class="active"><a href="tel:400-0660-300">客服热线：400-0660-300</a></li>
					<li><a href="tel:400-0791-729">财务专线：400-0791-729</a></li>
				</ul>
			</div>
			<div class="pattern clearfix">
				<ul>
					<li><a href="javascript:;">手机版</a></li>
					<li><a href="http://www.yunshu333.com">电脑板</a></li>
					<li><a href="https://www.yunshu333.cn/about/index.html?id=5">帮助中心</a></li>
				</ul>
			</div>
			<div class="records">Copyright © 2017 云数网</div>
		</section>
		<jsp:include page="comm/menu.jsp"></jsp:include>
		
		<script type="text/javascript" src="/static/front/app/js/index.js"></script>
	</body>
</html>