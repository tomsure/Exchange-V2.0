<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add Automated Trading Details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveAutotrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Cryptocurrency</dt>
				<dd>
					<select type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<option value="${type.fid}">${type.fvirtualcointypeByFvirtualcointype2.fShortName}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Type</dt>
				<dd>
					<select type="combox" name="ftype">
						<c:forEach items="${map}" var="m">
							<option value="${m.key}">${m.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>UID</dt>
				<dd>
					<input type="text" name="fuserid" maxlength="20"
						class="required digits" />
				</dd>
			</dl>
			<dl>
				<dt>Min Trading Qty</dt>
				<dd>
					<input type="text" name="fminqty" maxlength="20"
						class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>Max Trading Qty</dt>
				<dd>
					<input type="text" name="fmaxqty" maxlength="20"
						class="required number" />
				</dd>
			</dl>
			
			<dl>
				<dt>Price Sync Type</dt>
				<dd>
					<select type="combox" name="fsynType">
						<c:forEach items="${typemap}" var="m">
							<option value="${m.key}">${m.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt>Min Floating Price</dt>
				<dd>
					<input type="text" name="fminprice" maxlength="20"
						class="required number" /> <span>(0 for sync with a third party)</span>
				</dd>
			</dl>
			<dl>
				<dt>Max Floating Price</dt>
				<dd>
					<input type="text" name="fmaxprice" maxlength="20"
						class="required number" /> <span>(0 for sync with a third party)</span>
				</dd>
			</dl>
			<dl>
				<dt>Frequency (Minute)</dt>
				<dd>
					<input type="text" name="ftimes" maxlength="20"
						class="required digits" />
				</dd>
			</dl>
			<dl>
				<dt>Pre-Start Pause(s)</dt>
				<dd>
					<input type="text" name="fstoptimes" maxlength="20"
						class="required digits" />
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
