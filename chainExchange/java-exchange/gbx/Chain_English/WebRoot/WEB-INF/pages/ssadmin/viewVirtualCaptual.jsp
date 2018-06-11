<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Approve Cryptocurrency Withdrawal <font color="red"></font></h2>


<div class="pageContent">

	<form method="post" action="ssadmin/virtualCapitalOutAudit.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<!-- <dl>
				<dt>钱包密码：</dt>
				<dd>
					<input type="password" name="fpassword" maxlengpahx="50"  size="40"/>
				</dd>
			</dl> -->
			<dl>
				<dt>User Name</dt>
				<dd>
				    <input type="hidden" name="uid" value="${virtualCapitaloperation.fid}"/>
					<input type="text" name="fname" maxlength="30" size="40" 
					  value="${virtualCapitaloperation.fuser.floginName}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>Type</dt>
				<dd>
					<select type="combox" name="virtualcointype.fid" disabled="true">
					<option value="${virtualCapitaloperation.fvirtualcointype.fid}">${virtualCapitaloperation.fvirtualcointype.fname}</option>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>Amount</dt>
				<dd>
					<input type="text" name=famount maxlength="30"
						class="required number" size="40"  value="${virtualCapitaloperation.famount}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>Fee</dt>
				<dd>
					<input type="text" name="ffees" maxlength="30"
						class="required number" size="40"  value="${virtualCapitaloperation.ffees}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>Withdrawal</dt>
				<dd>
					<input type="text" name="withdraw_virtual_address" maxlength="30"
						class="required number" size="40" value="${virtualCapitaloperation.withdraw_virtual_address}" disabled="true"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">Approve</button>
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
