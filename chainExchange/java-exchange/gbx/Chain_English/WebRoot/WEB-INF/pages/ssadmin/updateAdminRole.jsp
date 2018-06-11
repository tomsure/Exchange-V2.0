<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	Edit info. of Admin:<font color="red">${fadmin.fname}</font>
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateAdminRole.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Role</dt>
				<dd>
					<input type="hidden" name="fadmin.fid" value="${fadmin.fid}" /> <select
						type="combox" name="frole.fid">
						<c:forEach items="${roleMap}" var="role">
							<c:if test="${role.key == fadmin.frole.fid}">
								<option value="${role.key}" selected="true">${role.value}</option>
							</c:if>
							<c:if test="${role.key != fadmin.frole.fid}">
								<option value="${role.key}">${role.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">Modify</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">Cancel</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
</script>
