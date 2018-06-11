<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Update System parameter details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateSystemArgs.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>KEY</dt>
				<dd>
					<input type="hidden" name="fid" value="${systemargs.fid}" /> <input
						type="text" name="key" maxlength="50" class="required" size="70"
						value="${systemargs.fkey}" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt>Img Link</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>Parameter</dt>
				<dd>
					<textarea name="value" rows="10" cols="70">${systemargs.fvalue}</textarea>
				</dd>
			</dl>
			<dl>
				<dt>Remarks</dt>
				<dd>
					<textarea name="desc" cols="70" rows="1">${systemargs.fdescription}</textarea>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">Save</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">Cancel</button>
						</div>
					</div></li>
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
