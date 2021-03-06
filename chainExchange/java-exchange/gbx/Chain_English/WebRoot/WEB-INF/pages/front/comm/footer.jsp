<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>

<link href="${oss_url}/static/front/css/bitbs/application.css" media="all" rel="stylesheet">
<link href="${oss_url}/static/front/css/bitbs/webpack-public-style.css" media="screen" rel="stylesheet">
<div>
    <footer data-reactroot="" class="korbit-footer">
        <section class="footer-contents">
            <section class="korbit-informations">
                <div class="language-selection"><span>&nbsp;</span></div>
                <div class="korbit-information" style="margin-top: -40px;">
                    <span class="korbit">&nbsp;</span>
                    <b><span>Official Website： </span><span><a href="${requestScope.constant['telephone1']}">${requestScope.constant['telephone1']}</a></span></b>
                    <br/>
					<span class="korbit">&nbsp;</span>
					<b><span>Suppoort： </span><span>ex@gbx.gi</span></b>
					<div class="contract-list" style="display: none">
                        <span>Working day: 9:00-12:00 a.m. 13:00-18:00/ p.m. holiday break</span><br>
                    </div>
                    <div class="contract-list" style="display: none">
                        <span>Official group：</span><br>
                    </div>
                    <div class="contract-list" style="display: none">
                        <span>${requestScope.constant['qqQUN1'] } &nbsp;&nbsp; ${requestScope.constant['qqQUN2'] } &nbsp;&nbsp;${requestScope.constant['qqQUN3'] }</span><br>
                    </div>
                </div>
                <div class="cert-logos" style="margin-top: 10px;"><span class="kb-mark" style="display: none"><a class="kb-logo" role="button" tabindex="0"><img alt="kb-logo" src="${oss_url}/static/front/images/bitbs/kbescrow.png" width="32" height="32"></a></span><i></i>
                    <a class="bitgo-instant-logo" href="#"><img alt="bitgo-instant-logo" src="${requestScope.constant['logoImage'] }" width="162" height="32"></a>
                </div>
            </section>
            <section class="korbit-sitemaps-wrapper">
                <div>
                    <section class="sitemap-group">
                        <div><b><span>Platform Introduction</span></b>
                            <a class="sitemap" href="javascript:void(0);" target="_blank"><span> About </span></a>
                            <a class="sitemap" href="javascript:void(0);" target="_blank"><span> Laws and Regulations </span></a>
                            <a class="sitemap" href="javascript:void(0);" target="_blank"><span> Registration Protocol </span></a>
                            <%--<a class="sitemap" href="/about/t_detail.html?id=3" target="_blank"><span> Jobs </span></a>--%>
                        </div>
                    </section>
                    <section class="sitemap-group">
                        <div><b><span> Help Center </span></b>
                            <a class="sitemap" href="javascript:void(0);"><span> Registration Guide </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Transaction Guide </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Deposit Guide </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Currency Guide </span></a>
                        </div>
                    </section>
                    <section class="sitemap-group">
                        <div><b><span> Downloads</span></b>
                            <a class="sitemap" href="javascript:void(0);"><span> API </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> APP </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Wallet Download </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Answer </span></a>
                        </div>
                    </section>
                </div>
                <!-- react-empty: 230 -->
            </section>
        </section>
    </footer>
</div>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_en.js"></script>