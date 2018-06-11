<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../comm/include.inc.jsp" %>
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
		<title>TOP5排行榜</title>
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
				TOP5排行榜
			</div>
		</nav>
		<section>
			<div class="UpsAndWowns">
				<span class="active">24h涨跌</span>
				<span>7D涨跌</span>
				<span>24h成交额</span>
				<span>24h成交量</span>
			</div>
			<div class="rankinglist" style="display: block;">
				<ul>
					<c:forEach items="${type1 }" var="v" varStatus="vs" begin="0" end="4" >
						<li>
							<em>${vs.index+1 }</em>
							<p><img src="${v.fvirtualcointypeByFvirtualcointype2.furl }">${v.fvirtualcointypeByFvirtualcointype2.fname }</p>
							<span>${v.rose>=0?'+':'' }${v.rose }%</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="rankinglist">
				<ul>
					<c:forEach items="${type2 }" var="v" varStatus="vs" begin="0" end="4">
						<li>
							<em>${vs.index+1 }</em>
							<p><img src="${v.fvirtualcointypeByFvirtualcointype2.furl }">${v.fvirtualcointypeByFvirtualcointype2.fname }</p>
							<span>${v.rose7>=0?'+':'' }${v.rose7 }%</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="rankinglist">
				<ul>
					<c:forEach items="${type1 }" var="v" varStatus="vs" begin="0" end="4">
						<li>
							<em>${vs.index+1 }</em>
							<p><img src="${v.fvirtualcointypeByFvirtualcointype2.furl }">${v.fvirtualcointypeByFvirtualcointype2.fname }</p>
							<span>${v.totalCny24 }</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="rankinglist">
				<ul>
					<c:forEach items="${type1 }" var="v" varStatus="vs" begin="0" end="4">
						<li>
							<em>${vs.index+1 }</em>
							<p><img src="${v.fvirtualcointypeByFvirtualcointype2.furl }">${v.fvirtualcointypeByFvirtualcointype2.fname }</p>
							<span>${v.total24 }</span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</section>
		<jsp:include page="comm/menu.jsp"></jsp:include>
	</body>
</html>
<script>
	$(function(){
		$(".UpsAndWowns span").on('click',function(){
			$(this).addClass("active").siblings().removeClass("active"); 
	        var index = $(".UpsAndWowns span").index(this);
	        $(".rankinglist").eq(index).show().siblings('.rankinglist').hide();
		});
		
		$(".rankinglist ul li").on('click',function(){
	    	window.location.href="jiaoyizhongxin_xiangqing.html";
	    });
		
	});
</script>