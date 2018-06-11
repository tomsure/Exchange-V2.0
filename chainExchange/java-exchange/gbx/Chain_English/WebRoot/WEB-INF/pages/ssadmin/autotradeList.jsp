<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/autotradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/autotradeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">Search</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="ssadmin/goAutotradeJSP.html?url=ssadmin/addAutotrade" height="400"
				width="800" target="dialog" rel="addAutotrade"><span>Add</span> </a></li>
			<li><a class="delete"
				href="ssadmin/deleteAutotrade.html?uid={sid_user}" target="ajaxTodo"
				title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a></li>
			<li><a class="edit"
				href="ssadmin/goAutotradeJSP.html?url=ssadmin/updateAutotrade&uid={sid_user}"
				height="400" width="800" target="dialog" rel="updateAutotrade"><span>Edit</span>
			</a></li>
			<li><a class="delete"
				href="ssadmin/doAutotrade.html?uid={sid_user}&type=1" target="ajaxTodo"
				title="Confirm: Are you sure you want to disable?"><span>Disable</span> </a></li>
			<li><a class="edit"
				href="ssadmin/doAutotrade.html?uid={sid_user}&type=2" target="ajaxTodo"
				title="Confirm: Are you sure you want to enable?"><span>Enable</span> </a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">UID</th>
				<th width="60">Type</th>
				<th width="60">Price Sync Type</th>
				<th width="60">Status</th>
				<th width="60">Cryptocurrency</th>
				<th width="60">Min Trading Qty</th>
				<th width="60">Max Trading Qty</th>
				<th width="60">Min Floating Price</th>
				<th width="60">Max Floating Price</th>
				<th width="60">Frequency (Minute)</th>
				<th width="60">Random Pause Pior to Start (secs)</th>
				<th width="60">Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${autotradeList}" var="autotrade"
				varStatus="num">
				<tr target="sid_user" rel="${autotrade.fid}">
					<td>${num.index +1}</td>
					<td>${autotrade.fuser.fid}</td>
					<td>${autotrade.ftype_s}</td>
					<td>${autotrade.fsynType_s}</td>
					<td>${autotrade.fstatus_s}</td>
					<td>${autotrade.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}(${autotrade.ftrademapping.fvirtualcointypeByFvirtualcointype1.fname})</td>
					<td><fmt:formatNumber value="${autotrade.fminqty}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${autotrade.fmaxqty}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${autotrade.fminprice}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${autotrade.fmaxprice}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${autotrade.ftimes}</td>
					<td>${autotrade.fstoptimes}</td>
					<td>${autotrade.fcreatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>Total: ${totalCount}</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
