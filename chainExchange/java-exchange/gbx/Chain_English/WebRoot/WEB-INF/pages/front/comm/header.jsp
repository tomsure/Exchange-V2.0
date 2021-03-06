<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>

<link href="${oss_url}/static/front/css/bitbs/application.css" media="all" rel="stylesheet">
<link href="${oss_url}/static/front/css/bitbs/webpack-public-style.css" media="screen" rel="stylesheet">
<style>
	input[type="text"],
	input[type="text"]:focus,
	input[type="password"],
	input[type="password"]:focus,
	input[type="datetime"],
	input[type="datetime"]:focus,
	input[type="datetime-local"],
	input[type="datetime-local"]:focus,
	input[type="date"],
	input[type="date"]:focus,
	input[type="month"],
	input[type="month"]:focus,
	input[type="time"],
	input[type="time"]:focus,
	input[type="week"],
	input[type="week"]:focus,
	input[type="number"],
	input[type="number"]:focus,
	input[type="email"],
	input[type="email"]:focus,
	input[type="url"],
	input[type="url"]:focus,
	input[type="search"],
	input[type="search"]:focus,
	input[type="tel"],
	input[type="tel"]:focus,
	input[type="color"],
	input[type="color"]:focus,
	.uneditable-input {
		height: 30px !important;
	}

	.control-group .control-label{ margin-bottom: 8px;}

	.controls .controls-right{ right: 10px !important;}
	
	
	.modal{ border: none !important; background: none !important; overflow: visible !important; overflow-x: visible !important; overflow-y: visible !important; } 
	.modal-content{ margin-top: 0px !important;}
	
	.row{ margin: 15px auto 0px auto !important;}
	
</style>

<div id="main" class="">
    <div>
        <div class="top-nav-wrapper b3" style="left: -0px">
            <section class="top-nav-container">
                <!-- react-empty: 3 -->
                <div>
                    <a class="top-logo-link" href="/">
                        <img class="pull-left korbit-menu-logo" src="${oss_url}/static/front/images/bitbs/logo-gray.png">
                    </a>
                    <div class="top-nav-links-container">
                        <div class="drop-down-menu-container">
                            <a href="/trade/coin.html?coinType=0&tradeType=0" target="_self" class="top-nav-link top-nav-link-dropdown">
                                <span>Trade</span>
                            </a>
                        </div>
                        <div class="drop-down-menu-container">
                        
			                  <c:if test="${sessionScope.login_user!=null }">
												<span class="top-nav-link top-nav-link-dropdown">
													<span>Account</span><b class="caret"></b>
												</span>
			                      <div class="drop-down-menu">
			                          <a href="/financial/index.html" class="drop-down-menu-item"><span>My Assets</span></a>
			                          <a href="/trade/entrust.html?status=0" class="drop-down-menu-item"><span>My Trade</span></a>
			                          <a href="/account/rechargeCny.html" class="drop-down-menu-item"><span>Deposit</span></a>
			                          <a href="/account/withdrawCny.html" class="drop-down-menu-item"><span>Withdrawal</span></a>
			                      </div>
			                  </c:if>
                  </div>
                  
                        <!--
                        <div class="drop-down-menu-container">
                            <a href="/crowd/index.html" target="_self" class="top-nav-link top-nav-link-dropdown"><span>ICO</span></a>
                        </div>
												--!>

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
                            	<!--英文图片名称是CN.png-->
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

<div>
    <div class="ticker-bar">
        <div class="inner-col" style="width: 1170px !important;">
            <div class="pull-left korbit-ticker" style=" width: 50%; height: 20px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap; color: #fff;text-align: right;display:none;">
                <c:forEach items="${requestScope.constant['news']}" var="v" begin="0" end="0">
                    <a href="${v.url }" target="_blank" style="color: #fff;">${v.ftitleEn }</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
