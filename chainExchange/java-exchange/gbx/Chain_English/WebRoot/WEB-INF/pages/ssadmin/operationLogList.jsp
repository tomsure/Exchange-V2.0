<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/operationLogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /><input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/operationLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
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
			<shiro:hasPermission name="ssadmin/addOperationLog.html">
				<li><a class="add"
					href="ssadmin/goOperationLogJSP.html?url=ssadmin/addOperationLog"
					height="280" width="800" target="dialog" rel="addOperationLog"><span>Add</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteOperationLog.html">
				<li><a class="delete"
					href="ssadmin/deleteOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to delete? "><span>Delete</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/auditOperationLog.html">
				<li><a class="edit"
					href="ssadmin/auditOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to approve?"><span>Approve</span> </a>
				</li>
			</shiro:hasPermission>
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
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>Transfer Manner</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>Amount</th>
				<th width="60">Description</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
				<th width="60" orderField="flastUpdateTime"
					<c:if test='${param.orderField == "flastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>Time of Last Change</th>
				<th width="60" orderField="fkey1"
					<c:if test='${param.orderField == "fkey1" }'> class="${param.orderDirection}"  </c:if>>Approver</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${operationlogList}" var="operationlog"
				varStatus="num">
				<tr target="sid_user" rel="${operationlog.fid}">
					<td>${operationlog.fuser.fid}</td>
					<td>${operationlog.fuser.floginName}</td>
					<td>${operationlog.fuser.fnickName}</td>
					<td>${operationlog.fuser.frealName}</td>
					<td>${operationlog.ftype_s}</td>
					<td>${operationlog.fstatus_s}</td>
					<td><fmt:formatNumber value="${operationlog.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${operationlog.fdescription}</td>
					<td>${operationlog.fcreateTime}</td>
					<td>${operationlog.flastUpdateTime}</td>
					<td>${operationlog.fkey1}</td>
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
