<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/articleTypeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/articleTypeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Keyword(s)<input type="text" name="keywords" value="${keywords}"
						size="60" />[Name, title, keywords]</td>
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
			<shiro:hasPermission name="ssadmin/updateArticleType.html">
				<li><a class="edit"
					href="ssadmin/goArticleTypeJSP.html?url=ssadmin/updateArticleType&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateAricle"><span>Edit</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60" orderField="fname"
					<c:if test='${param.orderField == "fname" }'> class="${param.orderDirection}"  </c:if>>Type Name</th>
				<th width="60" orderField="fkeywords"
					<c:if test='${param.orderField == "fkeywords" }'> class="${param.orderDirection}"  </c:if>>Keywords</th>
				<th width="60">Description</th>
				<th width="60" orderField="flastCreateDate"
					<c:if test='${param.orderField == "flastCreateDate" }'> class="${param.orderDirection}"  </c:if>>Creation Time </th>
				<th width="60" orderField="flastModifyDate"
					<c:if test='${param.orderField == "flastModifyDate" }'> class="${param.orderDirection}"  </c:if>>Modification Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleTypeList}" var="articleType"
				varStatus="num">
				<tr target="sid_user" rel="${articleType.fid}">
					<td>${num.index +1}</td>
					<td>${articleType.fname}</td>
					<td>${articleType.fkeywords}</td>
					<td>${articleType.fdescription}</td>
					<td>${articleType.flastCreateDate}</td>
					<td>${articleType.flastModifyDate}</td>
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
