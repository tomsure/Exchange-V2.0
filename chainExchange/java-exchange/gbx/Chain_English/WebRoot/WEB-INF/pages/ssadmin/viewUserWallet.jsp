<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/viewUserWallet.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="cid" value="${cid}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);"
		action="ssadmin/viewUserWallet.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<input type="hidden" name="cid" value="${cid}" />
					<td>Member Info<input type="text" name="keywords"
						value="${keywords}" size="60" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">Search</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar"></div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">UID</th>
				<th width="60">Username</th>
				<th width="60">Real Name</th>
				<th width="60">Mobile</th>
				<th width="60">Name</th>
				<th width="60">Available/Number</th>
				<th width="60">Freezing amount/Number</th>
				<th width="60">Recharge amount/Number</th>
				<th width="60">Withdrawal amount/Number</th>
				<th width="60">Buy amount/Number</th>
				<th width="60">Sell amount/Number</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="v" varStatus="num">
				<tr target="sid_user" rel="${v.uid}">
					<td>${num.index +1}</td>
					<td>${v.uid}</td>
					<td>${v.loginName}</td>
					<td>${v.realName}</td>
					<td>${v.telephone}</td>
					<td>${v.coinName}</td>
					<td><fmt:formatNumber value="${v.total}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${v.frozen}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${v.regAmt}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${v.wAmt}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${v.buy}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${v.sell}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>Total: ${totalCount}</span>
		</div>
		<div class="pagination" targetType="dialogTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
