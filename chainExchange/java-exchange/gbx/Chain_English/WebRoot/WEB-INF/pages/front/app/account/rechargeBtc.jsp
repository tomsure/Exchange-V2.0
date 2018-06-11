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
    <title>地址充值</title>
    <link rel="stylesheet" href="/static/front/app/css/css.css" />
    <link rel="stylesheet" href="/static/front/app/css/style.css" />
    <link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
    <script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
</head>
<body>
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
        充值
        <p><a href="/m/account/rechargeBtcList.html?symbol=${symbol }">充值记录</a></p>
    </div>
</nav>
<section>
    <div class="pay-money">
        <p>充值地址</p>
        <input type="text" disabled="disabled" value="JUo32Eo15Xa8KJAGBfbNmhhwNSsWSw3Uf9" />
    </div>
    <div class="operation">
        <button class="active">点击复制充值地址</button>
        <p>充值提示：</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${fvirtualcointype.fregDesc }</p>
    </div>
</section>
</body>
</html>