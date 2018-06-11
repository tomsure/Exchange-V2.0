<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add Cryptocurrency Deposit details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveVirtualOperationLog.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Member</dt>
				<dd>
					<input type="hidden" name="userLookup.id" value="${userLookup.id}" />
					<input type="text" class="required" name="userLookup.floginName"
						value="" suggestFields="id,floginName"
						suggestUrl="ssadmin/userLookup.html" lookupGroup="userLookup"
						readonly="readonly" /> <a class="btnLook"
						href="ssadmin/userLookup.html" lookupGroup="userLookup">Search</a>
				</dd>
			</dl>
			<dl>
				<dt>Cryptocurrency Name</dt>
				<dd>
					<select type="combox" name="vid">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == vid}">
								<option value="${type.fid}" selected="true">${type.fShortName}</option>
							</c:if>
							<c:if test="${type.fid != vid}">
								<option value="${type.fid}">${type.fShortName}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Total</dt>
				<dd>
					<input type="text" name="fqty" maxlength="50" size="40"
						class="number required" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">Save</button>
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
