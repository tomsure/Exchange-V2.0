<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Modify Help Category</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateAbout.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Title (Chinese)</dt>
				<dd>
					<input type="hidden" name="fid" value="${fabout.fid }" /> <input
						type="text" name="ftitle" maxlength="50" class="required"
						size="70" value="${fabout.ftitle }" />
				</dd>
			</dl>
			<dl>
				<dt>Title</dt>
				<dd>
					<input
						type="text" name="ftitleEn" maxlength="50" class="required"
						size="70" value="${fabout.ftitleEn }" />
				</dd>
			</dl>
			<dl>
				<dt>Type</dt>
				<dd>
					<select type="combox" name="ftype">
						<c:forEach items="${type}" var="t">
							<c:if test="${t.key == fabout.ftype}">
								<option value="${t.key}" selected="true">${t.value}</option>
							</c:if>
							<c:if test="${t.key != fabout.ftype}">
								<option value="${t.key}">${t.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Contents(Chinese)</dt>
				<dd>
					<textarea class="editor {width:'780px'}" name="fcontent" rows="20" cols="100"  upImgUrl="ssadmin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
						${fabout.fcontent }
				</textarea>
				</dd>
			</dl>
			<dl>
				<dt>Contents</dt>
				<dd>
					<textarea class="editor {width:'780px'}" name="fcontentEn" rows="20" cols="100"  upImgUrl="ssadmin/upload.html"
							  upImgExt="jpg,jpeg,gif,png">
						${fabout.fcontentEn }
					</textarea>
				</dd>
			</dl>
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
