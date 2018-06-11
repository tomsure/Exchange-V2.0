<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/entrustList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate1" value="${logDate1}" /><input
		type="hidden" name="price" value="${price}" /><input
		type="hidden" name="price1" value="${price1}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input
		type="hidden" name="ftype1" value="${ftype1}" /><input
		type="hidden" name="status" value="${status}" /><input
		type="hidden" name="entype" value="${entype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/entrustList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords" value="${keywords}"
						size="20" /></td>
					<td>Fiat Money<select type="combox" name="ftype1">
							<c:forEach items="${typeMap1}" var="type">
								<c:if test="${type.key == ftype1}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype1}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>	
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
					<td>Start Time<input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>End Time <input type="text" name="logDate1" class="date"
						readonly="true" value="${logDate1 }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					
					<td>Open Trice<input type="text" name="price" value="${price}"
						size="10" /></td>
					<td>Close price<input type="text" name="price1" value="${price1}"
						size="10" /></td>	
					<td>Status <select type="combox" name="status">
							<c:forEach items="${statusMap}" var="s">
								<c:if test="${s.key == status}">
									<option value="${s.key}" selected="true">${s.value}</option>
								</c:if>
								<c:if test="${s.key != status}">
									<option value="${s.key}">${s.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
					<td>Type <select type="combox" name="entype">
							<c:forEach items="${entypeMap}" var="t">
								<c:if test="${t.key == entype}">
									<option value="${t.key}" selected="true">${t.value}</option>
								</c:if>
								<c:if test="${t.key != entype}">
									<option value="${t.key}">${t.value}</option>
								</c:if>
							</c:forEach>
					</select>
					
					</td>	
					<td><button type="submit">Search</button></td>
				</tr>
			</table>
			
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<shiro:hasPermission name="ssadmin/cancelEntrust.html">
			<li><a title="Confirm: Are you sure you want to cancel?" target="selectedTodo" rel="ids"
				postType="delete" href="ssadmin/cancelEntrust.html"
				class="edit"><span>Cancel</span> </a>
			</li>
			<li class="line">line</li>
		<li><a class="icon" href="ssadmin/entrustExport.html"
					target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to export the record?"><span>Export</span>
				</a></li>
		</shiro:hasPermission>
		
		</ul>
	</div>
	<table class="table" width="150%" layoutH="138">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="20">Order ID</th>
				<th width="60">User UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName"}'> class="${param.orderDirection}"  </c:if>>Username</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName"}'> class="${param.orderDirection}"  </c:if>>Nickname</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName"}'> class="${param.orderDirection}"  </c:if>>Real Name</th>
				<th width="60">Fiat Money</th>
				<th width="60">Trading Cryptocurrency</th>
				<th width="60" orderField="fentrustType"
					<c:if test='${param.orderField == "fentrustType"}'> class="${param.orderDirection}"  </c:if>>Trading Type</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus"}'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="60" orderField="fprize"
					<c:if test='${param.orderField == "fprize"}'> class="${param.orderDirection}"  </c:if>>Unit Trice</th>
				<th width="60" orderField="fcount"
					<c:if test='${param.orderField == "fcount"}'> class="${param.orderDirection}"  </c:if>>Quantity</th>
				<th width="60" orderField="fleftCount"
					<c:if test='${param.orderField == "fleftCount"}'> class="${param.orderDirection}"  </c:if>>Pending</th>
				<th width="60">Completed</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount"}'> class="${param.orderDirection}"  </c:if>>Total Amount</th>
				<th width="60" orderField="fsuccessAmount"
					<c:if test='${param.orderField == "fsuccessAmount"}'> class="${param.orderDirection}"  </c:if>>Total of Transactions</th>
				<th width="60">Total Trading Fee</th>
				<th width="60">Remaining Trading Fee</th>
				<th width="100" orderField="flastUpdatTime"
					<c:if test='${param.orderField == "flastUpdatTime"}'> class="${param.orderDirection}"  </c:if>>Modified Time</th>
				<th width="100" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime"}'> class="${param.orderDirection}"  </c:if>>Create Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entrustList}" var="entrust" varStatus="num">
				<tr target="sid_user" rel="${entrust.fid}">
				    <td><input name="ids" value="${entrust.fid}"
						type="checkbox">
					</td>
					<td>${entrust.fid}</td>
					<td>${entrust.fuser.fid}</td>
					<td>${entrust.fuser.floginName}</td>
					<td>${entrust.fuser.fnickName}</td>
					<td>${entrust.fuser.frealName}</td>
					<td>${entrust.ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName}</td>
					<td>${entrust.ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}</td>
					<td>${entrust.fentrustType_s}</td>
					<td>${entrust.fstatus_s}</td>
					<td><fmt:formatNumber value="${entrust.fprize}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fcount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fleftCount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fcount - entrust.fleftCount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fsuccessAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.ffees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fleftfees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td>${entrust.flastUpdatTime}</td>
					<td>${entrust.fcreateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>Total: ${totalCount}；
			 Total number of inquiries：<font color="red"><fmt:formatNumber value="${totalAmt }" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></font>;
			 Total transaction amount of inquiry interval：<font color="red"><fmt:formatNumber value="${totalQty }" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></font>;
			 Inquiry interval total fee：<font color="red"><fmt:formatNumber value="${fees }" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></font>;</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
