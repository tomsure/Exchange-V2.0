<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/withdrawFeesList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/withdrawFeesList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Level<input type="text" name="keywords"
						value="${keywords}" size="60" class="digits" /></td>
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
			<shiro:hasPermission name="ssadmin/updateWithdrawFees.html">
				<li><a class="edit"
					href="ssadmin/goWithdrawFeesJSP.html?url=ssadmin/updateWithdrawFees&uid={sid_user}"
					height="200" width="800" target="dialog" rel="updateWithdrawFees"><span>Edit</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="75">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Member Level</th>
				<th width="60">USD Withdrawal Fee</th>
				<th width="60">Amount Limit</th>
				<th width="60">Trading Fee</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${withdrawFeesList}" var="withdrawFees"
				varStatus="num">
				<tr target="sid_user" rel="${withdrawFees.fid}">
					<td>${num.index +1}</td>
					<td>${withdrawFees.flevel}</td>
					<td>${withdrawFees.ffee}</td>
					<td>${withdrawFees.famt}</td>
					<td>${withdrawFees.flastFee}</td>
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
