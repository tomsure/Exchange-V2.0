<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
	<div class="pageFormContent" layoutH="20">

		<fieldset>
			<legend>Member Info.</legend>
			<dl>
				<dt>Member Total</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${totalUser}</span>
				</dd>
			</dl>
			<dl>
				<dt>Authenticated Total</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${totalValidateUser}</span>
				</dd>
			</dl>
			<dl>
				<dt>Current Date New Members</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${todayTotalUser}</span>
				</dd>
			</dl>
			<dl>
				<dt>Current Date Authenticated</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${todayValidateUser}</span>
				</dd>
			</dl>
		</fieldset>

		<fieldset>
			<legend>Current Date Deposit</legend>
			<c:forEach items="${amountInMap }" var="v">
			<dl>
				<dt >Amount USD</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
								value="${v.value}" pattern="#0.######" /></span>
				</dd>
			</dl>
			</c:forEach>
			 <dl>
				<dt>Manual Deposit</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
								value="${todayOpAmt}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>
		
		<fieldset>
			<legend>Total Deposit</legend>
			<c:forEach items="${totalAmountInMap }" var="v">
			<dl>
				<dt >Total USD Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
								value="${v.value}" pattern="#0.######" /></span>
				</dd>
			</dl>
			</c:forEach>
			<dl>
				<dt>Total Manual Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
								value="${totalOpAmt}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>

		<fieldset>
			<legend>All Funds Statistics</legend>
			<c:forEach items="${virtualQtyList}" var="virtual">
				<dl>
					<dt>Total ${virtual.fShortName}:</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtual.totalQty}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<dl>
					<dt>Frozen:</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtual.frozenQty}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>

		<fieldset>
			<legend>Incompleted Pending Statistics</legend>
			<c:forEach items="${entrustBuyMap}" var="entrustBuy">
				<dl>
					<dt>Bought ${entrustBuy.fShortName} Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustBuy.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustSellMap}" var="entrustSell">
				<dl>
					<dt>Sold ${entrustSell.fShortName} Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>


		<fieldset>
			<legend>Accumulated Pending Withdrawal</legend>
			<dl>
				<dt>USD Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
							value="${amountOutWaitingMap.totalAmount}" pattern="#0.######" />
					</span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutWaitingMap}" var="virtualOutWaiting">
				<dl>
					<dt>${virtualOutWaiting.fShortName} Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutWaiting.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>

		<fieldset>
			<legend>Current Date Withdrawal Summary</legend>
			<dl>
				<dt>Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
							value="${amountOutMap.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutSuccessMap}" var="virtualOutSuccess">
				<dl>
					<dt>Total ${virtualOutSuccess.fName} Withdrawn Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>Accumulated Withdrawal</legend>
			<dl>
				<dt>USD Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
							value="${amountOutMap1.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutSuccessTotalMap}" var="virtualOutSuccess">
				<dl>
					<dt>${virtualOutSuccess.fName} Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>Today's Trading Fee(Withdrawal)</legend>
			<dl>
				<dt>Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
							value="${amountOutFeeMap1.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutFeesMap}" var="virtualOutSuccess">
				<dl>
					<dt>Today ${virtualOutSuccess.fName} Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>Accumulated Trading Fee(Withdrawal)</legend>
			<dl>
				<dt>Total Amount</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">$<fmt:formatNumber
							value="${amountOutFeeMap2.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutFeesTotalMap}" var="virtualOutSuccess">
				<dl>
					<dt>Total ${virtualOutSuccess.fName} Qty.</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>USD Trading Fee Details</legend>
			<c:forEach items="${entrustSellFeesMap1}" var="entrustSell">
				<dl>
					<dt>Today's Fees ${entrustSell.fName}</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit">$<fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustSellFeesMap2}" var="entrustSell">
				<dl>
					<dt>Today's Fees ${entrustSell.fName}</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit">$<fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>Cryptocurrency Trading Fee</legend>
			<c:forEach items="${entrustBuyFeesMap1}" var="entrustSell">
				<dl>
					<dt>${entrustSell.fName}(Totay)</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustBuyFeesMap2}" var="entrustSell">
				<dl>
					<dt>${entrustSell.fName} Today</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>USD Trading Fee Summary</legend>
			<dl style="width: 900px;">
					<dt style="width: 200px;">Today's Trading Amount</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit">$<fmt:formatNumber
								value="${entrustSellFeesMap11}" pattern="#0.##" /> </span>
					</dd>
					<dt style="width: 200px;">Accumulated Trading Amount</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit">$<fmt:formatNumber
								value="${entrustSellFeesMap22}" pattern="#0.##" /> </span>
					</dd>
				</dl>
			
		</fieldset>	
	</div>
</body>
</html>

