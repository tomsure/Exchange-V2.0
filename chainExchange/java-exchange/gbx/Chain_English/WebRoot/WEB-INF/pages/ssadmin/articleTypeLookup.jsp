<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<form id="pagerForm" method="post"
	action="ssadmin/articleTypeLookup.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post"
		action="ssadmin/articleTypeLookup.html"
		onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>keywords</label> <input class="textInput"
					name="keywords" value="${keywords}" type="text" size="70">
				</li>
			</ul>
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

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Type</th>
				<th width="60">Keywords</th>
				<th width="60">Description</th>
				<th width="30">Lookup back</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleTypeList}" var="articleType"
				varStatus="num">
				<tr>
					<td>${num.index +1}</td>
					<td>${articleType.fname}</td>
					<td>${articleType.fkeywords}</td>
					<td>${articleType.fdescription}</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({id:'${articleType.fid}', articleType:'${articleType.fname}'})"
						title="Lookup back">select</a>
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>Total: ${totalCount}</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>