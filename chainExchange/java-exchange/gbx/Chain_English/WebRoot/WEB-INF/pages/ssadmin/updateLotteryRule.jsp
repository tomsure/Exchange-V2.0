<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改奖励规则信息</h2>

<div class="pageContent">

	<form method="post" action="ssadmin/updateLotteryRule.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>每天抽奖次数：</dt>
				<dd>
				<input type="hidden" name="fid" value="${lotteryrule.fid }"/>
					<input type="text" name="ftimes" class="required digits" value="${lotteryrule.ftimes }"/>
				</dd>
			</dl>
			<dl>
				<dt>上线奖励比例：</dt>
				<dd>
					<input type="text" name="frate" class="required number" value="${lotteryrule.frate }"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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
