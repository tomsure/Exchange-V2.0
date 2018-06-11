<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/subscriptionLogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="ftype" value="${ftype}" />  <input
		type="hidden" name="parentId" value="${parentId}" /><input
		type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/subscriptionLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<input type="hidden" name="parentId" value="${parentId}" />
					<td>Title<input type="text" name="keywords" value="${keywords}"
						size="60" />[Member Info]</td>
					<td>Status <select type="combox" name="ftype">
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
		    <%-- <li><a title="确实要指定这些记录的中签比例吗?" target="selectedTodo" rel="ids"
				postType="string" href="ssadmin/subFail.html"
				class="edit"><span>全部指定预设<font color="red">${requestScope.constant['subSuccessRate'] }份</font></span> </a>
			</li>
		    <li><a title="确实要操作这些记录吗?" target="selectedTodo" rel="ids"
				postType="string" href="ssadmin/subSuccess.html" 
				class="edit"><span>全部审核</span> </a>
			</li>
			<li><a title="确实要操作这些记录吗?" target="selectedTodo" rel="ids"
				postType="string" href="ssadmin/subSend.html"
				class="edit"><span>全部解冻</span> </a>
			</li> --%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="60">Type</th>
				<th width="60">Ratio</th>
				<th width="60">Status</th>
				<th width="60">Payment</th>
				<th width="60">Username</th>
				<th width="60">Nickname</th>
				<th width="60">Real Name</th>
				<th width="60">Total</th>
				<th width="60">Unfrozen Quantity</th>
				<th width="60">Price</th>
				<th width="60">Amount</th>
				<th width="60">Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${subscriptionLogList}" var="subscriptionLog"
				varStatus="num">
				<tr target="sid_user" rel="${subscriptionLog.fid}">
				   <td><input name="ids" value="${subscriptionLog.fid}"
						type="checkbox">
					</td>
					<td>${subscriptionLog.fsubscription.ffrozenType_s}</td>
					<td>${subscriptionLog.fsubscription.frate}</td>
					<td>${subscriptionLog.fstatus_s}</td>
					<td>${subscriptionLog.fsubscription.symbol}</td>
					<td>${subscriptionLog.fuser.floginName}</td>
					<td>${subscriptionLog.fuser.fnickName}</td>
					<td>${subscriptionLog.fuser.frealName}</td>
					<td>${subscriptionLog.fcount}</td>
					<td>${subscriptionLog.flastcount}</td>
					<td>${subscriptionLog.fprice}</td>
					<td>${subscriptionLog.ftotalCost}</td>
					<td>${subscriptionLog.fcreatetime}</td>
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
