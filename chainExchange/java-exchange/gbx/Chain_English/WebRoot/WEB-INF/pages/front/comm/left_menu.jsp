 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include.inc.jsp"%>

<style>
	.container-full{ margin-top: 20px;}
</style>

<div class="col-xs-2 padding-left-clear leftmenu">
<div class="panel panel-default">
    <div class="panel-heading" data-toggle="collapse" href="#t1">
         <a class="panel-title">
         <i class="iconfont">&#xe790;</i>My Assets</a>
         
         <a class="pull-right">
         <i class="iconfont">&#xe65a;</i>
         </a>
    </div>
    <div id="t1" class="panel-collapse in">
        <div class="panel-body nav nav-pills nav-stacked">
        <li class="${leftMenu=='financial'?'active':'' }"><a href="/financial/index.html">
        			<i class="iconfont">&#xe624;</i>Account Assets</a></li>
		<li class="${leftMenu=='record'?'active':'' }"><a href="/account/record.html">
        			<i class="iconfont">&#xe6e2;</i>Financial Journal</a></li>
		<li class="${leftMenu=='recharge'?'active':'' }"><a href="/account/rechargeCny.html">
        			<i class="iconfont">&#xe686;</i>Deposit</a></li>
		<li class="${leftMenu=='withdraw'?'active':'' }"><a href="/account/withdrawCny.html">
        			<i class="iconfont">&#xe616;</i>Withdrawal</a></li>
        <li class="${leftMenu=='accountAdd'?'active':'' }"><a href="/financial/accountbank.html">
        			<i class="iconfont">&#xe6e2;</i>Wallet Mgmt</a></li>
        </div>
    </div>
    
    <div class="panel-heading" data-toggle="collapse" href="#t2">
         <a class="panel-title">
         <i class="iconfont">&#xe623;</i>My Trading</a>
         
         <a class="pull-right">
         <i class="iconfont">&#xe65a;</i>
         </a>
    </div>
    <div id="t2" class="panel-collapse in">
        <div class="panel-body nav nav-pills nav-stacked">
        <li class="${leftMenu=='entrust0'?'active':'' }"><a href="/trade/entrust.html?status=0"><i class="iconfont">&#xe64d;</i>Pending Order</a></li>
		<li class="${leftMenu=='entrust1'?'active':'' }"><a href="/trade/entrust.html?status=1"><i class="iconfont">&#xe622;</i>Trading History</a></li>
        <li class="${leftMenu=='assetsrecord'?'active':'' }"><a href="/financial/assetsrecord.html"><i class="iconfont">&#xe64d;</i>Asset Record</a></li>
 		<li class="${leftMenu=='mylogs'?'active':'' }"  style="display: none;"><a href="/crowd/logs.html"><i class="iconfont">&#xe60a;</i>My ICO</a></li>
        </div>
    </div>
 
    <!---->
    <div class="panel-heading" data-toggle="collapse" href="#t4">
         <a class="panel-title">
         <i class="iconfont">&#xe60b;</i>Security Center</a>
         
         <a class="pull-right">
         <i class="iconfont">&#xe65a;</i>
         </a>
    </div>
    <div id="t4" class="panel-collapse in">
        <div class="panel-body nav nav-pills nav-stacked">
		<li  class="${leftMenu=='security'?'active':'' }"><a href="/user/security.html"><i class="iconfont">&#xe646;</i>Security</a></li>
		<li  class="${leftMenu=='loginlog'?'active':'' }"><a href="/user/userloginlog.html?type=1"><i class="iconfont">&#xe64d;</i>Login History</a></li>
        <li  class="${leftMenu=='real'?'active':'' }"><a href="/user/realCertification.html"><i class="iconfont">&#xe62a;</i>Identity</a></li>
            <li  class="${leftMenu=='intro'?'active':'' }"><a href="/introl/mydivide.html"><i class="iconfont">&#xe60f;</i>Invitation Code</a></li>
        </div>
    </div>
    <!---->
    <%--<div class="panel-heading" data-toggle="collapse" href="#t5">--%>
         <%--<a class="panel-title">--%>
         <%--<i class="iconfont">&#xe600;</i>Account center</a>--%>
         <%--<a class="pull-right">--%>
         <%--<i class="iconfont">&#xe65a;</i>--%>
         <%--</a>--%>
    <%--</div>--%>
    <%--<div id="t5" class="panel-collapse in">--%>
        <%--<div class="panel-body nav nav-pills nav-stacked">--%>
		<%--<li  class="${leftMenu=='intro'?'active':'' }"><a href="/introl/mydivide.html"><i class="iconfont">&#xe60f;</i>Invitation Code</a></li>--%>
        <%--</div>--%>
    <%--</div>--%>
</div>

</div>