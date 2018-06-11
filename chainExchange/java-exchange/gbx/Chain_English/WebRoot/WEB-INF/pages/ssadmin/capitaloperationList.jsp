<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	  action="ssadmin/capitaloperationList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="fstatus" value="${fstatus}" /> <input
		type="hidden" name="capitalId" value="${capitalId}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /><input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		  action="ssadmin/capitaloperationList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords" value="${keywords}"
									size="30" /></td>
					<td>Deposit ID<input type="text" name="capitalId"
									value="${capitalId}" size="10" /></td>
					<td>Status<select type="combox" name="fstatus">
						<c:forEach items="${statusMap}" var="status">
							<c:if test="${status.key == fstatus}">
								<option value="${status.key}" selected="true">${status.value}</option>
							</c:if>
							<c:if test="${status.key != fstatus}">
								<option value="${status.key}">${status.value}</option>
							</c:if>
						</c:forEach>
					</select></td>
					<td>Start Date<input type="text" name="logDate" class="date"
									 readonly="true" value="${logDate }" />
					</td>
					<td>End Date<input type="text" name="logDate2" class="date"
									 readonly="true" value="${logDate2 }" />
					</td>
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
			<shiro:hasPermission name="ssadmin/capitaloperationExport.html">
				<li><a class="icon" href="ssadmin/capitaloperationExport.html"
					   target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to export the records?"><span>Excel</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
		<tr>
			<th width="20">UID</th>
			<th width="20">Order ID </th>
			<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>Username</th>
			<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>Nickname</th>
			<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>Real Name</th>
			<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>Type</th>
			<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Status</th>
			<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>Amount</th>
			<th width="60" orderField="fCnyAmount"
					<c:if test='${param.orderField == "fCnyAmount" }'> class="${param.orderDirection}"  </c:if>>CNY Amount</th>
			<th width="60" orderField="ffees"
					<c:if test='${param.orderField == "ffees" }'> class="${param.orderDirection}"  </c:if>>Trading Fee</th>
			<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>Creation Time</th>
			<th width="60" orderField="fLastUpdateTime"
					<c:if test='${param.orderField == "fLastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>Time of Last Change</th>
			<th width="60">Approver</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${capitaloperationList}" var="capitaloperation"
				   varStatus="num">
			<tr target="sid_user" rel="${capitaloperation.fid}">
				<td>${capitaloperation.fuser.fid}</td>
				<td>${capitaloperation.fid}</td>
				<td>${capitaloperation.fuser.floginName}</td>
				<td>${capitaloperation.fuser.fnickName}</td>
				<td>${capitaloperation.fuser.frealName}</td>
				<td>${capitaloperation.ftype_s}</td>
				<td>${capitaloperation.fstatus_s}</td>
				<td><fmt:formatNumber value="${capitaloperation.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
				<td><fmt:formatNumber value="${capitaloperation.fCnyAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
				<td><fmt:formatNumber value="${capitaloperation.ffees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
				<td>${capitaloperation.fcreateTime}</td>
				<td>${capitaloperation.fLastUpdateTime}</td>
				<td>${capitaloperation.fAuditee_id.fname}</td>
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
