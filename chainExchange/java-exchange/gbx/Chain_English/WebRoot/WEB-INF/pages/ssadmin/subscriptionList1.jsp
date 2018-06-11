<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/subscriptionList1.html">
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
		action="ssadmin/subscriptionList1.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Type of ICO cryptocurrency <select type="combox" name="ftype">
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
				<li><a class="add"
					href="ssadmin/goSubscriptionJSP.html?url=ssadmin/addSubscription1"
					height="430" width="800" target="dialog" rel="addSubscription1"><span>Add</span>
				</a>
				</li>
				<li><a class="edit"
					href="ssadmin/goSubscriptionJSP.html?url=ssadmin/updateSubscription1&uid={sid_user}"
					height="430" width="800" target="dialog" rel="updateSubscription1"><span>Modify</span>
				</a>
				</li>
				<li><a class="delete"
					href="ssadmin/deleteSubscription.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to delete?"><span>Delete</span> </a>
				</li>
				<li><a class="edit"
					href="ssadmin/subscriptionLogList.html?parentId={sid_user}"
					height="500" width="800" target="navTab" rel="subscriptionLogList"><span>View details</span>
				</a>
				</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<%--<th width="30">付款类型</th>--%>
				<th width="30">Type</th>
				<th width="30">Ratio</th>
				<th width="30">Valid Unfreezing</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname" }'> class="${param.orderDirection}"  </c:if>>Cryptocurrency Type</th>
				<th width="60" orderField="fisopen"
					<c:if test='${param.orderField == "fisopen" }'> class="${param.orderDirection}"  </c:if>>Valid ICO</th>
				<th width="60" orderField="ftotal"
					<c:if test='${param.orderField == "ftotal" }'> class="${param.orderDirection}"  </c:if>>Total</th>
				<th width="60" orderField="fAlreadyByCount"
					<c:if test='${param.orderField == "fAlreadyByCount" }'> class="${param.orderDirection}"  </c:if>>Number of ICO</th>
				<th width="60" orderField="fprice"
					<c:if test='${param.orderField == "fprice" }'> class="${param.orderDirection}"  </c:if>>Exchange Ratio</th>
				<th width="60" orderField="fbuyCount"
					<c:if test='${param.orderField == "fbuyCount" }'> class="${param.orderDirection}"  </c:if>>Maximum/Person</th>
				<th width="60" orderField="fminbuyCount"
					<c:if test='${param.orderField == "fminbuyCount" }'> class="${param.orderDirection}"  </c:if>>Minimum/Person</th>
				<th width="60" orderField="fbuyTimes"
					<c:if test='${param.orderField == "fbuyTimes" }'> class="${param.orderDirection}"  </c:if>>Max Times/Person</th>
				<th width="60" orderField="fbeginTime"
					<c:if test='${param.orderField == "fbeginTime" }'> class="${param.orderDirection}"  </c:if>>Start Time</th>
				<th width="60" orderField="fendTime"
					<c:if test='${param.orderField == "fendTime" }'> class="${param.orderDirection}"  </c:if>>End Time</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${subscriptionList1}" var="subscription"
				varStatus="num">
				<tr target="sid_user" rel="${subscription.fid}">
					<td>${num.index +1}</td>
					<%--<td>${subscription.symbol}</td>--%>
					<td>${subscription.ffrozenType_s}</td>
					<td>${subscription.frate}</td>
					<td>${subscription.fisstart}</td>
					<td>${subscription.fvirtualcointype.fShortName}</td>
					<td>${subscription.fisopen}</td>
					<td><fmt:formatNumber value="${subscription.ftotal}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${subscription.fAlreadyByCount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${subscription.fprices}</td>
					<td>${subscription.fbuyCount}</td>
					<td>${subscription.fminbuyCount}</td>
					<td>${subscription.fbuyTimes}</td>
					<td>${subscription.fbeginTime}</td>
					<td>${subscription.fendTime}</td>
					<td>${subscription.fcreateTime}</td>
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
