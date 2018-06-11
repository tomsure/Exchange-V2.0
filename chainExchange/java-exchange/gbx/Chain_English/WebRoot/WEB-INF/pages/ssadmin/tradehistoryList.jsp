<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/tradehistoryList.html">
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
		action="ssadmin/tradehistoryList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
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
					<td>Trading Mryptocurrency<select type="combox" name="ftype">
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
					<td>Time<input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }"  dateFmt="yyyy-MM-dd"/>
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
		
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="22">Serial Number</th>
				<th width="60">Fiat Money</th>
				<th width="60">Trading Cryptocurrency</th>
				<th width="60">Close Price</th>
				<th width="60">Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tradehistoryList}" var="tradehistory" varStatus="num">
				<tr target="sid_user" rel="${tradehistory.fid}">
				    <td>${num.index+1 }</td>
					<td>${tradehistory.ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName}</td>
					<td>${tradehistory.ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}</td>
					<td><fmt:formatNumber value="${tradehistory.fprice}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td>${tradehistory.fdate}</td>
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
