<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Modify Trading Fee for USD Withdrawal</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateWithdrawFees.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Member Level</dt>
				<dd>
					<input type="hidden" name="fid" value="${withdrawfees.fid}"/> 
					<input type="text" name="flevel" size="70" value="${withdrawfees.flevel}" readonly="true"/>
				</dd>
			</dl>
			<dl>
				<dt>USD Withdrawal Fee</dt>
				<dd>
					<input type="text" name="ffee" maxlength="100" size="40" class="required number" value="${withdrawfees.ffee}"/>
				</dd>
			</dl>
			<dl>
				<dt>Amount Limit</dt>
				<dd>
					<input type="text" name="famt" maxlength="100" size="40" class="required number" value="${withdrawfees.famt}"/>
				</dd>
			</dl>
			<dl>
				<dt>Trading Fee</dt>
				<dd>
					<input type="text" name="flastFee" maxlength="100" size="40" class="required number" value="${withdrawfees.flastFee}"/>
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
