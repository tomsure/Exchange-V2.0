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
    <title>安全设置</title>
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
        安全设置
    </div>
</nav>
<section>
    <div class="user-list clearfix bg-img01">
        <ul>
            <li <c:if test="${isPostRealValidate == true }">class="active"</c:if>>
                <a href="/m/user/realCertification.html"></a>
                <p>实名认证</p>
                <span><c:if test="${isPostRealValidate == false }">未绑定</c:if><c:if test="${isPostRealValidate == true }">已绑定</c:if></span>
            </li>
            <li <c:if test="${isBindEmail == true }">class="active"</c:if>>
                <a href="/m/securityEmail.html"></a>
                <p>邮箱绑定</p>
                <span><c:if test="${isBindEmail == false }">未绑定</c:if><c:if test="${isBindEmail == true }">已绑定</c:if></span>
            </li>
            <li <c:if test="${isBindTelephone == true }">class="active"</c:if>>
                <a href="/m/securityTelephone.html"></a>
                <p>手机绑定</p>
                <span><c:if test="${isBindTelephone == false }">未绑定</c:if><c:if test="${isBindTelephone == true }">已绑定</c:if></span>
            </li>
        </ul>
    </div>
    <div class="user-list clearfix bg-img02">
        <ul>
            <li>
                <a href="xiugaidenglumima.html"></a>
                <p>登陆密码</p>
                <span>修改</span>
            </li>
            <li>
                <a href="xiugaizhifumima.html"></a>
                <p>支付密码</p>
                <span>修改</span>
            </li>
        </ul>
    </div>
    <div class="user-list clearfix bg-img02">
        <ul>
            <li style="background: none; text-align: center; padding: 0;">
                <a href="/m/user/logout.html"></a>
                退出登录
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