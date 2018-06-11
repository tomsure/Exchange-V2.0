<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/countLimitList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/countLimitList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>IP Address<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
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
				<li><a class="delete"
					href="ssadmin/deleteCountLimit.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to remove the constraint?"><span>Remove Constraint</span> </a>
				</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>Constraint Type</th>
				<th width="60" orderField="fip"
					<c:if test='${param.orderField == "fip" }'> class="${param.orderDirection}"  </c:if>>IP Address</th>
				<th width="60">Times</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${countLimitList}" var="countlimit" varStatus="num">
				<tr target="sid_user" rel="${countlimit.fid}">
					<td>${num.index +1}</td>
					<td>${countlimit.ftype_s}</td>
					<td>${countlimit.fip}</td>
					<td>${countlimit.fcount}</td>
					<td>${countlimit.fcreateTime}</td>
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
