<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/virtualwalletList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="ftype" value="${ftype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/virtualwalletList.html" method="post">
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
	<li><a class="icon" href="ssadmin/virtualwalletExport.html"
					target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to export the records?"><span>Export</span>
				</a></li>
	</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">UID</th>
				<th width="60">Username</th>
				<th width="60">Nickname</th>
				<th width="60">Real Name</th>
				<th width="60" orderField="fvirtualcointype.fid"
					<c:if test='${param.orderField == "fvirtualcointype.fid" }'> class="${param.orderDirection}"  </c:if>>Currency Type</th>
				<th width="60" orderField="ftotal"
					<c:if test='${param.orderField == "ftotal" }'> class="${param.orderDirection}"  </c:if>>Total Qty.</th>
				<th width="60" orderField="ffrozen"
					<c:if test='${param.orderField == "ffrozen" }'> class="${param.orderDirection}"  </c:if>>Frozen Qty.</th>
				<th width="60" orderField="ftotal"
					<c:if test='${param.orderField == "ftotal" }'> class="${param.orderDirection}"  </c:if>>Available Qty. of Current Date (for restricted)</th>
				<th width="60" orderField="flastUpdateTime"
					<c:if test='${param.orderField == "flastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>Time of Last Change</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualwalletList}" var="virtualwallet"
				varStatus="num">
				<tr target="sid_user" rel="${virtualwallet.fid}">
					<td>${virtualwallet.fuser.fid}</td>
					<td>${virtualwallet.fuser.floginName}</td>
					<td>${virtualwallet.fuser.fnickName}</td>
					<td>${virtualwallet.fuser.frealName}</td>
					<td>${virtualwallet.fvirtualcointype.fShortName}</td>
					<td><fmt:formatNumber value="${virtualwallet.ftotal}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${virtualwallet.ffrozen}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${virtualwallet.fcanSellQty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${virtualwallet.flastUpdateTime}</td>
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
