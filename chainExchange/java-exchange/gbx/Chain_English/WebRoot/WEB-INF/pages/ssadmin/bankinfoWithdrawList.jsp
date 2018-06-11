<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/bankinfoWithdrawList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" /><input type="hidden"
		name="address" value="${address}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/bankinfoWithdrawList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
					<td>Bank accoun<input type="text" name="address" value="${address}"
						size="40" />
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

		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>Username</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>Nickname</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>Real Name</th>
				<th width="60">Bank</th>
				<th width="60">Account Number</th>
				<th width="60">Address</th>
				<th width="60">Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bankinfoWithdrawList}" var="v" varStatus="num">
				<tr target="sid_user" rel="${v.fid}">
					<td>${v.fuser.fid}</td>
					<td>${v.fuser.floginName}</td>
					<td>${v.fuser.fnickName}</td>
					<td>${v.fuser.frealName}</td>
					<td>${v.fname}</td>
					<td>${v.fbankNumber}</td>
					<td>${v.faddress}-${v.fothers}</td>
					<td>${v.fcreateTime}</td>
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
