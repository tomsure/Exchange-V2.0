<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">增加代理商额度</h2>


<div class="pageContent">
	<form method="post" action="ssadmin/updateProxyAmt.html" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
		    <input type="hidden" name="uid" value="${fproxy.fid}"/>
		    <dl>
				<dt>剩余额度：</dt>
				<dd>
					<input type="text" name="flastAmt" maxlength="20" class="required" size="100" value="${fproxy.flastAmt }" readonly="readonly"/>
				</dd>
			</dl>
			<dl>
				<dt>增加额度：</dt>
				<dd>
					<input type="text" name="faddAmt" class="number required" size="100" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
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
