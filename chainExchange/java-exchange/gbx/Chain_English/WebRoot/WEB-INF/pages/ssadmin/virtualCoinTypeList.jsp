<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/virtualCoinTypeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/virtualCoinTypeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Keyword(s)<input type="text" name="keywords" value="${keywords}"
						size="60" />[Name, abbr., description]</td>
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
			<shiro:hasPermission
				name="ssadmin/addVirtualCoinType">
				<li><a class="add"
					href="ssadmin/goVirtualCoinTypeJSP.html?url=ssadmin/addVirtualCoinType"
					height="350" width="800" target="dialog" rel="addVirtualCoinType"><span>Add</span>
				</a>
				</li>
		     </shiro:hasPermission>
			<shiro:hasPermission
				name="ssadmin/deleteVirtualCoinType.html">
				<li><a class="delete"
					href="ssadmin/deleteVirtualCoinType.html?uid={sid_user}&status=1"
					target="ajaxTodo" title="Confirm: Are you sure you want to disable?"><span>Disable</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/goWallet.html">
				<li><a class="edit"
					href="ssadmin/goVirtualCoinTypeJSP.html?url=ssadmin/goWallet&uid={sid_user}"
					height="250" width="700" target="dialog" rel="goWallet"><span>Enable</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateVirtualCoinType.html">
				<li><a class="edit"
					href="ssadmin/goVirtualCoinTypeJSP.html?url=ssadmin/updateVirtualCoinType&uid={sid_user}"
					height="350" width="800" target="dialog"
					rel="updateVirtualCoinType"><span>Edit</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateCoinFees.html">
				<li><a class="edit"
					href="ssadmin/goVirtualCoinTypeJSP.html?url=ssadmin/updateCoinFees&uid={sid_user}"
					height="500" width="750" target="dialog"
					rel="updateVirtualCoinType"><span>Edit Withdrawal Trading Fee</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/testWallet.html">
				<li><a class="edit"
					href="ssadmin/testWallet.html?uid={sid_user}" target="ajaxTodo"><span>Test the Wallet</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/createAddress.html">
				<li><a class="edit"
					href="ssadmin/goVirtualCoinTypeJSP.html?url=ssadmin/createAddress&uid={sid_user}"
					height="250" width="700" target="dialog" rel="createWalletAddress"><span>Generate Wallet Addr.</span>
				</a>
				</li>
				<li><a class="edit"
					href="ssadmin/goVirtualCoinTypeJSP.html?url=ssadmin/etcMainAddr&uid={sid_user}"
					height="250" width="700" target="dialog" rel="etcMainAddr"><span>Summarize to Primary Address</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">FID</th>
				<th width="60" orderField="fname"
					<c:if test='${param.orderField == "fname" }'> class="${param.orderDirection}"  </c:if>>Currency</th>
				<th width="60" orderField="fShortName"
					<c:if test='${param.orderField == "fShortName" }'> class="${param.orderDirection}"  </c:if>>Abbr.</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="30">Symbol</th>
				<th width="60">IP Address</th>
				<th width="60">Port No.</th>
				<th width="60">Valid Deposit</th>
				<th width="60">Valid Withdraw</th>
				<th width="60">Valid Auto-received Deposit</th>
				<th width="60">Base Exchange Token</th>
				<th width="60">Total Qty.</th>
				<th width="60" orderField="faddTime"
					<c:if test='${param.orderField == "faddTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualCoinTypeList}" var="virtualCoinType"
				varStatus="num">
				<tr target="sid_user" rel="${virtualCoinType.fid}">
					<td>${virtualCoinType.fid}</td>
					<td>${virtualCoinType.fname}</td>
					<td>${virtualCoinType.fShortName}</td>
					<td>
						<c:if test="${1 == virtualCoinType.fstatus}">Enable</c:if>
						<c:if test="${2 == virtualCoinType.fstatus}">Disable</c:if>
					</td>
					<td>${virtualCoinType.fSymbol}</td>
					<td>${virtualCoinType.fip}</td>
					<td>${virtualCoinType.fport}</td>
					<td>${virtualCoinType.fisrecharge}</td>
					<td>${virtualCoinType.FIsWithDraw}</td>
					<td>${virtualCoinType.fisauto}</td>
					<td>
						<c:if test="${1 == virtualCoinType.ftype}">Yes</c:if>
						<c:if test="${2 == virtualCoinType.ftype}">No</c:if>
					</td>
					<td><fmt:formatNumber value="${virtualCoinType.ftotalqty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${virtualCoinType.faddTime}</td>
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
