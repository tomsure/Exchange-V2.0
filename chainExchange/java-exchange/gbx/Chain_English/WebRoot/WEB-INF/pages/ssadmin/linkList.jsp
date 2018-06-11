<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/linkList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/linkList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Keywords<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
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
			<shiro:hasPermission name="ssadmin/addLink.html">
				<li><a class="add"
					href="ssadmin/goLinkJSP.html?url=ssadmin/addLink&type=1"
					height="300" width="800" target="dialog" rel="addLink"><span>Add</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteLink.html">
				<li><a class="delete"
					href="ssadmin/deleteLink.html?uid={sid_user}" target="ajaxTodo"
					title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateLink.html">
				<li><a class="edit"
					href="ssadmin/goLinkJSP.html?url=ssadmin/updateLink&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateLink"><span>Edit</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Name</th>
				<th width="60">Type</th>
				<th width="60">Link</th>
				<th width="60">Sequence</th>
				<th width="60">Description</th>
				<th width="60">Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${linkList}" var="link" varStatus="num">
				<tr target="sid_user" rel="${link.fid}">
					<td>${num.index +1}</td>
					<td>${link.fname}</td>
					<td>${link.ftype_s}</td>
					<td>${link.furl}</td>
					<td>${link.forder}</td>
					<td>${link.fdescription}</td>
					<td>${link.fcreateTime}</td>
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
