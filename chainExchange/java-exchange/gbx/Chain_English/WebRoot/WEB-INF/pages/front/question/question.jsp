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

<link rel="stylesheet" href="${oss_url}/static/front/css/question/question.css" type="text/css"></link>
</head>
<body>
	



<%@include file="../comm/header.jsp" %>

 

	<div class="container">
		<div class="row">
			<!-- 左侧 -->
			<div class="col-xs-2 padding-left-clear">
				<div class="text-center question-left">
					<div class="question-online">
						<p id="question-pic">
							<span>
								意见反馈
							</span>
						</p>
					</div>
					<div class="question-menu"></div>
					<div class="question-img"></div>
					<div class="question-menu"></div>
				</div>
			</div>
			<!-- 右侧 -->
			<div class="col-xs-10 question-right rightarea">
				<ul class="nav nav-tabs rightarea-tabs question-nav">
					<li class="active">
						<a href="/question/question.html">
							问答
						</a>
					</li>
					<li class="">
						<a href="/question/questionlist.html">
							列表
						</a>
					</li>
				</ul>
				<div class="col-xs-12 padding-clear padding-top-30 form-horizontal">
					<div class="form-group ">
						<label for="question-type" class="col-xs-2 control-label">
							问题类型
						</label>
						<div class="col-xs-4">
							<select id="question-type" class="form-control">
								<c:forEach items="${question_list }" var="v">
									<option value="${v.key }">${v.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group ">
						<label for="question-desc" class="col-xs-2 control-label">
							问题描述：
						</label>
						<div class="col-xs-9">
							<textarea id="question-desc" class="form-control" rows="10">请输入问题描述（不得少于10字）!</textarea>
						</div>
					</div>
					<div class="form-group ">
						<label for="" class="col-xs-2 control-label"> </label>
						<div class="col-xs-10">
							<span id="errortips" class="text-danger"></span>
						</div>
					</div>
					<div class="form-group ">
						<label for="diyMoney" class="col-xs-2 control-label"></label>
						<div class="col-xs-4">
							<button id="submitQuestion" class="btn btn-danger btn-block">
								提交问题
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	



<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/question/question.js"></script>
</body>
</html>