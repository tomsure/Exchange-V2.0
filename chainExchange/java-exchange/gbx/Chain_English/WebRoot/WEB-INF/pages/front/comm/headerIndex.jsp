<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>

<div id="main" class="">
    <div>
        <div class="top-nav-wrapper b3 landing-nav new-landing-header" style="left: -0px">
            <section class="top-nav-container">
                <!-- react-empty: 3 -->
                <div>
                    <a class="top-logo-link" href="/">
                        <img class="pull-left korbit-menu-logo" width="109" height="32" alt="korbit-logo" src="${requestScope.constant['logoImage'] }">
                        <img class="pull-left korbit-menu-logo color" width="0" height="0" alt="korbit-logo" src="${oss_url}/static/front/images/bitbs/logo-gray.png">
                    </a>
                    <div class="top-nav-links-container">
                        <div class="drop-down-menu-container">
                            <a href="javascript:void(0);" target="_self" class="top-nav-link top-nav-link-dropdown">
                                <span>Trade</span><b class="caret"></b>
                            </a>
                            <div class="drop-down-menu">
                                <c:forEach var="vv" items="${fMap }" varStatus="vn">
                                    <c:forEach items="${vv.value }" var="v" varStatus="vs">
                                        <a href="/trade/coin.html?coinType=${v.fid }&tradeType=0" class="drop-down-menu-item"><span>${v.fvirtualcointypeByFvirtualcointype2.fShortName }</span></a>
                                    </c:forEach>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="drop-down-menu-container">
									<span class="top-nav-link top-nav-link-dropdown">
										<span>Account</span><b class="caret"></b>
									</span>
                            <div class="drop-down-menu">
                                <a href="/financial/index.html" class="drop-down-menu-item"><span>My Assets</span></a>
                                <a href="/trade/entrust.html?status=0" class="drop-down-menu-item"><span>My Transactions</span></a>
                                <a href="/account/rechargeCny.html" class="drop-down-menu-item"><span>Deposit</span></a>
                                <a href="/account/withdrawCny.html" class="drop-down-menu-item"><span>Withdraw</span></a>
                            </div>
                        </div>
                        <div class="drop-down-menu-container">
                            <a href="/crowd/index.html" target="_self" class="top-nav-link top-nav-link-dropdown"><span>Tokensale</span></a>
                        </div>

                        <c:if test="${sessionScope.login_user==null }">
                            <i class="separator"></i>
                            <div class="drop-down-menu-container">
                                <a href="/user/login.html" target="_self" class="top-nav-link top-nav-link-dropdown"><span>Sign In</span></a>
                            </div>
                            <div class="drop-down-menu-container">
                                <a href="/user/register.html" target="_self" class="top-nav-link top-nav-link-dropdown square-frame"><span>Register</span></a>
                            </div>
                        </c:if>
                        
						<!--此段为登录后出现的样式-->
                        <c:if test="${sessionScope.login_user!=null }">
                            <i class="separator"></i>
                            <div class="drop-down-menu-container">
                                <a href="/user/security.html" target="_self" class="top-nav-link top-nav-link-dropdown" style="margin-right: 0px !important;"><span>ID:${login_user.fid}</span></a>
                            </div>
                            <div class="drop-down-menu-container">
                                <a href="/user/logout.html" target="_self" class="top-nav-link top-nav-link-dropdown" style="margin-left: 0px !important;"><span>Logout</span></a>
                            </div>
                        </c:if>
                        <!--此段为登录后出现的样式-->
                        
                        <i class="separator"></i>
                        <div class="drop-down-menu-container">
                            <a target="_self" class="top-nav-link top-nav-link-dropdown">
                                <img style=" height: 22px; vertical-align: middle; margin-right: 5px;" src="${oss_url}/static/front/images/EN.png">
                                <span>English</span><b class="caret"></b>
                               	<!--中文图片名称是 CN.png-->
                            </a>
                            <div class="drop-down-menu">
                                <a href="https://exc.gbx.gi" class="drop-down-menu-item"><span>English</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>

</div>
