<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Details of adding a bank account</h2>


<div class="pageContent">
	
	<form method="post" action="ssadmin/saveSystemBank.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Bank Name</dt>
				<dd>
					<input type="text" name="systemBank.fbankName" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>Account Name</dt>
				<dd>
					<input type="text" name="systemBank.fownerName" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>Bank Address</dt>
				<dd>
					<input type="text" name="systemBank.fbankAddress" maxlength="70" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>Account Number</dt>
				<dd>
					<input type="text" name="systemBank.fbankNumber" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Save</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">Cancel</button></div></div></li>
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
