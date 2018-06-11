<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/introlinfoList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" /><input
		type="hidden" name="logDate" value="${logDate}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/introlinfoList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
					<td>Date <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" /></td>
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
		<shiro:hasPermission name="ssadmin/introlinfoExport.html">
			<li><a class="icon" href="ssadmin/introlinfoExport.html"
				target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to export the records?"><span>Export</span>
			</a>
			</li>
		</shiro:hasPermission>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20" orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>UID</th>
				<th width="60">Username</th>
				<th width="60">Nickname</th>
				<th width="60">Real Name</th>
				<th width="60">Valid USD</th>
				<th width="100">Title of earnings</th>
				<th width="60">Number of earnings</th>
				<th width="60" orderField="fcreatetime"
					<c:if test='${param.orderField == "fcreatetime" }'> class="${param.orderDirection}"  </c:if>>Creation time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${introlinfoList}" var="introlinfo" varStatus="num">
				<tr target="sid_user" rel="${introlinfo.fid}">
					<td>${introlinfo.fuser.fid}</td>
					<td>${introlinfo.fuser.floginName}</td>
					<td>${introlinfo.fuser.fnickName}</td>
					<td>${introlinfo.fuser.frealName}</td>
					<td>${introlinfo.fiscny}</td>
					<td>${introlinfo.ftitle}</td>
					<td>${introlinfo.fqty}</td>
					<td>${introlinfo.fcreatetime}</td>
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
