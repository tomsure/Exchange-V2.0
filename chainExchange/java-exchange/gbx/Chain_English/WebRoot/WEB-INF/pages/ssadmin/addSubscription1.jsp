<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add ICO</h2>

<div class="pageContent">

	<form method="post" action="ssadmin/saveSubscription1.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>Title</dt>
				<dd>
					<input type="text" name="ftitle" maxlength="300"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>White paper link</dt>
				<dd>
					<input type="text" name="fbaipi" maxlength="300"
						   size="70" />
				</dd>
			</dl>
			<dl>
			<dt>Payment type</dt>
				<dd>
						<c:forEach items="${coins}" var="type">
							<input type="checkbox" name="fvi_ids" value="${type.fid}"/>${type.fShortName}
						</c:forEach>
				</dd>
			</dl>
			<dl>
				<dt>subscription ratio</dt>
				<dd>
					<input type="text" name="fprices" class="required" size="70"/><br/>【Multiple payment types in English / separate,for example:0.5/1】
				</dd>
			</dl>
			<dl>
				<dt>Type of ICO cryptocurrency</dt>
				<dd>
					<select type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<option value="${type.fid}">${type.fShortName}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dt>Unfreezing type</dt>
				<dd>
					<select
						type="combox" name=ffrozenType class="required">
						<c:forEach items="${frozenType}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Enable unfreezing or not</dt>
				<dd>
					<input type="checkbox" name="fisstart" />
					<span>Thawing at 0 points a day, thawing on the month one month</span>
				</dd>
			</dl>
			<dl>
				<dt>Unfreezing ratio</dt>
				<dd>
					<input type="text" name="frate" class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>Enable ICO or not</dt>
				<dd>
					<input type="checkbox" name="fisopen" />
				</dd>
			</dl>
			<dl>
				<dt>Total number of ICO</dt>
				<dd>
					<input type="text" name="ftotal" class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>Largest number of ICO per person</dt>
				<dd>
					<input type="text" name="fbuyCount" class="required digits" /><span>0:Infinite</span>
				</dd>
			</dl>
			<dl>
				<dt>Smallest number of ICO per person</dt>
				<dd>
					<input type="text" name="fminbuyCount" class="required digits" />
				</dd>
			</dl>
			<dl>
				<dt>Maximun entries of ICO per person</dt>
				<dd>
					<input type="text" name="fbuyTimes" class="required digits" /><span>0:Infinite</span>
				</dd>
			</dl>
			<dl>
				<dt>Start time</dt>
				<dd>
					<input type="text" name="fbeginTime" class="required date"
						readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" size="40" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>End time</dt>
				<dd>
					<input type="text" name="fendTime" class="required date"
						readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" size="40" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>introduce</dt>
				<dd>
					<textarea class="editor" name="fcontent" rows="20" cols="105"
						tools="simple" upImgUrl="ssadmin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">Save</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">Cancel</button>
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
