<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/shoppinglogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="supplierNo" value="${supplierNo}" /> <input
		type="hidden" name="status" value="${status}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/shoppinglogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>商家号：<input type="text" name="supplierNo" value="${supplierNo}"
						size="60" />
					</td>
					<td>订单状态： <select type="combox" name="status">
							<c:forEach items="${statusMap}" var="s">
								<c:if test="${s.key == status}">
									<option value="${s.key}" selected="true">${s.value}</option>
								</c:if>
								<c:if test="${s.key != status}">
									<option value="${s.key}">${s.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
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
			<li><a class="edit"
				href="ssadmin/goShoppinglogJSP.html?url=ssadmin/sendShoppinglog&uid={sid_user}"
				height="200" width="800" target="dialog" rel="sendShoppinglog"><span>发货</span>
			</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">订单ID</th>
				<th width="60">商品名称</th>
				<th width="40">状态</th>
				<th width="60">购买者UID</th>
				<th width="40">购买数量</th>
				<th width="40">支付类型</th>
				<th width="40">支付金额</th>
				<th width="40">手机号码</th>
				<th width="40">收货地址</th>
				<th width="40">备注</th>
				<th width="40">快递单号</th>
				<th width="40">发货日期</th>
				<th width="40">下单日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shoppinglogList}" var="shoppinglogList"
				varStatus="num">
				<tr target="sid_user" rel="${shoppinglogList.fid}">
					<td>${shoppinglogList.fid}</td>
					<td>${shoppinglogList.fgoods.fname}</td>
					<td>${shoppinglogList.fstatus_s}</td>
					<td>${shoppinglogList.fuser.fid}</td>
					<td>${shoppinglogList.fqty}</td>
					<td>${shoppinglogList.fpaytype_s}</td>
					<td>${shoppinglogList.fpriceAmt}</td>
					<td>${shoppinglogList.fphone}</td>
					<td>${shoppinglogList.freceiveAddress}</td>
					<td>${shoppinglogList.fremark}</td>
					<td>${shoppinglogList.fexpressNo}</td>
					<td>${shoppinglogList.fsendtime}</td>
					<td>${shoppinglogList.fcreatetime}</td>
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
