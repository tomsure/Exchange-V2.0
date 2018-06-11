<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/userList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="uid" value="${uid}" /><input
		type="hidden" name="startDate" value="${startDate}" /> <input
		type="hidden" name="troUid" value="${troUid}" /> <input
		type="hidden" name="troNo" value="${troNo}" /> <input type="hidden"
		name="ftype" value="${ftype}" /> <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/userList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Member Info<input type="text" name="keywords"
						value="${keywords}" size="40" />
					</td>
					<td>Referee UID<input type="text" name="troUid" value="${troUid}"
						size="10" />
					</td>
					<td>Member Status <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
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
			<shiro:hasPermission name="ssadmin/userForbbin1.html">
				<li><a class="delete"
					href="ssadmin/userForbbin.html?uid={sid_user}&status=1&rel=listUser"
					target="ajaxTodo" title="Confirm: Are you sure you want to disable?"><span>Disable</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/userForbbin2.html">
				<li><a class="edit"
					href="ssadmin/userForbbin.html?uid={sid_user}&status=2&rel=listUser"
					target="ajaxTodo" title="Confirm: Are you sure you want to enable?"><span>Enable</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/userForbbin3.html">
				<li><a class="edit"
					href="ssadmin/userForbbin.html?uid={sid_user}&status=3&rel=listUser"
					target="ajaxTodo" title="Confirm: Are you sure you want to reset the login parssword?"><span>Reset Login Password</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/userForbbin4.html">
				<li><a class="edit"
					href="ssadmin/userForbbin.html?uid={sid_user}&status=4&rel=listUser"
					target="ajaxTodo" title="Confirm: Are you sure you want to reset the transaction parssword?"><span>Reset Transactoin Password</span> </a>
				</li>
		   </shiro:hasPermission>
		   <shiro:hasPermission name="ssadmin/userForbbin5.html">
				<li><a class="edit"
					href="ssadmin/cancelGoogleCode.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to reset the Google Verification code?"><span>Reset Google Verification Code</span>
				</a>
				</li>
		   </shiro:hasPermission>
		   <shiro:hasPermission name="ssadmin/userForbbin6.html">
				<li><a class="edit"
					href="ssadmin/cancelTel.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to reset the mobile number?"><span>Reset Mobile Number</span>
				</a>
				</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="ssadmin/userForbbin7.html">
			<li><a class="edit"
					href="ssadmin/goUserJSP.html?url=ssadmin/updateUserGrade&uid={sid_user}"
					height="250" width="700" target="dialog" rel="updateUserGrade"><span>Change Level</span>
				</a></li>
            <li><a class="edit"
					href="ssadmin/setTiger.html?uid={sid_user}"
					target="ajaxTodo" title="Confirm: Are you sure you want to set the trader?"><span>Set Trader</span>
				</a>
				</li>
			<li><a class="edit"
				href="ssadmin/goUserJSP.html?uid={sid_user}&url=ssadmin/updateIntroPerson"
				height="240" width="800" target="dialog" rel="updateIntroPerson"><span>Modify Referee</span>
			</a></li>		
	  </shiro:hasPermission>			
				<li class="line">line</li>
	<shiro:hasPermission name="ssadmin/userExport.html">
				<li><a class="icon" href="ssadmin/userExport.html"
					target="dwzExport" targetType="navTab" title="Confirm: Are you sure you want to export the records?"><span>Export</span>
				</a></li>
	</shiro:hasPermission>		
		</ul>
		<ul></ul>
	</div>
	<table class="table" width="150%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>Member UID</th>
				<th width="60">Registration Type</th>
                <th width="40" orderField="fIntroUser_id.fid"
					<c:if test='${param.orderField == "fIntroUser_id.fid" }'> class="${param.orderDirection}"  </c:if>>Referee</th>
				<th width="60" orderField="floginName"
					<c:if test='${param.orderField == "floginName" }'> class="${param.orderDirection}"  </c:if>>Username</th>
				<th width="60">Nickname</th>
				<th width="60">Real Name</th>
				<th width="60">Email</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>Member Status</th>
				<th width="60" orderField="fscore.flevel"
					<c:if test='${param.orderField == "fscore.flevel" }'> class="${param.orderDirection}"  </c:if>>Member Level</th>
				<th width="60">ID Type</th>
				<th width="60">ID Number</th>
				<th width="60">Mobile Number</th>
				<th width="60">Photocopy verification</th>
				<th width="60">Video verification</th>
				<th width="60">Trader or Not</th>
				<th width="60" orderField="fregisterTime"
					<c:if test='${param.orderField == "fregisterTime" }'> class="${param.orderDirection}"  </c:if>>Registration Time</th>
			    <th width="60">Registration IP</th>
			    <th width="60" orderField="flastLoginTime"
					<c:if test='${param.orderField == "flastLoginTime" }'> class="${param.orderDirection}"  </c:if>>Last Login Time</th>
			    <th width="60">Last Login IP</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="num">
				<tr target="sid_user" rel="${user.fid}">
					<td>${user.fid}</td>
					<td>${user.fregtype_s}</td>
					<td>${user.fIntroUser_id.fid}</td>
					<td>${user.floginName}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.femail}</td>
					<td>${user.fstatus_s}</td>
					<td>${user.fscore.flevel}</td>
					<td>${user.fidentityType_s}</td>
					<td>${user.fidentityNo}</td>
					<td>${user.ftelephone}</td>
					<td>${user.fhasImgValidate}</td>
					<td>${user.fhasVideoValidate}</td>
					<td>${user.fistiger}</td>
					<td>${user.fregisterTime}</td>
					<td>${user.fregIp}</td>
					<td>${user.flastLoginTime}</td>
					<td>${user.flastLoginIp}</td>
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
