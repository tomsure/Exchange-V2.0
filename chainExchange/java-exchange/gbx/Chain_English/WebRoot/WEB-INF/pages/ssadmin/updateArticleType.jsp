<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Modify news type details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateArticleType.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Type Name</dt>
				<dd>
					<input type="hidden" name="fid" value="${farticleType.fid}"
						maxlength="100" /> <input type="text" name="fname" maxlength="30"
						class="required" size="70" value="${farticleType.fname}" />
				</dd>
			</dl>
			<dl>
				<dt>Keywords</dt>
				<dd>
					<input type="text" name="fkeywords" maxlength="100" size="70"  value="${farticleType.fkeywords}"/>
				</dd>
			</dl>
			<dl>
				<dt>Description</dt>
				<dd>
					<input type="text" name="fdescription" maxlength="200" size="70"  value="${farticleType.fdescription}"/>
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
