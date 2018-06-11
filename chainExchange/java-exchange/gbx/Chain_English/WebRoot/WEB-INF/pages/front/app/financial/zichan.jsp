<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../comm/include.inc.jsp" %>
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
    <title>帐户资产</title>
    <link rel="stylesheet" href="/static/front/app/css/css.css" />
    <link rel="stylesheet" href="/static/front/app/css/style.css" />
    <link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
    <script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
</head>
<body style="background: #fff;">
<nav>
    <div class="Personal-title">
				<span>
					<a href="javascript:;" onClick="javascript :history.back(-1)">
						<em>
							<i></i>
							<i></i>
						</em>
						<strong>返回</strong>
					</a>
				</span>
        帐户资产
    </div>
</nav>
<section id="cont-padding">
    <div class="property">
        <div class="property_nav">
            <span>币种名称</span>
            <span>可用</span>
            <span>冻结</span>
            <span>总量</span>
            <span>估值</span>
        </div>
        <div class="property_con">
            <div class="cont_info clearfix">
                <span><img src="/static/front/app/images/bi_icon7.png">人民币</span>
                <span><fmt:formatNumber value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                <span><fmt:formatNumber value="${fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                <span><fmt:formatNumber value="${fwallet.ftotal+fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                <span><fmt:formatNumber value="${fwallet.ftotal+fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                <p><img src="/static/front/app/images/blck01.png"></p>
            </div>
            <div class="con_alert">
                <div class="info_alert clearfix">
                    <span><a href="/m/account/rechargeCny.html"><img src="/static/front/app/images/chongzhi.png">充值</a></span>
                    <span><a href="tixian.html"><img src="/static/front/app/images/tibi.png">提现</a></span>
                </div>
            </div>
            <c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
                <div class="cont_info clearfix">
                    <span><img src="${v.value.fvirtualcointype.furl}">${v.value.fvirtualcointype.fname }</span>
                    <span><fmt:formatNumber value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                    <span><fmt:formatNumber value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                    <span><fmt:formatNumber value="${v.value.ftotal+v.value.ffrozen }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                    <span><fmt:formatNumber value="${(v.value.ftotal+v.value.ffrozen)*v.value.fprice }" pattern="##.##" maxIntegerDigits="20" maxFractionDigits="4"/></span>
                    <p><img src="/static/front/app/images/blck01.png"></p>
                </div>
                <div class="con_alert">
                    <div class="info_alert clearfix">
                        <span><a href="/m/account/rechargeBtc.html?symbol=${v.key }"><img src="/static/front/app/images/chongzhi.png">充币</a></span>
                        <span><a href="tibi.html"><img src="/static/front/app/images/tibi.png">提币</a></span>
                        <span><a href="jiaoyizhongxin_xiangqing.html"><img src="/static/front/app/images/qujiaoyi.png">去交易</a></span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
</body>
</html>
<script>
    $(function(){
        $(".cont_info").on('click',function(){
            $(this).next(".con_alert").toggle(500).siblings(".con_alert").hide();
        });
    });
</script>