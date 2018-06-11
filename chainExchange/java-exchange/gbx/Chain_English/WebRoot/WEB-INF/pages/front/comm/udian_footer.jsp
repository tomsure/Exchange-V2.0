<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>


<footer>
    <div class="footer">
        <div class="footer-cont clearfix">
            <div class="company">
                <ul>
                    <li>
                        <h2>平台介绍</h2>
                        <p><a href="/about/t_detail.html?id=1">平台简介</a></p>
                        <p><a href="/about/t_detail.html?id=2">法律说明</a></p>
                        <p><a href="/about/t_detail.html?id=4">注册协议</a></p>
                        <p><a href="/about/t_detail.html?id=3">招贤纳士</a></p>
                    </li>
                    <li>
                        <h2>帮助中心</h2>
                        <p><a href="/about/index.html?id=5">注册指南</a></p>
                        <p><a href="/about/index.html?id=6">交易指南</a></p>
                        <p><a href="/about/index.html?id=7">充值指南</a></p>
                        <p><a href="/about/index.html?id=8">转币指南</a></p>
                    </li>
                    <li>
                        <h2>下载帮助</h2>
                        <p><a href="/about/index.html?id=9">API文档</a></p>
                        <p><a href="javascript:;">APP下载</a></p>
                        <p><a href="/about/index.html?id=61">钱包下载</a></p>
                        <p><a href="/question/question.html">在线问答</a></p>
                    </li>
                </ul>
            </div>
            <div class="service">
                <h2 class="img400"><img src="${oss_url}/static/front/images/conF/400.png"><a href="tel:${requestScope.constant['telephone1'] }" target="_blank">${requestScope.constant['telephone1'] }</a></h2>
                <p>财务专线  <a href="tel:${requestScope.constant['telephone'] }" target="_blank">${requestScope.constant['telephone'] }</a></p>
                <p>工作日：上午9：00-12：00 下午13：00-18:00/节假日休息</p>
                <h2>官方群</h2>
                <p>${requestScope.constant['qqQUN1'] }&nbsp;&nbsp;&nbsp;${requestScope.constant['qqQUN2'] }&nbsp;&nbsp;&nbsp;${requestScope.constant['qqQUN3'] }</p>
            </div>
            <div class="download-app">
                <h2>手机APP下载</h2>
                <p><a href="javascript:;"><img src="${oss_url}/static/front/images/conF/phone-download.png"></a></p>
                <p><a href="javascript:;"><img src="${oss_url}/static/front/images/conF/Android-download.png"></a></p>
            </div>
        </div>
    </div>
    <div class="records">
        <p>${requestScope.constant['webinfo'].fcopyRights } | ${requestScope.constant['webinfo'].fbeianInfo } </p>
        <p>
            ${requestScope.constant['webinfo'].fsystemMail }
        </p>
    </div>
</footer>
<%-- <div class="footer">
    <div class="footer-box">
        <div class="footer-left">
            <img src="${requestScope.constant['footerlogo'] }" style="width: 220px;">
        </div>
        <div class="footer-right">
            <p>${requestScope.constant['webinfo'].fcopyRights } | ${requestScope.constant['webinfo'].fbeianInfo }</p>
            <p  class="footerSafety">
                    ${requestScope.constant['webinfo'].fsystemMail }
            <p>
        </div>
    </div>
</div> --%>


<%--<c:if test="${isphone }">--%>
<%--<!-- WPA start -->--%>
<%--<script src="//wp.qiye.qq.com/qidian/2852150953/77afa3917ffc7c53c6148357b56dbcb0" charset="utf-8"></script>--%>
<%--<!-- WPA end -->--%>
<%--</c:if>--%>
<%--<c:if test="${!isphone }">--%>
<%--<!-- WPA start -->--%>
<%--<script src="//wp.qiye.qq.com/qidian/2852150953/0d67b59ac902d0b8e86c056564fd7ebb" charset="utf-8"></script>--%>
<%--<!-- WPA end -->--%>
<%--</c:if>--%>

<script type="text/javascript" src="${oss_url}/static/front/js/index/news-tabs.js" ></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>