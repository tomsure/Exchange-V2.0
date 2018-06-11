<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Modify Cryptocurrency Type Details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateVirtualCoinType.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
		    <dl>
				<dt>Icon Link</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>Currency Name</dt>
				<dd>
					<input type="hidden" name="fid" value="${virtualCoinType.fid}" />
					<input type="text" name="fname" class="required"
						size="70" value="${virtualCoinType.fname}" />
				</dd>
			</dl>
			<dl>
				<dt>Currency Name Abbr.</dt>
				<dd>
					<input type="text" name="fShortName"
						class="required" size="70" value="${virtualCoinType.fShortName}" />
				</dd>
			</dl>
			<dl>
				<dt>Symbol</dt>
				<dd>
					<input type="text" name="fSymbol" class="required"
						size="40" value="${virtualCoinType.fSymbol}" />
				</dd>
			</dl>
			<dl>
				<dt>ACCESS_KEY：</dt>
				<dd>
					<input type="password" name="faccess_key"
						class="required" size="70" value="${virtualCoinType.faccess_key}" />
				</dd>
			</dl>
			<dl>
				<dt>SECRT_KEY：</dt>
				<dd>
					<input type="password" name="fsecrt_key"
						class="required" size="70" value="${virtualCoinType.fsecrt_key}" />
				</dd>
			</dl>
			<dl>
				<dt>IP Address</dt>
				<dd>
					<input type="text" name="fip" class="required"
						size="70" value="${virtualCoinType.fip}" />
				</dd>
			</dl>
			<dl>
				<dt>Port No.</dt>
				<dd>
					<input type="text" name="fport"
						class="required number" size="30" value="${virtualCoinType.fport}" />
				</dd>
			</dl>
			<dl>
				<dt>Valid Deposit</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.fisrecharge}">
							<input type="checkbox" name="fisrecharge" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisrecharge" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Valid Withdraw</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.FIsWithDraw}">
							<input type="checkbox" name="FIsWithDraw" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="FIsWithDraw" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Valid Auto-received Deposit</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.fisauto}">
							<input type="checkbox" name="fisauto" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisauto" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			
			<%-- <dl>
				<dt>是否开启转账：</dt>
				<dd>
				    <c:choose>
						<c:when test="${virtualCoinType.fistransport}">
							<input type="checkbox" name="fistransport" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fistransport" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl> --%>
			<dl>
				<dt>Total Qty.</dt>
				<dd>
					<input type="text" name="ftotalqty"
						class="required number" size="30" value="<fmt:formatNumber value="${virtualCoinType.ftotalqty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Valid Auto Withdrawal</dt>
				<dd>
				<c:choose>
						<c:when test="${virtualCoinType.fisautosend}">
							<input type="checkbox" name="fisautosend" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisautosend" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Wallet Password</dt>
				<dd>
					<input type="password" name="fpassword"
						class="" size="70" value="${virtualCoinType.fpassword }"/>
				</dd>
			</dl>
			 <dl>
				<dt>Deposit Notes</dt>
				<dd>
				<textarea rows="3" cols="80" name="fregDesc">${virtualCoinType.fregDesc }</textarea>
				</dd>
			</dl>
			 <dl>
				<dt>Withdrawal Notes</dt>
				<dd>
				<textarea rows="3" cols="80" name="fwidDesc">${virtualCoinType.fwidDesc }</textarea>
				</dd>
			</dl>
			<dl>
				<dt>Valid ETH</dt>
				<dd>
				<c:choose>
						<c:when test="${virtualCoinType.fisEth}">
							<input type="checkbox" name="fisEth" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisEth" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Valid ETH Token</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.fisToken}">
							<input type="checkbox" name="fisToken" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisToken" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
			<dt>Base Exchange Token</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.ftype==1}">
							<input type="checkbox" name="ftype" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="ftype" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Primary Address</dt>
				<dd>
					<input type="text" name="mainAddr"
						class="" size="70" value="${virtualCoinType.mainAddr }"/>
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
