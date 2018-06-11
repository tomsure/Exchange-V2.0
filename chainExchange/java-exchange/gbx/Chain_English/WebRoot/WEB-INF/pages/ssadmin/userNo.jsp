<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	会员<font color="red">${fuser.fnickName}</font>
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/setUserNo.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>服务中心号：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${fuser.fid }"/>
					<input type="text" name="fuserNo" size="70" class="required" value="${fuser.fuserNo }"/>
				</dd>
			</dl>
			<dl>
				<dt>服务中心类型：</dt>
				<dd>
				   <select type="combox" name="fuserType">
					<c:forEach items="${userType}" var="t">
					<c:if test="${t.key == fuser.fuserType}">
				    	<option value="${t.key}" selected="true">${t.value}</option>
					</c:if>
					<c:if test="${t.key != fuser.fuserType}">
				    	<option value="${t.key}">${t.value}</option>
					</c:if>
					</c:forEach>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>手续费奖励比例：</dt>
				<dd>
					<input type="text" name="fServiceTradeRate" size="50" class="required" value="${fuser.fServiceTradeRate }"/>
					<span>正常奖励比例#平级奖励比例</span>
				</dd>
			</dl>
			<%-- <dl>
				<dt>众筹奖励比例：</dt>
				<dd>
					<input type="text" name="fServiceSubRate" size="50" class="required" value="${fuser.fServiceSubRate }"/>
					<span>正常奖励比例#平级奖励比例</span>
				</dd>
			</dl> --%>
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
