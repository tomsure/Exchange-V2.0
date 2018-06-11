<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/killHistoryList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="logDate" value="${logDate}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/killHistoryList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
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
				href="ssadmin/goKillhistoryJSP.html?url=ssadmin/addKillhistory"
				height="280" width="800" target="dialog" rel="addKillhistory"><span>新增</span>
			</a>
			</li>
			<li><a class="delete"
				href="ssadmin/deleteKillhistory.html?uid={sid_user}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a>
			</li>
			<li><a class="edit"
				href="ssadmin/auditKillhistory.html?fid={sid_user}"
				target="ajaxTodo" title="确定要审核吗?"><span>审核</span> </a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">状态</th>
				<th width="60">类型</th>
				<th width="60">用户ID</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${killHistoryList}" var="killHistory" varStatus="num">
				<tr target="sid_user" rel="${killHistory.fid}">
					<td>${num.index +1}</td>
					<td>${killHistory.fstatus_s}</td>
					<td>${killHistory.ftype_s}</td>
					<td>${killHistory.fuser}</td>
					<td>${killHistory.fcreatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
