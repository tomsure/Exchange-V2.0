<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">发放冻结</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/sendVirtualOperationLog.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>剩余未解冻数量：</dt>
				<dd>
				    <input type="hidden" name="uid" value="${virtualoperationlog.fid}"/>
					<span>${virtualoperationlog.ffrozenQty-virtualoperationlog.fqty }</span>
				</dd>
			</dl>
			<dl>
				<dt>提现数量：</dt>
				<dd>
					<input type="text" name=sendQty maxlength="30"
						class="required number" size="40"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">审核</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
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
