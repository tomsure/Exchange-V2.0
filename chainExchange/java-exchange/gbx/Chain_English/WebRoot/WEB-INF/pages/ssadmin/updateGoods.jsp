<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改商品信息</h2>


<div class="pageContent">
	<form method="post" action="ssadmin/updateGoods.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>商品名称：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${goods.fid }"/>
					<input type="text" name="fname"  class="required"
						size="70"  value="${goods.fname }"/>
				</dd>
			</dl>
			<dl>
				<dt>单价：</dt>
				<dd>
					<input type="text" name="fprice"  class="required number"
						size="20"  value="${goods.fprice }"/>
				</dd>
			</dl>
			<dl>
				<dt>市场价：</dt>
				<dd>
					<input type="text" name="fmarketPrice"  class="required number"
						size="20"  value="${goods.fmarketPrice }"/>
				</dd>
			</dl>
			<%-- <dl>
				<dt>邮费：</dt>
				<dd>
					<input type="text" name="fpostageAmt" maxlength="20" class="required number"
						size="20"  value="${goods.fpostageAmt }"/>
				</dd>
			</dl>
			<dl>
				<dt>总价大于多少包邮：</dt>
				<dd>
					<input type="text" name="fbaoyouAmt" maxlength="20" class="required number"
						size="20"  value="${goods.fbaoyouAmt }"/>
				</dd>
			</dl> --%>
			<dl>
				<dt>分类：</dt>
				<dd>
					<select type="combox" name="ftype">
					<c:forEach items="${typeMap}" var="t">
					<c:if test="${t.key == goods.ftype.fid}">
				    	<option value="${t.key}" selected="true">${t.value}</option>
					</c:if>
					<c:if test="${t.key != goods.ftype.fid}">
				    	<option value="${t.key}">${t.value}</option>
					</c:if>
					</c:forEach>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>总库存数量：</dt>
				<dd>
					<input type="text" name="ftotalQty"  class="required digits"
						size="20"  value="${goods.ftotalQty }"/>
				</dd>
			</dl>
			<dl>
				<dt>剩余数量：</dt>
				<dd>
					<input type="text" name="flastQty"  class="required digits"
						size="20"  value="${goods.flastQty }"/>
				</dd>
			</dl>
			<%-- <dl>
				<dt>商家UID：</dt>
				<dd>
					<input type="text" name="fsupplierNo" 
						class="required" size="40"  value="${goods.fsupplierNo }"/>
				</dd>
			</dl>
 			<dl>
				<dt>QQ号：</dt>
				<dd>
					<input type="text" name="fqq" 
						class="required" size="40"  value="${goods.fqq }"/>
				</dd>
			</dl>
			<dl>
				<dt>自定义名称1：</dt>
				<dd>
					<input type="text" name="fstyleName" maxlength="20"
						class="required" size="40" value="${goods.fstyleName }"/>
				</dd>
			</dl>
			<dl>
				<dt>自定义类型1：</dt>
				<dd>
					<input type="text" name="fstyleType"
						class="required" size="40" value="${goods.fstyleType }"/><span>用#号分开</span>
				</dd>
			</dl>
			<dl>
				<dt>自定义名称2：</dt>
				<dd>
					<input type="text" name="fstyleName1" maxlength="20"
						class="required" size="40" value="${goods.fstyleName1 }"/>
				</dd>
			</dl>
			<dl>
				<dt>自定义类型2：</dt>
				<dd>
					<input type="text" name="fstyleType1"
						class="required" size="40" value="${goods.fstyleType1 }"/><span>用#号分开</span>
				</dd>
			</dl> --%>
			<dl>
				<dt>图片链接：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
		<!-- 	<dl>
				<dt>图片链接2：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata1"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>图片链接3：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata2"
						id="filedata" />
				</dd>
			</dl> -->
			<dl>
				<dt>允许付款类型：</dt>
				<dd>
					<input type="text" name="fbuyType"  value="${goods.fbuyType }" class="required"/>
		            <span>币ID，用#隔开，人民币为4</span>
				</dd>
			</dl>
			<dl>
				<dt>显示序号：</dt>
				<dd>
					<input type="text" name="fseq" maxlength="20" class="required digits"
						size="20" value="${goods.fseq }" />
					<span>越小显示越前</span>	
				</dd>
			</dl>
			<dl>
				<dt>商品描述：</dt>
				<dd>
					<textarea class="editor" name="fdescription" rows="20" cols="105"
						tools="mfull" upImgUrl="ssadmin/upload.html"
						upImgExt="jpg,jpeg,gif,png">${goods.fdescription }
				</textarea>
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
	function customvalidXxx(element) {
		if ($(element).val() == "xxx")
			return false;
		return true;
	}
</script>
