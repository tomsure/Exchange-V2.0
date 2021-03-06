<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/virtualCapitalOutList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/virtualCapitalOutList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
					<td>Cryptocurrency Type<select type="combox" name="ftype">
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
			<shiro:hasPermission name="ssadmin/viewVirtualCaptual.html">
				<li><a class="edit"
					href="ssadmin/goVirtualCapitaloperationJSP.html?uid={sid_user}&url=ssadmin/viewVirtualCaptual"
					height="320" width="800" target="dialog"
					rel="viewVirtualCapitaloperation" title="Confirm: Are you sure you want to approve?"><span>Approve</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/viewVirtualCaptual.html?type=1">
				<li><a class="edit"
					href="ssadmin/goVirtualCapitaloperationChangeStatus.html?uid={sid_user}&type=1"
					height="320" width="800" target="ajaxTodo"
					rel="viewVirtualCapitaloperation" title="Confirm: Are you sure you want to Lock?"><span>Lock</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/viewVirtualCaptual.html?type=2">
				<li><a class="edit"
					href="ssadmin/goVirtualCapitaloperationChangeStatus.html?uid={sid_user}&type=2"
					height="320" width="800" target="ajaxTodo"
					rel="viewVirtualCapitaloperation" title="Confirm: Are you sure you want to Cancel Locking?"><span>Cancel Locking</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/viewVirtualCaptual.html?type=3">
				<li><a class="edit"
					href="ssadmin/goVirtualCapitaloperationChangeStatus.html?uid={sid_user}&type=3"
					height="320" width="800" target="ajaxTodo"
					rel="viewVirtualCapitaloperation" title="Confirm: Are you sure you want to Cancel Withdrawal?"><span>Cancel Withdrawal</span>
				</a>
				</li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" href="ssadmin/virtualCapitalOutListExport.html"
				target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to Export?"><span>Export</span>
			</a></li>
		</ul>
	</div>
	<table class="table" width="150%" layoutH="138">
		<thead>
			<tr>
				<th width="20">UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>Username</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>Nickname</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>Real Name</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname" }'> class="${param.orderDirection}"  </c:if>>Cryptocurrency Type</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>Transaction Type</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>Qty.</th>
				<th width="60" orderField="ffees"
					<c:if test='${param.orderField == "ffees" }'> class="${param.orderDirection}"  </c:if>>Trading Fee</th>
				<th width="60">Withdrawal Address</th>
				<th width="60">Trading ID</th>
			<!--	<th width="60">Remark</th> -->
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
				<th width="60" orderField="flastUpdateTime"
					<c:if test='${param.orderField == "flastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>Time of Last Change</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualCapitaloperationList}"
				var="virtualCapitaloperation" varStatus="num">
				<tr target="sid_user" rel="${virtualCapitaloperation.fid}">
					<td>${virtualCapitaloperation.fuser.fid}</td>
					<td>${virtualCapitaloperation.fuser.floginName}</td>
					<td>${virtualCapitaloperation.fuser.fnickName}</td>
					<td>${virtualCapitaloperation.fuser.frealName}</td>
					<td>${virtualCapitaloperation.fvirtualcointype.fShortName}</td>
					<td>${virtualCapitaloperation.fstatus_s}</td>
					<td>${virtualCapitaloperation.ftype_s}</td>
					<td><fmt:formatNumber value="${virtualCapitaloperation.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${virtualCapitaloperation.ffees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${virtualCapitaloperation.withdraw_virtual_address}</td>
					<td>${virtualCapitaloperation.ftradeUniqueNumber}</td>
				<!--	<td>${virtualCapitaloperation.fremark}</td> -->
					<td>${virtualCapitaloperation.fcreateTime}</td>
					<td>${virtualCapitaloperation.flastUpdateTime}</td>
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
