<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/adminList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/adminList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Admin Name<input type="text" name="keywords"
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
			<shiro:hasPermission name="ssadmin/addAdmin.html">
				<li><a class="add"
					href="ssadmin/goAdminJSP.html?url=ssadmin/addAdmin" height="300"
					width="800" target="dialog" rel="addAdmin"><span>Add</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/forbbinAdmin.html?status=1">
				<li><a class="delete"
					href="ssadmin/forbbinAdmin.html?uid={sid_user}&status=1"
					target="ajaxTodo" title="Confirm: Are you sure you want to disable?"><span>Disable</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/forbbinAdmin.html?status=2">
				<li><a class="edit"
					href="ssadmin/forbbinAdmin.html?uid={sid_user}&status=2"
					target="ajaxTodo" title="Confirm: Are you sure you want to enable?"><span>Enable</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateAdminPassword.html">
				<li><a class="edit"
					href="ssadmin/goAdminJSP.html?url=ssadmin/updateAdmin&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateAdmin"><span>Modify Password</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateAdminRole.html">
				<li><a class="edit"
					href="ssadmin/goAdminJSP.html?url=ssadmin/updateAdminRole&uid={sid_user}"
					height="200" width="800" target="dialog" rel="updateAdmin"><span>Edit Role</span>
				</a></li>
				<li><a class="edit"
					href="ssadmin/goAdminJSP.html?url=ssadmin/updateAdminUser&uid={sid_user}"
					height="200" width="800" target="dialog" rel="updateAdminUser"><span>Associate Google AUTH of Admin</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Admin Name</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="40">Role</th>
				<th width="40">Creation Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adminList}" var="admin" varStatus="num">
				<tr target="sid_user" rel="${admin.fid}">
					<td>${num.index +1}</td>
					<td>${admin.fname}</td>
					<td>${admin.fstatus_s}</td>
					<td>${admin.frole.fname}</td>
					<td>${admin.fcreateTime}</td>
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
