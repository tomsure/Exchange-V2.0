<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/handingSharePlanList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="1" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/handingSharePlanList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>标题：<input type="text" name="keywords" value="${keywords}"
						size="60" />
					</td>
					<td>虚拟币类型： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="ssadmin/goSharePlanJSP.html?url=ssadmin/addHandingSharePlan"
				height="350" width="800" target="dialog" rel="addHandingSharePlan"><span>新增</span>
			</a></li>
			<li><a class="edit"
				href="ssadmin/goSharePlanJSP.html?url=ssadmin/updateHandingSharePlan&uid={sid_user}"
				height="350" width="800" target="dialog"
				rel="updateHandingSharePlan"><span>修改</span> </a></li>
			<li><a class="delete"
				href="ssadmin/deleteSharePlan.html?uid={sid_user}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span> </a></li>
			<li><a class="edit"
				href="ssadmin/auditSharePlan.html?uid={sid_user}" target="ajaxTodo"
				title="确定要审核吗?"><span>审核</span> </a></li>
			<li><a class="edit" href="ssadmin/sendMoney.html?uid={sid_user}"
				target="ajaxTodo" title="确定要发币吗?"><span>发币</span> </a></li>
			<li><a class="edit"
				href="ssadmin/sharePlanLogList.html?parentId={sid_user}"
				height="400" width="800" target="dialog" rel="sharePlanLogList"><span>查看记录</span>
			</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">标题</th>
				<th width="60">状态</th>
				<th width="60">持币名称</th>
				<th width="60">分红币名称</th>
				<th width="60">分红总币数量</th>
				<th width="30">待发送数</th>
				<th width="30">已发送数</th>
				<th width="60">有效开始时间</th>
				<th width="60">有效结束时间</th>
				<th width="60">审核人</th>
				<th width="60">审核时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${handingSharePlanList}" var="sharePlan"
				varStatus="num">
				<tr target="sid_user" rel="${sharePlan.fid}">
					<td>${num.index +1}</td>
					<td>${sharePlan.ftitle}</td>
					<td>${sharePlan.fstatus_s}</td>
					<td>${sharePlan.fvirtualcointype.fname}</td>
					<td>${sharePlan.ftypes}</td>
					<td>${sharePlan.famount}</td>
					<td>${sharePlan.noSend}</td>
					<td>${sharePlan.hasSend}</td>
					<td>${sharePlan.fstartDate}</td>
					<td>${sharePlan.fendDate}</td>
					<td>${sharePlan.fcreator.fname}</td>
					<td>${sharePlan.fcreateTime}</td>
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
