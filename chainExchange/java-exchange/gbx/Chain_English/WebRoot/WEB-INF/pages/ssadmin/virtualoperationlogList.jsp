<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/virtualoperationlogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/virtualoperationlogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Keyword(s)<input type="text" name="keywords" value="${keywords}"
						size="60" />[Member Info]</td>
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
			<shiro:hasPermission name="ssadmin/addVirtualOperationLog">
				<li><a class="add"
					href="ssadmin/goVirtualOperationLogJSP.html?url=ssadmin/addVirtualOperationLog"
					height="280" width="800" target="dialog" rel="addVirtualOperation"><span>Add</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteVirtualOperationLog.html">
				<li><a class="delete"
					href="ssadmin/deleteVirtualOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/auditVirtualOperationLog.html">
				<li><a class="edit"
					href="ssadmin/auditVirtualOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to approve?"><span>Approve</span> </a></li>
				<li><a class="edit"
					href="ssadmin/sendVirtualOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to unfreeze?"><span>Unfreeze</span> </a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>Username</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>Nickname</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>Real Name</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname" }'> class="${param.orderDirection}"  </c:if>>Cryptocurrency Name</th>
				<th width="60" orderField="fqty"
					<c:if test='${param.orderField == "fqty" }'> class="${param.orderDirection}"  </c:if>>Qty.</th>
				<th width="60" orderField=fcreateTime
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Time of Approval</th>
				<th width="60" orderField="fcreator.fname"
					<c:if test='${param.orderField == "fcreator.fname" }'> class="${param.orderDirection}"  </c:if>>Approver</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualoperationlogList}"
				var="virtualoperationlog" varStatus="num">
				<tr target="sid_user" rel="${virtualoperationlog.fid}">
					<td>${num.index +1}</td>
					<td>${virtualoperationlog.fuser.floginName}</td>
					<td>${virtualoperationlog.fuser.fnickName}</td>
					<td>${virtualoperationlog.fuser.frealName}</td>
					<td>${virtualoperationlog.fstatus_s}</td>
					<td>${virtualoperationlog.fvirtualcointype.fShortName}</td>
					<td>${virtualoperationlog.fqty}</td>
					<td>${virtualoperationlog.fcreateTime}</td>
					<td>${virtualoperationlog.fcreator.fname}</td>
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
