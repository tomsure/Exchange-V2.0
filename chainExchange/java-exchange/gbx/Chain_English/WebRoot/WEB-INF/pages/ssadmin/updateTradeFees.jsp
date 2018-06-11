<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	Modify Trading Fee Details
</h2>

<div class="pageContent">
	<form method="post" action="ssadmin/updateTradeFee.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
		<input type="hidden" name="fid" value="${ftradeMapping.fid}"/>
			<c:forEach items="${allFees}" var="fee">
				<dl>
					<dt>
						level[ <b><font color="red"><fmt:formatNumber
									value="${fee.flevel}" pattern="#0.######" /> </font> </b> ]Buy fee：
					</dt>
					<dd>
						<input type="text" name="fbuyfee${fee.fid}"
							value="<fmt:formatNumber
								value="${fee.fbuyfee}" pattern="#0.######" />"
							size="70" class="required number" />
					</dd>
				</dl>
				<dl>
					<dt>
						level[ <b><font color="red"><fmt:formatNumber
									value="${fee.flevel}" pattern="#0.######" /> </font> </b> ]Sell fee：
					</dt>
					<dd>
						<input type="text" name="fee${fee.fid}"
							value="<fmt:formatNumber
								value="${fee.ffee}" pattern="#0.######" />"
							size="70" class="required number" />
					</dd>
				</dl>		
			</c:forEach>
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
