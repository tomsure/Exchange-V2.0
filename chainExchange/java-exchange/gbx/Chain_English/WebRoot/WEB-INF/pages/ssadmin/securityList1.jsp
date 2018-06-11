<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/goSecurityJSP.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="treeId" value="${treeId}" /> <input type="hidden"
		name="url" value="ssadmin/securityList1" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form onsubmit="return divSearch(this, 'jbsxBox2moduleList');"
		action="ssadmin/goSecurityJSP.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Permission Name<input type="text" name="keywords"
						value="${keywords}" size="60" />
					</td>
					<input type="hidden" name="treeId" value="${treeId}" />
					<input type="hidden" name="url" value="ssadmin/securityList1" />
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
			<shiro:hasPermission name="ssadmin/addSecurity.html">
				<li><a class="add"
					href="ssadmin/goSecurityJSP.html?url=ssadmin/addSecurity&status=add&treeId1=${treeId}"
					height="250" width="800" target="dialog" rel="addLink"><span>Add</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteSecurity.html">
				<li><a class="delete"
					href="ssadmin/deleteSecurity.html?uid={sid_user}" target="ajaxTodo"
					title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateSecurity.html">
				<li><a class="edit"
					href="ssadmin/goSecurityJSP.html?url=ssadmin/updateSecurity&status=update&treeId1=${treeId}&uid={sid_user}"
					height="250" width="800" target="dialog" rel="updateLink"><span>Modify</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Permission Name</th>
				<th width="60">Access Address</th>
				<th width="60">Remarks</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${securityList}" var="security" varStatus="num">
				<tr target="sid_user" rel="${security.fid}">
					<td>${num.index +1}</td>
					<td>${security.fname}</td>
					<td>${security.furl}</td>
					<td>${security.fdescription}</td>
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
			currentPage="${currentPage}" rel="jbsxBox2moduleList"></div>
	</div>
</div>