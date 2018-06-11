<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加手续费分红计划信息</h2>

<div class="pageContent">

	<form method="post" action="ssadmin/updateHandingSharePlan.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>主题：</dt>
				<dd>
					<input type="hidden" name="uid" value="${shareplan.fid}" /> <input
						type="text" name="ftitle" maxlength="70" class="required"
						size="70" value="${shareplan.ftitle }" />
				</dd>
			</dl>
			<dl>
				<dt>持币名称：</dt>
				<dd>
					<select type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == shareplan.fvirtualcointype.fid}">
								<option value="${type.fid}" selected="true">${type.fname}</option>
							</c:if>
							<c:if test="${type.fid != shareplan.fvirtualcointype.fid}">
								<option value="${type.fid}">${type.fname}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>分红币名称：</dt>
				<dd>
					<select type="combox" name="vid1" class="required">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == shareplan.fsendcointype.fid}">
								<option value="${type.fid}" selected="true">${type.fname}</option>
							</c:if>
							<c:if test="${type.fid != shareplan.fsendcointype.fid}">
								<option value="${type.fid}">${type.fname}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>分红总币数量：</dt>
				<dd>
					<input type="text" name="ftotalCoinQty" class="required number"
						value="${shareplan.ftotalCoinQty }" />
				</dd>
			</dl>
			<dl>
				<dt>有效开始日期：</dt>
				<dd>
					<input type="text" name="startDate" class="required date" dateFmt="yyyy-MM-dd HH:mm:ss"
						readonly="true"  value="<fmt:formatDate value="${shareplan.fstartDate }"
												pattern="yyyy-MM-dd HH:mm:ss" />"/> <a class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>有效结束日期：</dt>
				<dd>
					<input type="text" name="endDate" class="required date" dateFmt="yyyy-MM-dd HH:mm:ss"
						readonly="true"  value="<fmt:formatDate value="${shareplan.fendDate }"
												pattern="yyyy-MM-dd HH:mm:ss" />"/> <a class="inputDateButton" href="javascript:;">选择</a>
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
