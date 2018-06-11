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
    <title>云数网</title>
    <link rel="stylesheet" href="/static/front/app/css/css.css" />
    <link rel="stylesheet" href="/static/front/app/css/style.css" />
    <link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
    <script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
    <style>
        .list_cont ul li{ position:relative;}
        .list_cont ul li a{ position:absolute; left:0;
            top:0; width: 100%; height: 100%; z-index: 2;}
    </style>
</head>
<body>
<nav>
    <div class="Personal-title">
        交易中心
    </div>
</nav>
<section class="cont-padding">
    <div class="list_bar clearfix">
        <span class="active">币主板</span>
        <span>币创板<img src="/static/front/app/images/news.png"></span>
        <span>考察区</span>
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

            </div>
        </c:forEach>
    </c:forEach>


</section>
<jsp:include page="../comm/menu.jsp"></jsp:include>

<script type="text/javascript" src="/static/front/app/js/index.js"></script>
</body>
</html>