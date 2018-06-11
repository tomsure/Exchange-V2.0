<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Error_500</title>
    <style type="text/css">
     body {
         margin: 8% auto 0;
         max-width: 550px;
         min-height: 200px;
         padding: 10px;
         font-family: Verdana,Arial,Helvetica,sans-serif;
         font-size: 14px;
     }
     p {
         color: #555;
         margin: 10px 10px;
     }

     img {
         border: 0px;
     }
     .d {
         color: #404040;
     }
    </style>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
</head>
<body>
    <a href="${requestScope.constant['fulldomain'] }"><img src="${requestScope.constant['logoImage'] }" alt="${requestScope.constant['webName'] }" /></a>
    <p>Opps,something went wrong. </p>
    <p class="d">Please check that the address is correct. Try to refresh the page or feel free to contact the admin if the problem persists.</p>
    <p><a href="${requestScope.constant['fulldomain'] }">Back to Home Page.</a></p>
</body>
</html>