<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Modify ICO</h2>

<div class="pageContent">

	<form method="post" action="ssadmin/updateSubscription1.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
		     <dl>
				<dt>Title</dt>
				<dd>
					<input type="text" name="ftitle"
						class="required" size="70" value="${subscription.ftitle}"/>
				</dd>
			</dl>
			<dl>
				<dt>White paper link</dt>
				<dd>
					<input type="text" name="fbaipi" maxlength="300" value="${subscription.fbaipi}"
						   size="70" />
				</dd>
			</dl>
			<dl>
				<dt>Payment type</dt>
				<dd>
					<c:set value=",${subscription.fcost_vi_ids}," var="fcost_vi_idsStr"></c:set>
					<c:forEach items="${coins}" var="type">
						<c:set value=",${type.fid}," var="fidStr"></c:set>
						<input type="checkbox" name="fvi_ids" <c:if test="${fn:indexOf(fcost_vi_idsStr,fidStr) > -1}">checked="checked"</c:if> value="${type.fid}"/>${type.fShortName}
					</c:forEach>
				</dd>
			</dl>
			<dl>
				<dt>subscription ratio</dt>
				<dd>
					<input type="text" name="fprices" value="${subscription.fprices}" class="required" size="70"/><br/>【Multiple payment types in English / separate,for example:0.5/1】
				</dd>
			</dl>
			<dl>
				<dt>Type of ICO cryptocurrency</dt>
				<dd>
					<input type="hidden" name="fid" value="${subscription.fid }" /> <select
						type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == subscription.fvirtualcointype.fid}">
								<option value="${type.fid}" selected="true">${type.fShortName}</option>
							</c:if>
							<c:if test="${type.fid != subscription.fvirtualcointype.fid}">
								<option value="${type.fid}">${type.fShortName}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dt>Unfreezing type</dt>
				<dd>
					<select
						type="combox" name="ffrozenType" class="required">
						<c:forEach items="${frozenType}" var="type">
							<c:if test="${type.key == subscription.ffrozenType}">
								<option value="${type.key}" selected="true">${type.value}</option>
							</c:if>
							<c:if test="${type.key != subscription.ffrozenType}">
								<option value="${type.key}">${type.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>Enable unfreezing or not</dt>
				<dd>
					<c:choose>
						<c:when test="${subscription.fisstart}">
							<input type="checkbox" name="fisstart" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisstart" />
						</c:otherwise>
					</c:choose>
					<span>Thawing at 0 points a day, thawing on the month one month</span>
				</dd>
			</dl>
			<dl>
				<dt>Unfreezing ratio</dt>
				<dd>
					<input type="text" name="frate" class="required number" value="${subscription.frate }"/>
				</dd>
			</dl>
			<dl>
				<dt>Enable ICO or not</dt>
				<dd>
					<c:choose>
						<c:when test="${subscription.fisopen}">
							<input type="checkbox" name="fisopen" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisopen" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Total number of ICO</dt>
				<dd>
					<input type="text" name="ftotal" class="required number"
						value="<fmt:formatNumber value="${subscription.ftotal}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/>" />
				</dd>
			</dl>
			
			<dl>
				<dt>Largest number of ICO per person</dt>
				<dd>
					<input type="text" name="fbuyCount" class="required digits"
						value="${subscription.fbuyCount}" /><span>0:Infinite</span>
				</dd>
			</dl>
			<dl>
				<dt>Smallest number of ICO per person</dt>
				<dd>
					<input type="text" name="fminbuyCount" class="required digits"
						value="${subscription.fminbuyCount}" />
				</dd>
			</dl>
			<dl>
				<dt>Maximun entries of ICO per person</dt>
				<dd>
					<input type="text" name="fbuyTimes" class="required digits"
						value="${subscription.fbuyTimes}" /><span>0:Infinite</span>
				</dd>
			</dl>
			<dl>
				<dt>Start time</dt>
				<dd>
					<input type="text" name="fbeginTime" class="required date"
						readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" size="40"
						value="${s}" /> <a class="inputDateButton"
						href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>End time</dt>
				<dd>
					<input type="text" name="fendTime" class="required date"
						readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" size="40"
						value="${e}" /> <a class="inputDateButton"
						href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>introduce</dt>
				<dd>
					<textarea class="editor" name="fcontent" rows="20" cols="105"
						tools="simple" upImgUrl="ssadmin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
						${subscription.fcontent}
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
