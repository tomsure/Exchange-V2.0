<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<table class="table">
	<tr>
		<td>Order</td>
		<td>Time</td>
		<td>Receive</td>
		<td>Type</td>
		<td>Amount($)</td>
		<td>Status</td>
		<td>Action</td>
	</tr>
	<c:forEach items="${list}" var="v">
		<tr>
			<td class="gray">${v.fid }</td>
			<td><fmt:formatDate value="${v.fcreateTime }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>${v.systembankinfo.fownerName }<br/>
					${v.systembankinfo.fbankNumber }<br/>
					${v.systembankinfo.fbankAddress}
			</td>
			<td>${v.fremittanceType }</td>
			<td>${v.famount }</td>
			<td>${v.fstatus_s }</td>
			<td class="opa-link"><c:if
					test="${(v.fstatus==1 || v.fstatus==2)}">
					<a class="rechargecancel opa-link" href="javascript:void(0);"
						data-fid="${v.fid }">Cancel</a>
					<c:if test="${v.fstatus==1}">
						&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link"
							href="javascript:void(0);" data-fid="${v.fid }">Submit</a>
					</c:if>
				</c:if> <c:if test="${(v.fstatus==3 || v.fstatus==4)}">
				--
			</c:if>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${fn:length(list)==0 }">
		<tr>
			<td colspan="6" class="no-data-tips" align="center"><span>
					You don't have recharge data for the moment. </span></td>
		</tr>
	</c:if>
</table>

<input type="hidden" value="${cur_page }" name="currentPage"
	id="currentPage"></input>
<div class="text-right">${pagin }</div>