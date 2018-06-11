<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/ssadmin/coinVoteLogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ssadmin/coinVoteLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Keywords<input type="text" name="keywords" value="${keywords}"
						size="60" />[name,short name,description]</td>
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
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">ID</th>
				<th width="20">UID</th>
				<th width="30">Username</th>
				<th width="60">Cryptocurrency</th>
				<th width="60">Vote</th>
				<th width="60">Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${coinVoteLogList}" var="virtualCoinType"
				varStatus="num">
				<tr target="sid_user" rel="${virtualCoinType.fid}">
					<td>${num.index +1}</td>
					<td>${virtualCoinType.fuser.fid}</td>
					<td>${virtualCoinType.fuser.floginName}</td>
					<td>${virtualCoinType.fcoinvote.fshortName}</td>
					<td>${virtualCoinType.vote==-1?'Opposition':'Favor'}</td>
					<td>${virtualCoinType.fcreatetime}</td>
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
