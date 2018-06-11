<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getHeader("X-Forwarded-Proto") + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<!doctype html>
<html>
<head>
	<base href="<%=basePath%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="../comm/link.inc.jsp"%>

	<link rel="stylesheet" href="${oss_url}/static/front/css/zhongchou/zc.css"
		  type="text/css"></link>
</head>
<body>


<%@include file="../comm/header.jsp"%>


<div class="container-full" style="margin-top: 10px;">
	<div class="container displayFlex">
		<div class="row">
			<div class="autobox">
				<div class="crowd_center clear po_re zin70">
					<div class="crowd_con clear">
						<div class="crowd_con_l " style="width: 740px;height: 320px;">
							<div class="crowd_bi clear">
								<span class="crowd_bi_hlb">
								<img src="${fsubscription.fvirtualcointype.furl }" width="50px" height="50px"/>
								</span>
								<h2>
									<a href="/crowd/view.html?fid=${fsubscription.fid }">${fsubscription.ftitle }</a>
								</h2>
								<div class="crowd_hui crowd_hui_${fsubscription.fstatus=='Ongoing'?'green':'red' }">
									<em></em>${fsubscription.fstatus}
								</div>
								<div style="float: right;width: 100px;height: 30px;border-radius: 2px;margin-top: 14px;
							     margin-right: 10px;line-height: 28px;padding: 2px 8px;color:white;text-align: center;"><a href="${fsubscription.fbaipi}" target="_blank">Whitepaper</a></div>
							</div>
							<ul class="crowd_subscribe clear">
								<li style="list-style-type:none;">
									<p>Total(${fsubscription.fvirtualcointype.fShortName })</p>
									<span class="red"> <fmt:formatNumber value="${fsubscription.ftotal }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="0"/></span></li>
								<li style="list-style-type:none;">
									<p>Surplus(${fsubscription.fvirtualcointype.fShortName })</p>
									<span class="red"> <fmt:formatNumber value="${fsubscription.ftotal-fsubscription.fAlreadyByCount }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="0"/></span></li>
								<c:if test="${!fsubscription.fisICO }">
									<%--<li style="list-style-type:none;">--%>
									<%--<p>单价</p>--%>
									<%--<span class="red">${fsubscription.symbol1 } <fmt:formatNumber value="${fsubscription.fprice }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span></li>--%>
								</c:if>
								<c:if test="${fsubscription.fisICO }">
									<li style="list-style-type:none;">
										<p>Max Support</p>
										<span class="red">
								<c:choose>
									<c:when test="${v.fbuyCount ==0 }">
										Unrestricted
									</c:when>
									<c:otherwise>
										${fsubscription.symbol1}<fmt:formatNumber value="${fsubscription.fbuyCount }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="0"/>
									</c:otherwise>
								</c:choose>
								</span></li>
									<li style="list-style-type:none;">
										<p>Min Support</p>
										<span class="red">
												${fsubscription.symbol1}<fmt:formatNumber value="${fsubscription.fminbuyCount }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="0"/>
										</span></li>
								</c:if>
							</ul>
							<div class="crowd_time">
								<div>
									<c:choose>
										<c:when test="${fsubscription.fstatus=='Ongoing'}">
											　　　<p class="crowd_con_lp" id="cdstatus">Spport End：</p>
											<div class="crowd_time_all">
												<div id="t_d">
													<em>天</em>
												</div>
												<div id="t_h">
													<em>时</em>
												</div>
												<div id="t_m">
													<em>分</em>
												</div>
												<div id="t_s">
													<em>秒</em>
												</div>
											</div>
										</c:when>
										<c:when test="${fsubscription.fstatus=='Preheating'}">
											　　　<p class="crowd_con_lp" id="cdstatus">Spport Start：</p>
											<div class="crowd_time_all">
												<div id="t_d">
													<em>天</em>
												</div>
												<div id="t_h">
													<em>时</em>
												</div>
												<div id="t_m">
													<em>分</em>
												</div>
												<div id="t_s">
													<em>秒</em>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<p style="text-align: center; font-size: 22px; letter-spacing: 1px; line-height: 70px;">Ended</p>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="crowd_pro">
								<progress value="<fmt:formatNumber value="${fsubscription.fAlreadyByCount }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="0"/>" max="${fsubscription.ftotal }"></progress>
								<span><fmt:formatNumber value="${fsubscription.fAlreadyByCount/fsubscription.ftotal*100 }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="2"/>%</span>
							</div>
							<div class="crowd_xq">
								<span>
								<c:if test="${fsubscription.fisICO }">
									Supported${fsubscription.symbol }:
								</c:if>
								<c:if test="${!fsubscription.fisICO }">
									Supported Num:
								</c:if>
								<fmt:formatNumber value="${fsubscription.fAlreadyByCount }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="0"/></span>
								<%--<c:if test="${!fsubscription.fisICO }">--%>
								<%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
								<%--<span>已支持${fsubscription.symbol }：<fmt:formatNumber value="${fsubscription.fprice*fsubscription.fAlreadyByCount }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>--%>
								<%--</c:if>--%>
							</div>
						</div>

						<div class="crowd_con_r">
							<div class="crowd_my">
								<div class="login_text zin90 clear">
									<c:forEach items="${coinType}" var="coinType" varStatus="vs">
										Available
										${coinType.fShortName }：<span class="green">
										<fmt:formatNumber value="${walletList[vs.index].ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
										</span><br/>
										<%--<a href="${rechargeUrl }" target="_blank" class="orange crowd_recharge">去充值</a>--%>
									</c:forEach>
								</div>
								<div class="login_text zin90 clear">
									<span id="subCrowdNum" class="crowd_num">-</span> <input
										type="text" id='crowd_num'
										style="width: 163px; margin:0px 5px;float: left"
										onkeyup="crowdnumChange(this);"
										placeholder="Num" /> <span id="addCrowdNum"
																		  class="crowd_num">+</span>
								</div>

								<div class="login_text zin90 clear">
									<select style="width: 250px;" onchange="selectCoinType(this);" id="coinType">
										<c:forEach items="${coinType}" var="coinType" varStatus="vs">
											<option value="${coinType.fid}" price="${priceList[vs.index]}">${coinType.fShortName}</option>
										</c:forEach>
									</select>
								</div>

								<div class="login_text zin90 clear orange" id="yue">

								</div>

								<div class="login_text zin80">
									<input type="password" id="pwtrade" name='pwtrade' value=""
										   placeholder="transaction password" />
								</div>
								<div class="login_button">
									<c:choose>
										<c:when test="${fsubscription.fstatus=='Ongoing'}">
											<input type="button" value="Join ICO" onclick="joincrowd()" class="login_button_x"/>
										</c:when>
										<c:otherwise>
											<input style="background: #aaa" value="Support${fsubscription.fstatus}" onclick="return false" type="button">
										</c:otherwise>
									</c:choose>
								</div>
								<%--<div class="login_button">--%>
									<%--<a href="/crowd/logs.html" target="_blank" class="right crowd_look orange">View Record--%>
									<%--</a>--%>
								<%--</div>--%>
							</div>
						</div>
					</div>

					<div class="crowd_rule crowd_asch">
						<h1 class="crowd_rule_t">Rule</h1>
						<div class="crowd_rule_con crowd_asch_con">
							${fsubscription.fcontent }
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>
</div>


<input type="hidden" id="type" value="${fsubscription.fisICO==true?'金额':'份数' }"/>
<input type="hidden" id="start" value="${s }"/>
<input type="hidden" id="end" value="${e }"/>
<input type="hidden" id="subid" value="${fsubscription.fid }"/>
<input type="hidden" id="price" value=""/>
<input type="hidden" id="costId" value=""/>
<%@include file="../comm/footer.jsp"%>
<script type="text/javascript" src="${oss_url}/static/front/js/zc/zc.js"></script>
<script>
    function selectCoinType() {
        var name = $("#coinType option:selected").html();
        var price = $("#coinType option:selected").attr("price");
        var costId = $("#coinType").val();
        var crowd_num = $("#crowd_num").val();
        var yue = (crowd_num * price).toFixed(4);
        $("#yue").html("≈"+yue+name);
        $("#price").val(price);
        $("#costId").val(costId);
    }

    selectCoinType();
</script>
</body>
</html>