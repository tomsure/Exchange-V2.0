<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Summarize<font color="red">${virtualCoinType.fShortName}</font> to Primary Address：${virtualCoinType.mainAddr }</h2>


<div class="pageContent">
	
	<form method="post" action="ssadmin/etcMainAddr.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Wallet Password</dt>
				<dd>
				    <input type="hidden" name="uid" value="${virtualCoinType.fid}"/>
				    <input type="password" name="password" size="50" class="required"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Enable</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">Cancel</button></div></div></li>
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
