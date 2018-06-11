<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">新增定存类型信息</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveBalancetype.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>类型名称：</dt>
				<dd>
					<input type="text" name="fname" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>定存周期：</dt>
				<dd>
					<input type="text" name="fday" class="required digits" size="70"
						value="${fbalancetype.fday }" /><span>天</span>
				</dd>
			</dl>
			<dl>
				<dt>定存利率：</dt>
				<dd>
					<input type="text" name="frate" class="required number" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>虚拟币名称：</dt>
				<dd>
					<select type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<option value="${type.fid}">${type.fname}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<%-- <dl>
				<dt>类型：</dt>
				<dd>
					<select type="combox" name="frecType">
					<c:forEach items="${typeMap}" var="t">
						<option value="${t.key}">${t.value}</option>
					</c:forEach>
		            </select>
				</dd>
			</dl> --%>
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
