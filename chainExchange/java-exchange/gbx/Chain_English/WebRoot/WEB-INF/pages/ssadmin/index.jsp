<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getHeader("X-Forwarded-Proto") + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
	Date date = new Date();
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.set(Calendar.DAY_OF_MONTH, 1);
	String startDate = sdf.format(c.getTime());
	String endDate = sdf1.format(c.getTime()) + "-"
			+ c.getMaximum(Calendar.DAY_OF_MONTH);
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${requestScope.constant['webinfo'].ftitle }</title>

<link href="${oss_url}/static/ssadmin/js/themes/default/style.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="${oss_url}/static/ssadmin/js/themes/css/core.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="${oss_url}/static/ssadmin/js/themes/css/print.css" rel="stylesheet"
	type="text/css" media="print" />
<link href="${oss_url}/static/ssadmin/js/uploadify/css/uploadify.css"
	rel="stylesheet" type="text/css" media="screen" />
<link href="${oss_url}/static/ssadmin/js/ztree/css/zTreeStyle.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="${oss_url}/static/ssadmin/js/treeTable/themes/default/treeTable.css"
	rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/ssadmin/js/ztree/css/keta.css" rel="stylesheet"
	type="text/css" />
	<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />


<!--[if IE]>
<link href="${oss_url}/static/ssadmin/js/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="${oss_url}/static/ssadmin/js/js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="${oss_url}/static/ssadmin/js/js/jquery-1.7.2.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/jquery.cookie.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/jquery.validate.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/jquery.bgiframe.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/xheditor/xheditor-1.2.1.min.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/xheditor/xheditor_lang/zh-cn.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/uploadify/scripts/jquery.uploadify.js"
	type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/chart/raphael.js"></script>
<script type="text/javascript"
	src="${oss_url}/static/ssadmin/js/chart/g.raphael.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/chart/g.bar.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/chart/g.line.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/chart/g.pie.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/chart/g.dot.js"></script>

<script src="${oss_url}/static/ssadmin/js/js/dwz.core.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.util.date.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.validate.method.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.drag.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.tree.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.accordion.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.ui.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.theme.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.switchEnv.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.alertMsg.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.contextmenu.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.tab.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.resize.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.dialogDrag.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.sortDrag.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.cssTable.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.stable.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.pagination.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.database.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.datepicker.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.effects.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.panel.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.checkbox.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.history.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.combox.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/js/dwz.print.js" type="text/javascript"></script>

<%-- zTree --%>
<script src="${oss_url}/static/ssadmin/js/ztree/js/jquery.ztree.all-3.5.min.js"
	type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/ztree/js/keta.js" type="text/javascript"></script>
<script src="${oss_url}/static/ssadmin/js/treeTable/jquery.treeTable.min.js"
	type="text/javascript"></script>

<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="${oss_url}/static/ssadmin/js/js/dwz.regional.en.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("/static/ssadmin/js/dwz.frag-en.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录", // 弹出登录对话框
			//		loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "${oss_url}/static/ssadmin/js/themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav"><br/>
				<a  href="javascript:void(0);"><font style="color: white;font-size: 20px;">&nbsp;&nbsp;&nbsp;Trading platform management</font></a>
				<ul class="nav">
					<shiro:hasPermission name="webBaseInfo">
						<li><a href="ssadmin/webBaseInfoList.html" target="dialog"
							width="600" title="WebSite Info">WebSite Info</a></li>
					</shiro:hasPermission>
					<li><a
						href="ssadmin/goAdminJSP.html?url=ssadmin/updateAdminPassword"
						target="dialog" width="700" title="Modify Password">Modify Password</a></li>
					<li><a href="ssadmin/loginOut.html">Logout</a>
					</li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="azure"><div class="selected">蓝色</div>
					</li>
					<!-- <li theme="green"><div>绿色</div>
					</li>
					<li theme="red"><div>红色</div></li>
					<li theme="purple"><div>紫色</div>
					</li>
					<li theme="silver"><div>银色</div>
					</li> -->
					<!-- <li theme="azure"><div>天蓝</div>
					</li> -->
				</ul>
			</div>

			<!-- navMenu -->

		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>Main MENU</h2>
					<div>close</div>
				</div>
             <%@ include file="comm/detail.jsp"%>
			</div>
		</div>

		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">Home</span> </span> </a>
							</li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">Home</a>
					</li>
				</ul>

				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<p>
								<span><font color="red">Welcome: <b>${login_admin.fname}</b>
										,current time:${dateTime}</font> </span>
							</p>
						</div>
						<div style="height: 100%;">
						<img src="${oss_url}/static/ssadmin/img/welcome.png"style="width: 100%;height: 750px;"></img>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="footer">
		${requestScope.constant['webinfo'].fcopyRights }
	</div>

</body>
</html>