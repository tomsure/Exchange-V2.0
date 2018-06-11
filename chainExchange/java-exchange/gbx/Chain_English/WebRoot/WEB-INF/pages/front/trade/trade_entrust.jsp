<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>





 



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link rel="stylesheet" href="${oss_url}/static/front/css/finance/accountrecord.css" type="text/css"></link>
</head>
<body>
	
<jsp:include page="../comm/header.jsp"></jsp:include>



	<div class="container-full">
		<div class="container displayFlex">
		<div class="row">
			
<%@include file="../comm/left_menu.jsp" %>


			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea record">
					<div class="col-xs-12 rightarea-con">
					
                        <div class="panel-heading padding-bottom-clear">
							<div class="form-group">
								<span>Type </span>
								
								<select class="form-control typeselect" id="recordType">
								<option value="/trade/entrust.html?status=${status }&symbol=0">All</option>
								<c:forEach var="v" varStatus="vs" items="${requestScope.constant['tradeMappingss'] }">
										<option value="/trade/entrust.html?status=${status }&symbol=${v.fid}" ${ftrademapping.fid==v.fid?'selected':'' }>
										${v.fvirtualcointypeByFvirtualcointype1.fShortName } &#8594  ${v.fvirtualcointypeByFvirtualcointype2.fShortName }
										</option>
								</c:forEach>		
								</select>
							</div>
						</div>
                
						<div class="col-xs-12 padding-clear">
							<table class="table table-striped text-left">
					<tr class="bg-gary">
									<td class="text-center">
										Time
									</td>
									<td>
										Type
									</td>
									<td>
										Number
									</td>
									<td>
										Price
									</td>
									<td>
										Amount
									</td>
									<td>
										Volumes
									</td>
									<td>
										Transacted Amount
									</td>
									<td>
										Fee
									</td>
									<td>
										Average Price
									</td>
									<td>
										Status/Action
									</td>
								</tr>
					
									<c:if test="${fn:length(fentrusts)==0 }">
										<tr>
										<td class="no-data-tips" colspan="10">
											<span>
												No commission.
											</span>
										</td>
									</tr>
									</c:if>
									
									<c:forEach items="${fentrusts }" var="v" varStatus="vs">
					<tr>
						<td class="gray"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="${v.fentrustType==0?'text-success':'text-danger' }">(${v.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname})${v.fentrustType_s}${v.fisLimit==true?'[市价]':'' }</td>
						<td><fmt:formatNumber value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount2 }"/></td>
						<td><fmt:formatNumber value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount1 }"/></td>
						<td><fmt:formatNumber value="${v.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount1 }"/></td>
						<td><fmt:formatNumber value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount2 }"/></td>
						<td><fmt:formatNumber value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount1 }"/></td>
						<c:choose>
						<c:when test="${v.fentrustType==0 }">
						<td><fmt:formatNumber value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount1 }"/></td>
						</c:when>
						<c:otherwise>
						<td><fmt:formatNumber value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount1 }"/></td>
						</c:otherwise>
						</c:choose>
						<td><fmt:formatNumber value="${((v.fcount-v.fleftCount)==0)?0:  v.fsuccessAmount/((v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${v.ftrademapping.fcount1 }"/></td>
						<td>
						${v.fstatus_s }
						<c:if test="${v.fstatus==1 || v.fstatus==2}">
						&nbsp;|&nbsp;<a href="javascript:void(0);" class="tradecancel" data-value="${v.fid}" refresh="1">Cancel</a>
						</c:if>
						<c:if test="${v.fstatus==3}">
						&nbsp;|&nbsp;<a href="javascript:void(0);" class="tradelook" data-value="${v.fid}">View</a>
						</c:if>
						</td>
                          </tr>
			</c:forEach>
			
								
							</table>
							<div class="text-right">
								${pagin}
							</div>
							
						</div>
						<input type="hidden" value="${currentPage}" id="currentPage">
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="modal modal-custom fade" id="entrustsdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"></span>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">close</button>
				</div>
			</div>
		</div>
	</div>
	
<input id="fhasRealValidate" type="hidden" value="true">
<input type="hidden" id="symbol" value="${ftrademapping.fid }">
<jsp:include page="../comm/footer.jsp"></jsp:include>
<script type="text/javascript" src="${oss_url}/static/front/js/trade/trade.js"></script>
</body>
</html>
