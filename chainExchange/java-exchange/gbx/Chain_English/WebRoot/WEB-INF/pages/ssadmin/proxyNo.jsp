<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	会员<font color="red">${fuser.fnickName}</font>
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/setProxyNo.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>省市县号：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${fuser.fid }"/>
					<input type="text" name="fProxyNumber" size="70" class="required" value="${fuser.fProxyNumber }"/>
				</dd>
			</dl>
			<dl>
				<dt>手续费奖励比例：</dt>
				<dd>
					<input type="text" name="fProxyTradeRate" size="70" class="required number" value="${fuser.fProxyTradeRate }"/>
				</dd>
			</dl>
			<dl>
				<dt>众筹奖励比例：</dt>
				<dd>
					<input type="text" name="fProxySubRate" size="70" class="required number" value="${fuser.fProxySubRate }"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
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
