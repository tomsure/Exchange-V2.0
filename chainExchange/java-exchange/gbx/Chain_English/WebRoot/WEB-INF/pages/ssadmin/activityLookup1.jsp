<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/activityLookup.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="1" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post"
		action="ssadmin/activityLookup.html"
		onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[标题、内容]</td>
					<td>虚拟币类型： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">标题</th>
				<th width="60">状态</th>
				<th width="60">活动类型</th>
				<th width="60">内容</th>
				<th width="60">创建日期</th>
				<th width="60">更新日期</th>
				<th width="60">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${activityList}" var="activity" varStatus="num">
				<tr target="sid_user" rel="${activity.fid}">
					<td>${num.index +1}</td>
					<td>${activity.ftitle}</td>
					<td>${activity.fstatus_s}</td>
					<td>${activity.factivitytype.fvirtualCoinType.fname}${activity.factivitytype.fname}</td>
					<td>${activity.fcontent}</td>
					<td>${activity.fcreateTime}</td>
					<td>${activity.flastUpdateTime}</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({id:'${activity.fid}',
						 ftitle:'${activity.ftitle}'})"
						title="查找带回">选择</a></td>
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
