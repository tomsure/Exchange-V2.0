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

<link rel="stylesheet" href="${oss_url}/static/front/css/user/about.css" type="text/css"></link>
</head>
<body>



<%@include file="../comm/header.jsp" %>
	 
	 <div class="container about">
	<div class="row">
        <div class="col-xs-2 padding-clear">
            <div class="panel panel-default">
              <div class="panel-heading">
                  <i class="iconfont"></i> 帮助分类
              </div>
              <div id="collapseOne" class="panel-collapse collapse in">
            <div class="panel-body" style="padding:0px;">
            <table class="table table-hover" style="margin-bottom:0px;">
            <tbody class="left_nav">
              <c:forEach items="${abouts }" var="v">
              <tr class="${v.fid == fabout.fid ?'active':''}">
                 <td>
                <a href="/about/index.html?id=${v.fid }">
                <i class="iconfont"></i>${v.ftitle }</a>
                </td></tr> 
              </c:forEach>  
            </tbody></table>
                  </div>
              </div>
            </div>
        </div>
		<div class="col-xs-10 padding-right-clear">
			<div class="col-xs-12 main-right aboutcontent">
			${fabout.fcontent}
            </div>
	<!---end]]-->
   		</div>
    </div>
</div>
	
 
<%@include file="../comm/footer.jsp" %>

</body>
</html>
