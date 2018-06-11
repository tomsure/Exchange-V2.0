<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/limittradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/limittradeList.html" method="post">
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
			<shiro:hasPermission name="ssadmin/addLimittrade">
				<li><a class="add"
					href="ssadmin/goLimittradeJSP.html?url=ssadmin/addLimittrade&type=2"
					height="300" width="800" target="dialog" rel="addLimittrade"><span>Add</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteLimittrade">
				<li><a class="delete"
					href="ssadmin/deleteLimittrade.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateLimittrade">
				<li><a class="edit"
					href="ssadmin/goLimittradeJSP.html?url=ssadmin/updateLimittrade&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateLimittrade"><span>Edit</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Fiat Money</th>
				<th width="60">Trading Currenty</th>
				<th width="60">Lowest Price</th>
				<th width="60">Highest Price</th>
				<th width="60">Up/Down Ratio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${limittradeList}" var="limittrade" varStatus="num">
				<tr target="sid_user" rel="${limittrade.fid}">
					<td>${num.index +1}</td>
					<td>${limittrade.ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName}</td>
					<td>${limittrade.ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}</td>
					<td>${limittrade.fdownprice}</td>
					<td>${limittrade.fupprice}</td>
					<td>${limittrade.fpercent}</td>
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
