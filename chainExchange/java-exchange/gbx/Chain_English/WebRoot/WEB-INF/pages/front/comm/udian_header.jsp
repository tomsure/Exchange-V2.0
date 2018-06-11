<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery-1.9.1.min.js" ></script>
<link rel="stylesheet" href="${oss_url}/static/front/css/udian/css.css" />
<link rel="stylesheet" href="${oss_url}/static/front/css/udian/style.css" />
<div class="top-bar">
    <div class="bar-con">
        <div class="bar-info font12 fl">云数网专注于区块链技术 + 实体应用的数字资产交易</div>
        <div class="enrollment font12 fl">截止到日前，云数网注册人数<em class="font14">${requestScope.constant['totalActUser'] }人</em>，今日累计交易额：<em class="font14">￥${requestScope.constant['totalActAmt'] }</em></div>
        <div class="download-ma fr"><em><img src="${oss_url}/static/front/images/udian/phone.png"></em><span class=" font12">云数网APP下载</span><i><img src="${oss_url}/static/front/images/udian/list_img.png"></i></div>
        <div class="ma-show"><img src="${oss_url}/static/front/images/udian/timg.gif"></div>
    </div>
</div>
<div class="menu-index">
    <div class="menu-con clearfix">
        <div class="logo fl"><a href="javascript:;"><img src="${requestScope.constant['logoImage'] }" style="width:180px ;height:58px; "/></a></div>
        <div class="menu-list">
            <ul>
                <li class="active"><a href="javascript:;">首页</a></li>
                <li class="on"><a href="javascript:;">我的账户</a>

                    <div class="list_cont">
                        <p><a href="javascript:;">我的资产</a></p>
                        <p><a href="javascript:;">我的交易</a></p>
                        <p><a href="javascript:;">人民币充值</a></p>
                        <p><a href="javascript:;">人民币提现</a></p>
                    </div>

                </li>

                <li><a href="javascript:;">安全中心</a></li>
                <li><a href="javascript:;">行情中心</a></li>
                <li><a href="javascript:;">上币申请</a></li>
                <li><a href="javascript:;">区块查询</a></li>
            </ul>
        </div>
    </div>
</div>