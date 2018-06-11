<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/aboutList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/aboutList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Title<input type="text" name="keywords" value="${keywords}"
						size="60" />
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
			<li><a class="add"
				href="ssadmin/goAboutJSP.html?url=ssadmin/addAbout"
				height="500" width="900" target="dialog" rel="addAbout"><span>Add</span>
			</a></li>
			<li><a class="delete"
				href="ssadmin/deleteAbout.html?uid={sid_user}" target="ajaxTodo"
				title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a></li>
			<shiro:hasPermission name="ssadmin/updateAbout.html">
				<li><a class="edit"
					href="ssadmin/goAboutJSP.html?url=ssadmin/updateAbout&uid={sid_user}"
					height="500" width="900" target="dialog" rel="updateAdmin"><span>Modify</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">FID</th>
				<th width="60">Type</th>
				<th width="60">Title</th>
				<th width="100">Contents</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${aboutList}" var="about" varStatus="num">
				<tr target="sid_user" rel="${about.fid}">
					<td>${about.fid}</td>
					<td>${about.ftype}</td>
					<td>${about.ftitle}</td>
					<td>${about.fcontent_s}</td>
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
