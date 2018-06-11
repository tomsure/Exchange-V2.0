<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../comm/include.inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div class="munu clearfix">
			<ul>
				<li <c:if test='${"main"==menuFlag}'>class="active"</c:if> ><a href="/m/index.html?menuFlag=main">首页</a></li>
				<li <c:if test='${"trade"==menuFlag}'>class="active"</c:if> ><a href="/m/tradeIndex.html?menuFlag=trade">交易中心</a></li>
				<li <c:if test='${"article"==menuFlag}'>class="active"</c:if> ><a href="/m/service/ourService.html?id=1&menuFlag=article">最新公告</a></li>
				<li <c:if test='${"account"==menuFlag}'>class="active"</c:if> ><a href="/m/financial/index.html?menuFlag=account">个人中心</a></li>
			</ul>
		</div>
		
		
		