<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>

<link href="${oss_url}/static/front/css/bitbs/application.css" media="all" rel="stylesheet">
<link href="${oss_url}/static/front/css/bitbs/webpack-public-style.css" media="screen" rel="stylesheet">
<div>
    <footer data-reactroot="" class="korbit-footer">
            <section class="signup-in-footer"><img class="footer-background" alt="footer-background" src="${oss_url}/static/front/images/bitbs/bottom-cta-bg.png" width="1440" height="284">
                <div class="footer-main-contents"><i class="underline"></i>
                    <h2><span>Join Us today</span></h2>
                    <p><span>Start trading virtual currencies at the most secure exchange</span></p>
                    <a class="btn btn-signup" href="/user/register.html" tabindex="0"><span>Sign Up</span></a>
                </div>
            </section>
        <hr class="footer-horizontal-line">
        <section class="footer-contents">
            <section class="korbit-informations">
                <div class="language-selection"><span>&nbsp;</span></div>
                <div class="korbit-information">
                    <span class="korbit">&nbsp;</span>
                    <b><span>Customer service：</span><span>${requestScope.constant['telephone1'] }</span></b>
                    <div class="contract-list">
                        <span>Working day: 9:00-12:00 a.m. 13:00-18:00/ p.m. holiday break</span><br>
                    </div>
                    <div class="contract-list">
                        <span>Official group：</span><br>
                    </div>
                    <div class="contract-list">
                        <span>${requestScope.constant['qqQUN1'] } &nbsp;&nbsp; ${requestScope.constant['qqQUN2'] } &nbsp;&nbsp;${requestScope.constant['qqQUN3'] }</span><br>
                    </div>
                </div>
                <div class="cert-logos"><span class="kb-mark"><a class="kb-logo" role="button" tabindex="0"><img alt="kb-logo" src="${oss_url}/static/front/images/bitbs/kbescrow.png" width="32" height="32"></a></span><i></i>
                    <a class="bitgo-instant-logo" href="#"><img alt="bitgo-instant-logo" src="${requestScope.constant['logoImage'] }" width="162" height="32"></a>
                </div>
            </section>
            <section class="korbit-sitemaps-wrapper">
                <div>
                    <section class="sitemap-group">
                        <div><b><span>Platform Introduction</span></b>
                            <a class="sitemap" href="javascript:void(0);" target="_blank"><span> About </span></a>
                            <a class="sitemap" href="javascript:void(0);" target="_blank"><span> Legal Explanation </span></a>
                            <a class="sitemap" href="javascript:void(0);" target="_blank"><span> Registration Protocol </span></a>
                            <%--<a class="sitemap" href="/about/t_detail.html?id=3" target="_blank"><span> Jobs </span></a>--%>
                        </div>
                    </section>
                    <section class="sitemap-group">
                        <div><b><span> FAQ </span></b>
                            <a class="sitemap" href="javascript:void(0);"><span> Registration Guide </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Transaction Guide </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Recharge Guide </span></a>
                            <a class="sitemap" href="javascript:void(0);"><span> Currency Guide </span></a>
                        </div>
                    </section>
                    <section class="sitemap-group">
                        <div><b><span> Download Help </span></b>
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