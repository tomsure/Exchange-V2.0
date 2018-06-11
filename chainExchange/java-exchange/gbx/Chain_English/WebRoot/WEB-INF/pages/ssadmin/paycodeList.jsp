<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/paycodeList.html">
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
		action="ssadmin/paycodeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="30" />
					</td>
					<td>代理商ID：<input type="text" name="proxyid" value="${proxyid}"
						size="30" />
					</td>
					<td>创建时间:
					<input type="text" name="fcreatetime" class="date"
						readonly="true" />
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
				<!-- <li><a class="add"
					href="ssadmin/goPaycodeJSP.html?url=ssadmin/addPaycode"
					height="200" width="600" target="dialog" rel="addPaycode"><span>生成支付码</span>
				</a>
				</li>
				<li><a title="确定要审核吗?" target="selectedTodo" rel="ids"
				postType="string" href="ssadmin/sendPaycode.html" class="edit"><span>审核</span>
			</a></li>
			<li><a title="确定要取消吗?" target="selectedTodo" rel="ids"
				postType="string" href="ssadmin/cancelPaycode.html" class="edit"><span>取消</span>
			</a></li> -->
		</ul>
	</div>
	<table class="table" width="150%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl"></th>
				<th width="20">序号</th>
				<th width="60">代理商ID</th>
				<th width="60">代理商名称</th>
				<th width="60">充值会员ID</th>
				<th width="60">充值会员登陆名</th>
				<th width="180">支付码A段</th>
				<th width="180">支付码B段</th>
				<th width="100">金额</th>
				<th width="100">状态</th>
				<th width="100">创建时间</th>
				<th width="100">用户确认时间</th>
				<th width="100">管理员审核时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paycodeList}" var="paycodeList" varStatus="num">
				<tr target="sid_user" rel="${paycodeList.fid}">
					<td><input name="ids" value="${paycodeList.fid}"
						type="checkbox"></td>
					<td>${num.index +1}</td>
					<td>${paycodeList.fproxy.fid}</td>
					<td>${paycodeList.fproxy.name}</td>
					<td>${paycodeList.fuser.fid}</td>
					<td>${paycodeList.fuser.floginName}</td>
					<td>${paycodeList.fkey}</td>
					<td>${paycodeList.fvalue}</td>
					<td>${paycodeList.famount}</td>
					<td>${paycodeList.fstatus_s}</td>
					<td>${paycodeList.fcreatetime}</td>
					<td>${paycodeList.fuserTime }</td>
					<td>${paycodeList.fadminTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
