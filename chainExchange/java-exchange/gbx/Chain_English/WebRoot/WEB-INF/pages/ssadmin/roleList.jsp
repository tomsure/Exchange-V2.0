<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/roleList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/roleList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Role Name<input type="text" name="keywords"
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
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/createRole.html">
				<li><a class="add"
					href="ssadmin/goRoleJSP.html?url=ssadmin/createRole&uid=1"
					height="400" width="850" target="dialog" rel="createRole"><span>Add</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateRole.html">
				<li><a class="edit"
					href="ssadmin/goRoleJSP.html?url=ssadmin/updateRole&uid=1&roleId={sid_user}"
					height="400" width="850" target="dialog" rel="updateLink"><span>Modify</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Role Name</th>
				<th width="60">Description</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${roleList}" var="role" varStatus="num">
				<tr target="sid_user" rel="${role.fid}">
					<td>${num.index +1}</td>
					<td>${role.fname}</td>
					<td>${role.fdescription}</td>
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
