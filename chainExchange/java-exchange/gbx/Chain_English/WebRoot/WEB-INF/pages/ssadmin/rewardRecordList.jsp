<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/rewardRecordList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="ftype" value="${ftype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/rewardRecordList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键字：<input type="text" name="keywords" value="${keywords}"
						size="60" /> [会员名、昵称、真实姓名、活动标题]</td>
					<td></td>
					<td>奖励状态： <select type="combox" name="ftype">
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
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/auditRewardRecord.html?type=1">
				<li><a class="edit"
					href="ssadmin/auditRewardRecord.html?uid={sid_user}&type=1"
					target="ajaxTodo" title="确定要解除审核吗?"><span>审核</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/auditRewardRecord.html?type=2">
				<li><a class="edit"
					href="ssadmin/auditRewardRecord.html?uid={sid_user}&type=2"
					target="ajaxTodo" title="确定要拒绝吗?"><span>拒绝</span> </a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">登陆名</th>
				<th width="60">会员昵称</th>
				<th width="60">会员真实姓名</th>
				<th width="60">状态</th>
				<th width="60">活动标题</th>
				<th width="60">奖励原因</th>
				<th width="60">创建日期</th>
				<th width="60">最后更新日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rewardRecordList}" var="rewardRecord"
				varStatus="num">
				<tr target="sid_user" rel="${rewardRecord.fid}">
					<td>${num.index +1}</td>
					<td>${rewardRecord.fuser.floginName}</td>
					<td>${rewardRecord.fuser.fnickName}</td>
					<td>${rewardRecord.fuser.frealName}</td>
					<td>${rewardRecord.fstatus_s}</td>
					<td>${rewardRecord.factivity.ftitle}</td>
					<td>${rewardRecord.frewardReason_s}</td>
					<td>${rewardRecord.fcreateTime}</td>
					<td>${rewardRecord.flastUpdatetime}</td>
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
