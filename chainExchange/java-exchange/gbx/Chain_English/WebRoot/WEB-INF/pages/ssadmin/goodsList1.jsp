<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/goodsList1.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="supplierNo" value="${supplierNo}" /><input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="status" value="${status}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/goodsList1.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>商家号：<input type="text" name="supplierNo" value="${supplierNo}"
						size="60" />
					</td>
					<td>商品类型： <select type="combox" name="status">
							<c:forEach items="${statusMap}" var="s">
								<c:if test="${s.key == status}">
									<option value="${s.key}" selected="true">${s.value}</option>
								</c:if>
								<c:if test="${s.key != status}">
									<option value="${s.key}">${s.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td>名称：<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="ssadmin/goGoodsJSP.html?url=ssadmin/addGoods"
				height="500" width="800" target="dialog" rel="addGoods"><span>新增</span>
			</a>
			</li>
			<li><a class="edit"
				href="ssadmin/goGoodsJSP.html?url=ssadmin/updateGoods&uid={sid_user}"
				height="500" width="800" target="dialog" rel="updateGoods"><span>修改</span>
			</a>
			</li>
			<li><a class="add"
				href="ssadmin/upGoods.html?uid={sid_user}"
				target="ajaxTodo" title="确定要把该商品上架吗?"><span>商品上架</span> </a>
			</li>
			<li><a class="delete"
				href="ssadmin/takeOutGoods.html?uid={sid_user}"
				target="ajaxTodo" title="确定要把该商品下架吗?"><span>商品下架</span> </a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">显示序号</th>
				<th width="60">商品名称</th>
				<th width="40">商家UID</th>
				<th width="40">状态</th>
				<th width="40">会员价</th>
				<th width="40">市场价</th>
				<th width="40">总库存数量</th>
				<th width="40">剩余数量</th>
				<th width="40">累计销售数量</th>
				<th width="40">分类</th>
<!-- 				<th width="40">QQ</th>
				<th width="40">邮费</th>
				<th width="40">大于多少包邮</th>
				<th width="40">自定义名称1</th>
				<th width="40">自定义类型1</th>
				<th width="40">自定义名称2</th>
				<th width="40">自定义类型2</th> -->
				<th width="40">创建日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${goodsList1}" var="goodsList"
				varStatus="num">
				<tr target="sid_user" rel="${goodsList.fid}">
					<td>${goodsList.fseq}</td>
					<td>${goodsList.fname}</td>
					<td>${goodsList.fsupplierNo}</td>
					<td>${goodsList.fstatus_s}</td>
					<td>${goodsList.fprice}</td>
					<td>${goodsList.fmarketPrice}</td>
					<td>${goodsList.ftotalQty}</td>
					<td>${goodsList.flastQty}</td>
					<td>${goodsList.fsellQty}</td>
					<td>${goodsList.ftype.fname}</td>
<%-- 					<td>${goodsList.fqq}</td>
					<td>${goodsList.fpostageAmt}</td>
					<td>${goodsList.fbaoyouAmt}</td>
					<td>${goodsList.fstyleName}</td>
					<td>${goodsList.fstyleType}</td>
					<td>${goodsList.fstyleName1}</td>
					<td>${goodsList.fstyleType1}</td> --%>
					<td>${goodsList.fcreatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
