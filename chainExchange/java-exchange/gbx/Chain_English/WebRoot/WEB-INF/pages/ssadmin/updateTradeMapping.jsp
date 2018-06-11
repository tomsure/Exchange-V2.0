<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Modify Fiat Money Type Details</h2>


<div class="pageContent">

		<form method="post" action="ssadmin/updateTradeMapping.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dt>Fiat Money</dt>
				<dd>
					<input type="hidden" name="uid" value="${ftradeMapping.fid}" />
					<span>${ftradeMapping.fvirtualcointypeByFvirtualcointype1.fname }</span>
				</dd>
			</dl>
			<dl>
				<dt>Currency</dt>
				<dd>
					<span>${ftradeMapping.fvirtualcointypeByFvirtualcointype2.fname }</span>
				</dd>
			</dl>
			<dl>
				<dt>Unit Place Decimal Place</dt>
				<dd>
					<input type="text" name="fcount1"
						class="required number" size="30" value="<fmt:formatNumber value="${ftradeMapping.fcount1 }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Qty Decimal Place</dt>
				<dd>
					<input type="text" name="fcount2"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fcount2 }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Trading Hours</dt>
				<dd>
					<input type="text" name="ftradeTime"
						class="required" value="${ftradeMapping.ftradeTime }"/>
					<span>Enter 0 for not trading; 24 for 24-hour trading; 8:30-18:00 means that time span.</span>
				</dd>
			</dl>
			<dl>
				<dt>Open Price</dt>
				<dd>
					<input type="text" name="fprice"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fprice }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
            <dl>
				<dt>Min. Buying Qt</dt>
				<dd>
					<input type="text" name="fminBuyCount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminBuyCount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Min. Buying Unit Price</dt>
				<dd>
					<input type="text" name="fminBuyPrice"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminBuyPrice }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Min. Buying Amount</dt>
				<dd>
					<input type="text" name="fminBuyAmount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminBuyAmount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			 <dl>
				<dt>Min. Selling Qty</dt>
				<dd>
					<input type="text" name="fminSellCount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminSellCount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Min. Selling Unit Price</dt>
				<dd>
					<input type="text" name="fminSellPrice"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminSellPrice }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Min. Selling Amount</dt>
				<dd>
					<input type="text" name="fminSellAmount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminSellAmount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>Max. Pending Orders Times</dt>
				<dd>
					<input type="text" name="fmaxtimes"
						class="required number" size="30" value="${ftradeMapping.fmaxtimes }"/>
					<span>Unlimited if 0 is entered.</span>
				</dd>
			</dl>
			 <dl>
				<dt>Max. Buying Qty</dt>
				<dd>
					<input type="text" name="fmaxBuyCount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fmaxBuyCount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			 <dl>
				<dt>Max. Selling Qty</dt>
				<dd>
					<input type="text" name="fmaxSellCount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fmaxSellCount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl style="display: none">
				<dt>级别：</dt>
				<dd>
					<select type="combox" name="ftype">
					<c:forEach items="${type}" var="t">
					<c:if test="${t.key == ftradeMapping.ftype}">
				    	<option value="${t.key}" selected="true">${t.value}</option>
					</c:if>
					<c:if test="${t.key != ftradeMapping.ftype}">
				    	<option value="${t.key}">${t.value}</option>
					</c:if>
					</c:forEach>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>Trading Description</dt>
				<dd>
				<textarea rows="3" cols="80" name="ftradedesc">${ftradeMapping.ftradedesc}</textarea>
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
