<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">更新代理商信息</h2>


<div class="pageContent">
	<form method="post" action="ssadmin/updateProxy.html" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
		    <input type="hidden" name="uid" value="${fproxy.fid}"/>
			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required" size="100" value="${fproxy.name }"/>
				</dd>
			</dl>
			<dl>
				<dt>保证金：</dt>
				<dd>
					<input type="text" name="amount" maxlength="20" class="required" size="100" value="${fproxy.amount }"/>
				</dd>
			</dl>
			<dl>
				<dt>QQ：</dt>
				<dd>
					<input type="text" name="qq" maxlength="20" class="required" size="100" value="${fproxy.qq }"/>
				</dd>
			</dl>
			<dl>
				<dt>姓名：</dt>
				<dd>
					<input type="text" name="realname" maxlength="20" class="required" size="100" value="${fproxy.realname }"/>
				</dd>
			</dl>
			<dl>
				<dt>账号：</dt>
				<dd>
					<input type="text" name="account" maxlength="20" class="required" size="100" value="${fproxy.account }"/>
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
