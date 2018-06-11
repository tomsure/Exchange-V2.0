<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getHeader("X-Forwarded-Proto") + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html lang="ko" class=" js ">

<head>
	<base href="<%=basePath%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="comm/link.inc.jsp"%>
	<title>Korbit - Bitcoin, Ethereum, Remittance</title>

	<link href="${oss_url}/static/front/images/bitbs/favicon.ico" rel="shortcut icon" type="image/vnd.microsoft.icon">
	<link href="${oss_url}/static/front/css/bitbs/application.css" media="all" rel="stylesheet">
	<link href="${oss_url}/static/front/css/bitbs/webpack-public-style.css" media="screen" rel="stylesheet">
</head>

<body class="static_view eng">

<%@include file="comm/headerIndex.jsp"%>

<div id="main-content">
	<div class="landing-container">
		<div class="main-content-area no-submenus">

			<div>
				<div class="landing-page">
					<section class="landing-page">
						<section class="landing-page-main">
							<img class="landing-background" src="${oss_url}/static/front/images/bitbs/landing-banner3.jpg" alt="korbit-background">
							<div class="landing-page-main-contents">
								<h1 class="text-center">
									<span>Bitcoin Exchange</span></h1>
							</div>
						</section>
						<div class="table-wrapper">
							<div class="table-content">
								<div>
									<div>
										<div class="row">
											<div class="col-xs-12 padding-clear" style="margin-top:15px;">
												<div class="row">
													<input type="hidden" id="rate" value="${requestScope.constant['rate']}"/>

													<c:forEach var="vv" items="${fMap }" varStatus="vn">
														<c:forEach items="${vv.value }" var="v" varStatus="vs">
															<div class="col-xs-5 padding-clear" style="margin-left: 70px;cursor: pointer;" onclick="window.location.href='/trade/coin.html?coinType=${v.fid }&tradeType=0'">
																<div class="panel panel-default">
																	<div class="panel-heading" style="background-color: #FCFCFC">
																		<span class="text-left">
																			<img src="${v.fvirtualcointypeByFvirtualcointype2.furl }"
																				 style="width:30px; height:30px;">&nbsp;&nbsp;
																			<font size="4" style="font-weight: bold;margin-left: 10px;">${v.fvirtualcointypeByFvirtualcointype2.fShortName }/<font size="2">USDT</font></font>
																		</span>
																		<span class="text-left" style="float: right">
																			<font size="5" id="${v.fid }_price" style="font-weight: bold;">-</font>
																			<font size="5" id="${v.fid }_priceCNY" style="font-weight: bold;font-size: 15px;">-</font>
																			<font size="5" id="${v.fid }_rose" style="font-weight: bold;margin-left: 25px;">-</font>
																		</span>
																	</div>
																	<table class="table table-hover" id="main-table" style="background-color: #FCFCFC">
																		<tbody class="newlist">
																		<tr>
																			<td>
																				<span class="coin_news_title left"><font size="3" id="${v.fid }_total">-</font></span>
																			</td>
																			<td>
																				<span class="coin_news_title left"><font size="3" >$</font><font size="3" id="${v.fid }_amt">-</font></span>
																			</td>
																			<td>
																				<span class="coin_news_title left"><font size="3" id="${v.fid }_rose7">-</font></span>
																			</td>
																		</tr>
																		</tbody>
																	</table>
																</div>
															</div>
														</c:forEach>
													</c:forEach>

												</div>
											</div>
										</div>
									</div>
									<!-- react-empty: 141 --></div>
							</div>
							<div class="table-summary text-right">
										<span>
											<font style="vertical-align: inherit;">
												<font style="vertical-align: inherit;">24 Hour Volume（$）</font></font>
										</span>
								<b>${requestScope.constant['totalActAmt'] }</b>
							</div>
						</div>
					</section>
					<section class="korbit-introduce">
						<div class="korbit-introduce-wrapper">
							<figure class="korbit-introduce-content text-center">
								<img alt="Diverse Assets" src="${oss_url}/static/front/images/bitbs/exchange.png" width="65" height="56">
								<h4><span>Diverse Assets</span></h4>
								<figcaption style="text-align: center;"><span>100% margin, wallet hot and cold isolation, to ensure the safety of user funds.</span></figcaption>
							</figure>
							<figure class="korbit-introduce-content text-center">
								<img alt="Secure Storage" src="${oss_url}/static/front/images/bitbs/safety.png" width="62" height="56">
								<h4><span>Secure Storage</span></h4>
								<figcaption style="text-align: center;"><span>Professional service team, online customer service and technical support.</span></figcaption>
							</figure>
							<figure class="korbit-introduce-content text-center"><img alt="Most Reliable" src="${oss_url}/static/front/images/bitbs/technology.png" width="56" height="56">
								<h4><span>Most Reliable</span></h4>
								<figcaption style="text-align: center;"><span>Recharge instant, quick cash, second million single high-performance transaction engine.</span></figcaption>
							</figure>
							<figure class="korbit-introduce-content text-center"><img alt="Best Customer Service" src="${oss_url}/static/front/images/bitbs/cscenter.png" width="53" height="56">
								<h4><span>Best Customer Service</span></h4>
								<figcaption style="text-align: center;"><span>SSL, dynamic ID card and other banking security technologies to ensure transaction security.</span></figcaption>
							</figure>
						</div>
					</section>
					<section class="korbit-news">
						<table class="horizontal-headline">
							<tbody>
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<hr>
								</td>
								<td>
									<h6 class="text-center">Announcements</h6></td>
								<td>
									<hr>
								</td>
							</tr>
							</tbody>
						</table>
						<ul class="news-list list-layout">
							<c:forEach items="${articles[0].value }" var="v" varStatus="n">
								<li>
									<a href="/service/article.html?id=${v.fid }">
										<span>${v.ftitleEn}</span>
									</a>
									<span class="pull-right">
										(<fmt:formatDate value="${v.fcreateDate }" pattern="MM-dd" />)
									</span>
								</li>
								<li class="empty"></li>
							</c:forEach>
						</ul>
					</section>
					<div>
						<section class="korbit-products">
							<table class="horizontal-headline">
								<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
										<hr>
									</td>
									<td>
										<h6 class="text-center">Our Products</h6></td>
									<td>
										<hr>
									</td>
								</tr>
								</tbody>
							</table>
							<div class="slider-wrapper">
								<div class="slick-initialized slick-slider">
									<div class="slick-list">
										<div class="slick-track" style="opacity: 1; transform: translate3d(0px, 0px, 0px); width: 3030px;">
											<section data-index="0" class="slick-slide slick-active" tabindex="-1" style="outline: none; width: 1010px;">
												<figure><img alt="exchange" src="${oss_url}/static/front/images/bitbs/prod-exchange.jpg" width="390" height="293">
													<!-- react-empty: 220 -->
													<figcaption>
														<h4><span>Trustworthy communication</span></h4>
														<p><span>The world's first digital currency -USDT- countries currency trading center.</span></p>
														<a class="btn btn-signup" href="/user/register.html"><span>Get Started</span></a>
													</figcaption>
												</figure>
											</section>
										</div>
									</div>
								</div>
							</div>
						</section>
						<!-- react-empty: 228 -->
					</div>
					<section class="korbit-news">
						<table class="horizontal-headline">
							<tbody>
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<hr>
								</td>
								<td>
									<h6 class="text-center">Cooperative Partner</h6></td>
								<td>
									<hr>
								</td>
							</tr>
							</tbody>
						</table>
						<ul class="news-list list-layout">
							<c:forEach items="${requestScope.constant['ffriendlinks'] }" var="v">
								<li>
									<a href="${v.furl }" target="_blank">
										<img alt="techcrunch" width="158px"  height="42px" src="${v.fimages }" />
									</a>
								</li>
								<li class="empty"></li>
							</c:forEach>
						</ul>
					</section>
				</div>
			</div>

		</div>
		<!-- END div main-content-area -->
	</div>
</div>

<%@include file="comm/footerIndex.jsp" %>
<input type="hidden" id="alert" value="${alert }" />
<input type="hidden" id="errormsg" value="" />
<script type="text/javascript" src="${oss_url}/static/front/js/index/index.js"></script>
<script src="${oss_url}/static/front/js/index/intlInput.js"></script>
<script src="${oss_url}/static/front/js/index/jquery.sortElements.js"></script>
</body>

</html>