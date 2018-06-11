<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>


<h4 id="currentH4">
	<font style="vertical-align: inherit;cursor: pointer;">
		<font style="vertical-align: inherit;" id="currentFont1" onclick="showCurrent();">Pending Order</font></font>
	<font style="float: right;cursor: pointer;font-size:18px;width:150px;">
		<div style="background-image:url(${oss_url}/static/front/images/trade/red-button.png);background-size:100% 100%;text-align:center;">
			<font id="yijianCancel" class="allcancel opa-link" data-value="${ftrademapping.fid }" style="vertical-align: inherit;">Cancel All</font>
		</div>
	</font>
</h4>
<div class="trade" id="currentDiv">
	<table class="table2 table-striped table-hover table-condensed border-light-grey trading-orderbook" id="fentrustsbody1">
		<thead>
			<tr class="row-head row-narrow row-align-right regular-font-size">
				<th>Time</th>
				<th>Type</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Total</th>
				<th>Quantity of Transactions</th>
				<th>Transacted Amount</th>
				<th>Fee</th>
				<th>Status</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody id="entrutsCurData">
			<c:forEach items="${fentrusts1 }" var="v" varStatus="vs">
				<tr>
					<td class="text-small-2x gray"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="text-small-2x ${v.fentrustType==0?'text-success':'text-danger' }">${v.fentrustType_s}${v.fisLimit==true?'[市价]':'' }</td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<c:choose>
					<c:when test="${v.fentrustType==0 }">
					<td class="text-small-2x"><fmt:formatNumber value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					</c:when>
					<c:otherwise>
					<td class="text-small-2x"><fmt:formatNumber value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					</c:otherwise>
					</c:choose>
					<td class="text-small-2x">
					${v.fstatus_s }
					</td>
					<td class="text-small-2x opa-link">
					<c:if test="${v.fstatus==1 || v.fstatus==2}">
					<a href="javascript:void(0);" class="tradecancel opa-link" data-value="${v.fid}">Cancel</a>
					</c:if>
					<c:if test="${v.fstatus==3}">
					<a href="javascript:void(0);" class="tradelook opa-link" data-value="${v.fid}">View</a>
					</c:if>
					</td>
                 </tr>
			</c:forEach>

			<tr>
				<td colspan="10" class="text-small-2x" style="text-align: center;" id="entrutsCurFot"><a href="/trade/entrust.html?status=0&symbol=${ftrademapping.fid }">more&gt;&gt;</a></td>
			</tr>
		</tbody>
	</table>
</div>

<h4 id="mydealH4">
	<font style="vertical-align: inherit;cursor: pointer;">
		<font style="vertical-align: inherit;" id="myDealFont2" onclick="showMydeal();">Deal Record</font></font>
</h4>

<div class="trade" id="mydealDiv">
	<table class="table2 table-striped table-hover table-condensed border-light-grey trading-orderbook" id="fentrustsbody0">
		<thead>
			<tr class="row-head row-narrow row-align-right regular-font-size">
				<th>Time</th>
				<th>Type</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Total</th>
				<th>Quantity of Transactions</th>
				<th>Transacted Amount</th>
				<th>Avg Price</th>
				<th>Fee</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody id="entrutsCurData">
			<c:forEach items="${fentrusts2 }" var="v" varStatus="vs">
				<tr>
					<td class="text-small-2x gray"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="text-small-2x ${v.fentrustType==0?'text-success':'text-danger' }">${v.fentrustType_s}${v.fisLimit==true?'[市价]':'' }</td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<td class="text-small-2x"><fmt:formatNumber value="${((v.fcount-v.fleftCount)==0)?0:  v.fsuccessAmount/((v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					<c:choose>
					<c:when test="${v.fentrustType==0 }">
					<td class="text-small-2x"><fmt:formatNumber value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					</c:when>
					<c:otherwise>
					<td class="text-small-2x"><fmt:formatNumber value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
					</c:otherwise>
					</c:choose>
					<td class="text-small-2x">
					${v.fstatus_s }
					</td>
                 </tr>  
			</c:forEach>
			
			<tr>
				<td colspan="10" class="text-small-2x" style="text-align: center;" id="entrutsCurFot"><a href="/trade/entrust.html?status=1&symbol=${ftrademapping.fid }">more&gt;&gt;</a></td>
			</tr>
		</tbody>
	</table>
</div>

<script>
//    function showCurrent()
//    {
//        $("#currentH4").show();
//        $("#currentDiv").show();
//        $("#mydealH4").hide();
//        $("#mydealDiv").hide();
//        $("#currentFont1").attr("class","text-color2");
//        $("#myDealFont1").attr("class","text-color2");
//        $("#currentFont2").attr("class","");
//        $("#myDealFont2").attr("class","");
//        $("#yijianCancel").show();
//    }
//
//    function showMydeal()
//    {
//        $("#currentH4").hide();
//        $("#currentDiv").hide();
//        $("#mydealH4").show();
//        $("#mydealDiv").show();
//        $("#currentFont1").attr("class","");
//        $("#myDealFont1").attr("class","");
//        $("#currentFont2").attr("class","text-color2");
//        $("#myDealFont2").attr("class","text-color2");
//        $("#yijianCancel").hide();
//    }
</script>