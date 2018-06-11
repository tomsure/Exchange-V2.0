<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/ssadmin/coinVoteList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ssadmin/coinVoteList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>keywords<input type="text" name="keywords" value="${keywords}"
						size="60" />[name,short name,description]</td>
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
					href="ssadmin/goVirtualCoinVoteJSP.html?url=ssadmin/coinvote/addCoinVote"
					height="350" width="800" target="dialog" rel="addCoinVote"><span>Add</span>
				</a>
				</li>
				<li><a class="edit"
					href="ssadmin/goVirtualCoinVoteJSP.html?url=ssadmin/coinvote/editCoinVote&uid={sid_user}"
					height="350" width="800" target="dialog" rel="editCoinVote"><span>Edit</span>
				</a>
				</li>
		
				<li><a class="add"
					href="ssadmin/updateCoinVoteStatus1.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to Enable?"><span>Enable</span> </a>
				</li>
				<li><a class="delete"
					href="ssadmin/updateCoinVoteStatus2.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to Disable?"><span>Disable</span> </a>
				</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">ID</th>
				<th width="20">Chinese Name</th>
				<th width="60">Abbr.</th>
				<th width="60">Slogan</th>
				<th width="60">Affirmative Votes</th>
				<th width="60">Against Votes</th>
				<th width="60">Statis</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${coinVoteList}" var="v"
				varStatus="num">
				<tr target="sid_user" rel="${v.fid}">
					<td>${num.index +1}</td>
					<td>${v.fcnName}</td>
					<td>${v.fshortName}</td>
					<td>${v.fenName}</td>
					<td>${v.fyes}</td>
					<td>${v.fno}</td>
					<td>${v.fstatus_s}</td>
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
