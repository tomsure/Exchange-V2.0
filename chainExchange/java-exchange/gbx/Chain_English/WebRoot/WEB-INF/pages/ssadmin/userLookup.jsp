<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/userLookup.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="ssadmin/userLookup.html"
		onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Keywords<input type="text" name="keywords" value="${keywords}"
						size="60" /> [Member name, nickname, real name, ID No., username, mailbox]</td>
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
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="100">Username</th>
				<th width="60">Status</th>
				<th width="60">Nickname</th>
				<th width="60">Real name</th>
				<th width="60">Mobile Number</th>
				<th width="60">Email</th>
				<th width="60">ID No.</th>
				<th width="60">Valid Search</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList1}" var="user" varStatus="num">
				<tr>
					<td>${num.index +1}</td>
					<td>${user.floginName}</td>
					<td>${user.fstatus_s}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.ftelephone}</td>
					<td>${user.femail}</td>
					<td>${user.fidentityNo}</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({id:'${user.fid}', floginName:'${user.floginName}'})"
						title="查找带回">选择</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>Total: ${totalCount}</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
