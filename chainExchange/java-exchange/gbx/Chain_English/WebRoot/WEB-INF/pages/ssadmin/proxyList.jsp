<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/proxyList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="proxyid" value="${proxyid}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/proxyList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="30" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add"
					href="ssadmin/goProxyJSP.html?url=ssadmin/addProxy"
					height="350" width="800" target="dialog" rel="addProxy"><span>新增</span>
				</a>
				</li>
				<li><a class="delete"
					href="ssadmin/deleteProxy.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a>
				</li>
				<li><a class="edit"
					href="ssadmin/goProxyJSP.html?url=ssadmin/updateProxy&uid={sid_user}"
					height="350" width="800" target="dialog" rel="updateProxy"><span>修改</span>
				</a>
				</li>
				<li><a class="edit"
					href="ssadmin/goProxyJSP.html?url=ssadmin/updateProxyAmt&uid={sid_user}"
					height="350" width="800" target="dialog" rel="updateProxyAmt"><span>增加额度</span>
				</a>
				</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100">代理商ID</th>
				<th width="100">代理名称</th>
				<th width="100">状态</th>
				<th width="100">保证金</th>
				<th width="100">QQ</th>
				<th width="100">姓名</th>
				<th width="100">账号</th>
				<th width="100">会员ID</th>
				<th width="100">总额度</th>
				<th width="100">剩余额度</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${proxyList}" var="v" varStatus="vs">
				<tr target="sid_user" rel="${v.fid}">
					<td>${v.fid}</td>
					<td>${v.name}</td>
					<td>${v.fstatus_s}</td>
					<td>${v.amount}</td>
					<td>${v.qq}</td>
					<td>${v.realname}</td>
					<td>${v.account}</td>
					<td style="color:red;">${v.fuser.fid}</td>
					<td style="color:red;">${v.ftotalAmt}</td>
					<td style="color:red;">${v.flastAmt}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
		</div>
	</div>
</div>
