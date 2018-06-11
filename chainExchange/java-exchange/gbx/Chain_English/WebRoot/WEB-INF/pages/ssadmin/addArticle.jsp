<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">Add News</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveArticle.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">	
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>News Title</dt>
				<dd>
					<input type="text" name="ftitle"
						class="required" size="70" />
				</dd>
			</dl>

			<dl>
				<dt>News Title</dt>
				<dd>
					<input type="text" name="ftitleEn"
						   class="required" size="70" />
				</dd>
			</dl>

			<dl>
				<dt>Type</dt>
				<dd>
					<input type="hidden" name="articleLookup.id" id="articleLookup.id" value="${articleLookup.id}"/>
				    <input type="text" class="required" name="articleLookup.articleType" value="" suggestFields="id,articleType"
				     suggestUrl="ssadmin/articleTypeLookup.html" lookupGroup="orgLookup" readonly="readonly" size="70"/>
				    <a class="btnLook" href="ssadmin/articleTypeLookup.html" lookupGroup="articleLookup">Lookup back</a>
				</dd>
			</dl>
			<dl>
				<dt>Contents</dt>
				<dd>
					<textarea class="editor" name="fcontent" rows="20" cols="105"
						tools="simple" upImgUrl="ssadmin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
			</dl>
			<dl>
				<dt>Contents</dt>
				<dd>
					<textarea class="editor" name="fcontentEn" rows="20" cols="105"
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
