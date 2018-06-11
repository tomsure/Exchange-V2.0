<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/vote/vote.css" type="text/css"></link>
</head>
<body>



 


<%@include file="../comm/header.jsp" %>



	<div class="container-full">
		<div class="container displayFlex">
		<div class="row">
			<div class="col-xs-12 rightarea assets-list-rightarea">
				<div class="col-xs-12 rightarea-con padding-top-clear">
					<article class="assets-list-item hd">
						<span></span> <span>项目LOGO</span> <span>项目名称</span> <span>支持数 </span> <span>反对数</span> <span>详情</span>
					</article>
					
					<c:forEach items="${coinvotes }" var="v" varStatus="vn">
						<a href="/vote/details.html?id=${v.fid }" class="assets-list-item">
						<c:choose>
						   <c:when test="${vn.index+1 <= 3 && flag}">
						   <span> <i class="level level${vn.index+1 }"></i></span> 
						   </c:when>
							<c:otherwise>
						   <span> <i class="level level4"></i></span> 
						  </c:otherwise>
						</c:choose>   
							<span><img alt="${v.fcnName }" class="logo" src="${v.furl }"></img></span> 
							<span> ${v.fcnName }(${v.fshortName })</span>
							<span> ${v.fyes }</span>
							<span> ${v.fno }</span>
							<span><i class="list-caret"></i></span>
						</a>
					</c:forEach>
					
						<div class="assets-list-item text-right padding-top-30">
							${ pagin}
						</div>
					
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="../comm/footer.jsp" %>	

</body>
</html>