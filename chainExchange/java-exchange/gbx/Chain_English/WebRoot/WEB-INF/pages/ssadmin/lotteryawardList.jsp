<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/lotteryawardList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/lotteryawardList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>奖品名称：<input type="text" name="keywords" value="${keywords}"
						size="60" />
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
					href="ssadmin/goLotteryawardJSP.html?url=ssadmin/addLotteryaward"
					height="400" width="800" target="dialog" rel="addLotteryaward"><span>新增</span>
				</a>
				</li>
				<li><a class="edit"
					href="ssadmin/goLotteryawardJSP.html?url=ssadmin/updateLotteryaward&uid={sid_user}"
					height="400" width="800" target="dialog" rel="updateLotteryaward"><span>修改</span>
				</a>
				</li>
				<li><a class="delete"
					href="ssadmin/deleteLotteryaward.html?uid={sid_user}"
					target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a>
				</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">奖品ID</th>
				<th width="100">奖品名称</th>
				<th width="60">奖品数量</th>
				<th width="60">奖品单位</th>
				<th width="60">奖品类型</th>
				<th width="60">中奖概率</th>
			<!-- 	<th width="60">位置</th> -->
				<th width="60">奖品剩余总数量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lotteryawardList}" var="lotteryaward"
				varStatus="num">
				<tr target="sid_user" rel="${lotteryaward.fid}">
					<td>${lotteryaward.fid}</td>
					<td>${lotteryaward.fname}</td>
					<td>${lotteryaward.count}</td>
					<td>${lotteryaward.funit}</td>
					<td>${lotteryaward.ftype_s}</td>
					<td><fmt:formatNumber value="${lotteryaward.fchance}"  pattern="##.##" maxIntegerDigits="15" maxFractionDigits="10"/>%</td>
				   <%--  <td>${lotteryaward.fangle}</td> --%>
				    <td>${lotteryaward.ftotal}</td>
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
