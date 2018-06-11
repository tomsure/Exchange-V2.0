<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add Cryptocurrency Type Details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveVirtualCoinType.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Icon Link</dt>
				<dd>
					<input type="file" class="inputStyle" value="Choose File" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>Currency Name</dt>
				<dd>
					<input type="text" name="fname" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>Currency Name Abbr.</dt>
				<dd>
					<input type="text" name="fShortName"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>Symbol</dt>
				<dd>
					<input type="text" name="fSymbol" maxlength="1" class="required"
						size="40" />
				</dd>
			</dl>
			<dl>
				<dt>ACCESS_KEY：</dt>
				<dd>
					<input type="password" name="faccess_key"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>SECRT_KEY：</dt>
				<dd>
					<input type="password" name="fsecrt_key"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>IP Address</dt>
				<dd>
					<input type="text" name="fip" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>Port No.</dt>
				<dd>
					<input type="text" name="fport"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Valid Deposit</dt>
				<dd>
					<input type="checkbox" name="fisrecharge" checked='1' />
				</dd>
			</dl>
			<dl>
				<dt>Valid Withdraw</dt>
				<dd>
					<input type="checkbox" name="FIsWithDraw" checked='1' />
				</dd>
			</dl>
			<dl>
				<dt>Valid Auto-received Deposit </dt>
				<dd>
					<input type="checkbox" name="fisauto" checked='1' />
				</dd>
			</dl>
			<!-- <dl>
				<dt>是否开启转账：</dt>
				<dd>
					<input type="checkbox" name="fistransport" checked='1' />
				</dd>
			</dl> -->
			<dl>
				<dt>Total Qty.</dt>
				<dd>
					<input type="text" name="ftotalqty"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Valid Auto Withdrawal</dt>
				<dd>
				<input type="checkbox" name="fisautosend" />
				</dd>
			</dl>
			<dl>
				<dt>Wallet Password</dt>
				<dd>
					<input type="password" name="fpassword"
						class="" size="70"/>
				</dd>
			</dl>
						 <dl>
				<dt>Deposit Notes</dt>
				<dd>
				<textarea rows="3" cols="80" name="fregDesc"></textarea>
				</dd>
			</dl>
			 <dl>
				<dt>Withdrawal Notes</dt>
				<dd>
				<textarea rows="3" cols="80" name="fwidDesc"></textarea>
				</dd>
			</dl>
			<dl>
				<dt>Valid ETH</dt>
				<dd>
					<input type="checkbox" name="fisEth" />
				</dd>
			</dl>
			<dl>
			<dt>Valid ETH Token</dt>
			<dd>
				<input type="checkbox" name="fisToken" />
			</dd>
			</dl>
			<dl>
			<dt>Base Exchange Token</dt>
			<dd>
				<input type="checkbox" name="ftype" />
			</dd>
			</dl>
			<dl>
				<dt>Primary Address </dt>
				<dd>
					<input type="text" name="mainAddr"
						class="" size="70" />
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
