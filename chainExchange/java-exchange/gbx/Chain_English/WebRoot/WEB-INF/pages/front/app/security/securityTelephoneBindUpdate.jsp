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
    <title><c:if test="${isBindTelephone == true }">修改</c:if><c:if test="${isBindTelephone == false }">绑定</c:if>手机号</title>
    <link rel="stylesheet" href="/static/front/app/css/css.css" />
    <link rel="stylesheet" href="/static/front/app/css/style.css" />
    <link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
    <script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
    <style>
        .Personal-cont p img{
            padding:0;
        }
    </style>
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
        <c:if test="${isBindTelephone == true }">修改</c:if><c:if test="${isBindTelephone == false }">绑定</c:if>手机号
    </div>
</nav>
<section>
    <div class="information-cont">
        <c:if test="${isBindTelephone == true }">
            <ul>
                <li style="border-top:1px solid #e1e1e1;">
                    <span class="prefixion">+86</span>
                    <input class="phone-number" type="number" value="" placeholder="请输入手机号">
                </li>
                <li>
                    <input type="number" class="phone-code" value="" placeholder="手机验证码">
                    <span class="gain">获取验证码</span>
                </li>
                <li>
                    <input type="text" class="phone-code" value="" placeholder="请输入验证码">
                    <span class="gain1"><img src="/static/front/app/images/register_04.png" style="width: 100%; height: 100%;"></span>
                </li>
            </ul>
        </c:if>
        <c:if test="${isBindTelephone == false }">
            <ul>
                <li style="border-top:1px solid #e1e1e1;">
                    <span class="prefixion">+86</span>
                    <input class="phone-number" type="number" value="" placeholder="请输入手机号">
                </li>
                <li>
                    <input type="number" class="phone-code" value="" placeholder="手机验证码">
                    <span class="gain">获取验证码</span>
                </li>
                <li>
                    <input type="text" class="phone-code" value="" placeholder="请输入验证码">
                    <span class="gain1"><img src="/static/front/app/images/register_04.png" style="width: 100%; height: 100%;"></span>
                </li>
            </ul>
        </c:if>
    </div>
    <div class="next-padding">
        <div class="next next-active">提交</div>
    </div>
</section>
</body>
</html>
<script>
    $(function(){
        $(".next").on('click',function(){
            window.location.href = 'gerenzhongxin.html';
        });
    });
</script>