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
    <title>个人中心</title>
    <link rel="stylesheet" href="/static/front/app/css/css.css" />
    <link rel="stylesheet" href="/static/front/app/css/style.css" />
    <link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
    <script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
</head>
<body>
<nav>
    <div class="Personal-title">
        个人中心
        <div class="user-set"><a href="/m/security.html"><img src="/static/front/app/images/set_info.png"></a></div>
    </div>
    <div class="Personal-top">
        <div class="Personal-info clearfix">
            <div class="pic"><a href="javascript:;"><img src="/static/front/app/images/img_pic.png"></a></div>
            <div class="user-info">
                <h2 class="clearfix"><strong>${login_user.fnickName}</strong><em><img src="/static/front/app/images/certification.png"></em></h2>
                <p>UID：${login_user.fid}  ${login_user.ftelephone}</p>
            </div>
        </div>
        <div class="user-money clearfix">
            <ul>
                <li>
                    <h2><fmt:formatNumber
                            value="${totalCapitalTrade }" pattern="##.##"
                            maxIntegerDigits="20" maxFractionDigits="4" /></h2>
                    <p>预估总资产 (元)</p>
                </li>
                <li>
                    <h2><fmt:formatNumber
                            value="${fwallet.ftotal }" pattern="##.##"
                            maxIntegerDigits="20" maxFractionDigits="4" /></h2>
                    <p>可用人民币(元)</p>
                </li>
            </ul>
        </div>
    </div>
    <div class="account">
        <ul>
            <li>
                <a href="/m/financial/zichan.html">
                    <img src="/static/front/app/images/img_zijin.png">
                    <p>账户资产</p>
                </a>
            </li>
            <li>
                <a href="caiwurizhi.html">
                    <img src="/static/front/app/images/img_caiwu.png">
                    <p>财务日志</p>
                </a>
            </li>
            <li>
                <a href="zijinzhanghu.html">
                    <img src="/static/front/app/images/img_yinhangka.png">
                    <p>绑定银行卡</p>
                </a>
            </li>
        </ul>
    </div>
</nav>
<section id="cont-padding">
    <div class="user-list clearfix img_left">
        <ul>
            <li>
                <a href="/m/block/blockQuery.html"></a>
                <img src="/static/front/app/images/set_demand.png">
                <p>区块查询</p>
            </li>
            <li>
                <a href="/m/trade/entrust.html"></a>
                <img src="/static/front/app/images/set_entrust.png">
                <p>委托管理</p>
            </li>
            <li>
                <a href="wodechengjiao.html"></a>
                <img src="/static/front/app/images/set_deal.png">
                <p>我的成交</p>
            </li>
            <li>
                <a href="zichanjilu.html"></a>
                <img src="/static/front/app/images/set_timer.png">
                <p>资产记录</p>
            </li>
            <li>
                <a href="tel:4000663300"></a>
                <img src="/static/front/app/images/set_phone.png">
                <p>电话客服</p>
                <span>400-0663-300</span>
            </li>
        </ul>
    </div>
</section>
<jsp:include page="../comm/menu.jsp"></jsp:include>
</body>
</html>
<script>
    $(function(){
        $(".munu ul li").on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
        });
    });
</script>