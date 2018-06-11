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
    <title>手机认证</title>
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
        手机认证
        <c:if test="${isBindTelephone == true }"><p><a href="xiugaishoujihao.html">修改</a></p></c:if>
    </div>
</nav>
<section>
    <c:if test="${isBindTelephone == true }">
        <div class="Personal-cont">
            <p><img src="/static/front/app/images/succeed.png"></p>
            <h2 class="clearfix">手机已通过认证</h2>
            <ul class="clearfix">
                <li><span>手  机：</span>	${telNumber}</li>
            </ul>
        </div>
    </c:if>
    <c:if test="${isBindTelephone == false }">
        <div class="Personal-cont">
            <p><img src="/static/front/app/images/no_pass.png"></p>
            <h2 class="clearfix">未绑定手机</h2>
        </div>
        <div class="next-padding">
            <div class="next next-active">去绑定</div>
        </div>
    </c:if>
</section>
</body>
<script type="text/javascript">
    $(".next-padding").on("click", function() {
        window.location.href = "/m/securityTelephoneBindUpdate.html";
    });
</script>
</html>