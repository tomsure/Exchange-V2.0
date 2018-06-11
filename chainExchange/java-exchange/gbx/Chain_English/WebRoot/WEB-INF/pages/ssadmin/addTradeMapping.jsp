<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add Fiat Money Type Details</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveTradeMapping.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">	
		<div class="pageFormContent nowrap" layoutH="97">
			<dt>Fiat Money</dt>
				<dd>
					<select
						type="combox" name="fvirtualcointype1" class="required">
						<c:forEach items="${fvirtualcointypes_fb}" var="type">
							<option value="${type.fid}">${type.fShortName}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Currency</dt>
				<dd>
					<select type="combox" name="fvirtualcointype2" class="required">
						<c:forEach items="${fvirtualcointypes}" var="type">
							<option value="${type.fid}">${type.fShortName}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Unit Place Decimal Place</dt>
				<dd>
					<input type="text" name="fcount1"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Qty Decimal Place</dt>
				<dd>
					<input type="text" name="fcount2"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Trading Hours</dt>
				<dd>
					<input type="text" name="ftradeTime"
						class="required"/>
						<span>Enter 0 for not trading; 24 for 24-hour trading; 8:30-18:00 means that time span.</span>
				</dd>
			</dl>
			<dl>
				<dt>Open Price</dt>
				<dd>
					<input type="text" name="fprice"
						class="required number" size="30" />
				</dd>
			</dl>
            <dl>
				<dt>Min. Buying Qty</dt>
				<dd>
					<input type="text" name="fminBuyCount"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Min. Buying Unit Price</dt>
				<dd>
					<input type="text" name="fminBuyPrice"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Min. Buying Amount</dt>
				<dd>
					<input type="text" name="fminBuyAmount"
						class="required number" size="30" />
				</dd>
			</dl>
			 <dl>
				<dt>Min. Selling Qty</dt>
				<dd>
					<input type="text" name="fminSellCount"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Min. Selling Unit Price</dt>
				<dd>
					<input type="text" name="fminSellPrice"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Min. Selling Amount</dt>
				<dd>
					<input type="text" name="fminSellAmount"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>Max. Pending Orders Times</dt>
				<dd>
					<input type="text" name="fmaxtimes"
						class="required number" size="30" />
					<span>Unlimited if 0 is entered.</span>
				</dd>
			</dl>
			 <dl>
				<dt>Max. Buying Qty</dt>
				<dd>
					<input type="text" name="fmaxBuyCount"
						class="required number" size="30"/>
				</dd>
			</dl>
			 <dl>
				<dt>Max. Selling Qty</dt>
				<dd>
					<input type="text" name="fmaxSellCount"
						class="required number" size="30"/>
				</dd>
			</dl>
		<!-- 	<dl>
				<dt>是否限卖：</dt>
				<dd>
					<input type="checkbox" name="fislimit" checked='1' />
				</dd>
			</dl>
			<dl>
				<dt>限卖比例：</dt>
				<dd>
					<input type="text" name="ftraderate"
						class="required number" size="30" />
				</dd>
			</dl> -->
			<dl style="display: none">
				<dt>级别：</dt>
				<dd>
					<select type="combox" name="ftype">
					<c:forEach items="${type}" var="t">
						<option value="${t.key}">${t.value}</option>
					</c:forEach>
		            </select>
				</dd>
			</dl>
		   <dl>
				<dt>Trading Description</dt>
				<dd>
				<textarea rows="3" cols="80" name="ftradedesc"></textarea>
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
