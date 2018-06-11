<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/capitalOutSucList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="capitalId" value="${capitalId}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		  action="ssadmin/capitalOutSucList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords" value="${keywords}"
									size="40" /></td>
					<td>Deposit ID<input type="text" name="capitalId"
									value="${capitalId}" size="10" />
					</td>
					<td>Start Date<input type="text" name="logDate" class="date"
									 readonly="true" value="${logDate }" />
					</td>
					<td>End Date<input type="text" name="logDate2" class="date"
									 readonly="true" value="${logDate2 }" />
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" href="ssadmin/capitaloperationOutSucExport.html"
				   target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to export the records?"><span>Export</span>
			</a></li>
		</ul>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
		<tr>
			<th width="22"><input type="checkbox" group="ids"
								  class="checkboxCtrl">
			</th>
			<th width="20">Order ID </th>
			<th width="20">UID</th>
			<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>Username</th>
			<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>Nickname</th>
			<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>Real Name</th>
			<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>Type</th>
			<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
			<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>Amount</th>
			<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>CNY Amount</th>
			<th width="60" orderField="ffees"
					<c:if test='${param.orderField == "ffees" }'> class="${param.orderDirection}"  </c:if>>Trading Fee</th>
			<th width="60" orderField="fBank"
					<c:if test='${param.orderField == "fBank" }'> class="${param.orderDirection}"  </c:if>>Bank</th>
			<th width="60" orderField="fAccount"
					<c:if test='${param.orderField == "fAccount" }'> class="${param.orderDirection}"  </c:if>>Beneficiary Account</th>
			<th width="60" orderField="fPhone"
					<c:if test='${param.orderField == "fPhone" }'> class="${param.orderDirection}"  </c:if>>Mobile Number</th>
			<th width="60">Bank Address</th>
			<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
			<th width="60" orderField="fLastUpdateTime"
					<c:if test='${param.orderField == "fLastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>Time of Last Change</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${capitaloperationList}" var="capitaloperation"
				   varStatus="num">
			<tr target="sid_user" rel="${capitaloperation.fid}">
				<td><input name="ids" value="${capitaloperation.fid}"
						   type="checkbox">
				</td>
				<td>${capitaloperation.fid}</td>
				<td>${capitaloperation.fuser.fid}</td>
				<td>${capitaloperation.fuser.floginName}</td>
				<td>${capitaloperation.fuser.fnickName}</td>
				<td>${capitaloperation.fuser.frealName}</td>
				<td>${capitaloperation.ftype_s}</td>
				<td>${capitaloperation.fstatus_s}</td>
				<td>${capitaloperation.famount}</td>
				<td>${capitaloperation.famount*requestScope.constant['rate']}</td>
				<td>${capitaloperation.ffees}</td>
				<td>${capitaloperation.fBank}</td>
				<td>${capitaloperation.faccount_s}</td>
				<td>${capitaloperation.fPhone}</td>
				<td>${capitaloperation.faddress}</td>
				<td>${capitaloperation.fcreateTime}</td>
				<td>${capitaloperation.fLastUpdateTime}</td>
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
