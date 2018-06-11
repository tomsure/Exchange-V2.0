<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/lotterylogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="fstatus" value="${fstatus}" /> <input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="logDate" value="${logDate}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/lotterylogList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>会员登陆名：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
					<td>状态： <select type="combox" name="fstatus">
							<c:forEach items="${statusMap}" var="status">
								<c:if test="${status.key == fstatus}">
									<option value="${status.key}" selected="true">${status.value}</option>
								</c:if>
								<c:if test="${status.key != fstatus}">
									<option value="${status.key}">${status.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td>奖品类型： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td>日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" /></td>
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
			<li><a title="确定要发奖吗?" target="selectedTodo" rel="ids"
				postType="string" href="ssadmin/sendLotteryaward.html" class="edit"><span>发奖</span>
			</a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl"></th>
				<th width="40">用户ID</th>
				<th width="40">登陆名</th>
				<th width="40">手机号码</th>
				<th width="100">奖品</th>
				<th width="30">数量</th>
				<th width="50" orderField="fflag"
					<c:if test='${param.orderField == "fflag" }'> class="${param.orderDirection}"  </c:if>>是否实物奖品</th>
				<th width="60">状态</th>
				<th width="60">中奖时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lotterylogList}" var="lotterylog" varStatus="num">
				<tr target="sid_user" rel="${lotterylog.fid}">
					<td><input name="ids" value="${lotterylog.fid}"
						type="checkbox"></td>
					<td>${lotterylog.fuser.fid}</td>
					<td>${lotterylog.fuser.floginName}</td>
					<td>${lotterylog.fuser.ftelephone}</td>
					<td>${lotterylog.ftitle}</td>
					<td>${lotterylog.fqty}</td>
					<td>${lotterylog.fflag}</td>
					<td>${lotterylog.fstatus_s}</td>
					<td>${lotterylog.fcreateTime}</td>
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
