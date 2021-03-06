<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Update link details</h2>


<div class="pageContent">


		<form method="post" action="ssadmin/updateLink.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">	
		<div class="pageFormContent nowrap" layoutH="97">
		<dl>
				<dt>Icon link</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>Name</dt>
				<dd>
					<input type="hidden" name="fid" value="${friendlink.fid}" /> <input
						type="text" name="fname" maxlength="20" class="required"
						size="70" value="${friendlink.fname}" />
				</dd>
			</dl>
			<dl>
				<dt>Type</dt>
				<dd>
					<select type="combox" name="ftype" disabled="true">
						<c:forEach items="${linkTypeMap}" var="link">
							<c:if test="${link.key == friendlink.ftype}">
								<option value="${link.key}" selected="true">${link.value}</option>
							</c:if>
							<c:if test="${link.key != friendlink.ftype}">
								<option value="${link.key}">${link.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Link</dt>
				<dd>
					<input type="text" name="furl" maxlength="100"
						class="required" size="70" value="${friendlink.furl}" />
				</dd>
			</dl>
			<dl>
				<dt>Description</dt>
				<dd>
					<input type="text" name="fdescription" maxlength="120"
						size="70" value="${friendlink.fdescription}" />
				</dd>
			</dl>

			<dl>
				<dt>Sequence</dt>
				<dd>
					<input type="text" name="forder" maxlength="20"
						class="required digits" value="${friendlink.forder}" />
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
