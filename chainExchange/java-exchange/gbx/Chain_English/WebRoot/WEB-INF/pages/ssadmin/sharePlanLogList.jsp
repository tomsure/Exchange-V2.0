<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/sharePlanLogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="parentId" value="${parentId}" /> <input
		type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);"
		action="ssadmin/sharePlanLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /><input type="hidden"
						name="parentId" value="${parentId}" />
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
			
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>会员登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60">状态</th>
<!-- 				<th width="60">参与金额/数量</th> -->
<!-- 				<th width="60">平台总金额/数量</th> -->
				<th width="60">分得金额/数量</th>
				<th width="60">创建日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sharePlanLogList}" var="sharePlanLog" varStatus="num">
				<tr target="sid_user" rel="${sharePlanLog.fid}">
					<td>${num.index +1}</td>
					<td>${sharePlanLog.fuser.floginName}</td>
					<td>${sharePlanLog.fuser.fnickName}</td>
					<td>${sharePlanLog.fuser.frealName}</td>
					<td>${sharePlanLog.fstatus_s}</td>
<!-- 					<td>${sharePlanLog.fselfAmount}</td> -->
<!-- 					<td>${sharePlanLog.fTotalAmount}</td> -->
					<td>${sharePlanLog.famount}</td>
					<td>${sharePlanLog.fcreatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
