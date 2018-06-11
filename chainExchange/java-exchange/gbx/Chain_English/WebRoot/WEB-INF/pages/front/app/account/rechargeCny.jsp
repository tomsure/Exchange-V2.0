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
    <title>充值</title>
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
        充值
        <p><a href="renminbichongzhijilu.html">充值记录</a></p>
    </div>
</nav>
<section>
    <div class="operation bg_blue">
        <p>人民币充值须知：</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${requestScope.constant['rechageBankDesc'] }</p>
        <span><img src="/static/front/app/images/delete.png"></span>
    </div>
    <div class="pay-money">
        <p>请选择您需要充值类型</p>
        <select disabled="disabled">
            <option value="人民币充值">人民币充值</option>
        </select>
        <p>充值金额</p>
        <input type="number" placeholder="100" />
        <input type="hidden" value="0.${randomMoney }" id="random">
        <div class="random">充值金额为:<em>100.${randomMoney }</em></div>
        <select>
            <c:forEach items="${bankInfo }" var="v">
                <option value="${v.fid }">${v.fbankName }</option>
            </c:forEach>
        </select>
    </div>
    <div class="operation">
        <button class="go_pay active">下一步</button>
    </div>
</section>
</body>
</html>
<script>
    $(".operation button").on('click',function(){
        window.location.href = "chongzhi_tips.html";
    });
    $(".bg_blue span").on('click',function(){
        $(".bg_blue").hide();
    });
</script>