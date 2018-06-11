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
    <title>区块查询</title>
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
        区块查询
    </div>
</nav>
<section>
    <div class="property_con mt20">
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201706121453040_0oKkx.jpg">微云币</span>
            <p><a href="http://119.23.36.212/">查询</a></p>
        </div>
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201706111833027_4A16g.png">檀香币</span>
            <p><a href="http://120.77.202.3/">查询</a></p>
        </div>
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201707271036028_0fAWW.png">贝贝币</span>
            <p><a href="http://119.23.46.182:8080">查询</a></p>
        </div>
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201707181953033_oPUB5.png">U点</span>
            <p><a href="http://120.77.181.127/">查询</a></p>
        </div>
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201706111035038_VQO3x.png">努比亚</span>
            <p><a href="http://www.nubiancoin.com/">查询</a></p>
        </div>
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201610151151008_pgr3D.png">鸡币</span>
            <p><a href="http://112.74.88.88:8080/ ">查询</a></p>
        </div>
        <div class="cont_info clearfix">
            <span><img src="https://yunshu333.oss-cn-shenzhen.aliyuncs.com/upload/system/201708152015018_Dg5IM.png">小彤币</span>
            <p><a href="http://39.108.174.162/">查询</a></p>
        </div>
    </div>
</section>
</body>
</html>