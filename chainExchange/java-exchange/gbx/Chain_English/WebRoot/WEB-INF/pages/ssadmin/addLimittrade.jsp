<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add Price Limit Details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveLimittrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Transaction Type</dt>
				<dd>
					<select type="combox" name="vid">
						<c:forEach items="${trademappings}" var="v">
							<option value="${v.fid}">${v.fvirtualcointypeByFvirtualcointype1.fShortName}-${v.fvirtualcointypeByFvirtualcointype2.fShortName }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Lowest Price</dt>
				<dd>
					<input type="text" name="fdownprice" maxlength="20"
						class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>Highest Price</dt>
				<dd>
					<input type="text" name="fupprice" maxlength="20"
						class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>Up/Down Ratio</dt>
				<dd>
					<input type="text" name="fpercent" maxlength="20"
						class="required number" />
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
