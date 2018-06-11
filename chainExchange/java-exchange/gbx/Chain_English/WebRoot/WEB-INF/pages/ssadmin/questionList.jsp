<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/questionList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/questionList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Submitter<input type="text" name="keywords" value="${keywords}"
						size="60" />
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
			<shiro:hasPermission name="ssadmin/viewQuestion.html">
				<li><a class="edit"
					href="ssadmin/goQuestionJSP.html?url=ssadmin/viewQuestion1&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateLink"><span>View</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">No.</th>
				<th width="60">Submitter UID</th>
				<th width="60">Submitter</th>
				<th width="60">Mobile</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus"}'> class="${param.orderDirection}"  </c:if>>Status</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype"}'> class="${param.orderDirection}"  </c:if>>Question Type</th>
				<th width="100">Question</th>
				<th width="100">Response</th>
				<th width="60">Response Admin</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime"}'> class="${param.orderDirection}"  </c:if>>Creation Time
				</th>
				<th width="60" orderField="fSolveTime"
					<c:if test='${param.orderField == "fSolveTime"}'> class="${param.orderDirection}"  </c:if>>Response Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questionList}" var="question" varStatus="num">
				<tr target="sid_user" rel="${question.fid}">
					<td>${num.index +1}</td>
					<td>${question.fuser.fid}</td>
					<td>${question.fuser.frealName}</td>
					<td>${question.fuser.ftelephone}</td>
					<td>${question.fstatus_s}</td>
					<td>${question.ftype_s}</td>
					<td>${question.fdesc}</td>
					<td>${question.fanswer}</td>
					<td>${question.fadmin.fname}</td>
					<td>${question.fcreateTime}</td>
					<td>${question.fSolveTime}</td>
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
