<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../comm/include.inc.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
                <c:if test="${fentrusts2.size() >0 }">
                    <c:forEach items="${fentrusts2 }" var="v" varStatus="vs">
                        <li class="border-bottom">
                            <span><fmt:formatDate value="${v.fcreateTime }" pattern="HH:mm:ss"/></span>
                            <span><em class="<c:if test="${'买入'==v.fentrustType_s}">sell</c:if><c:if test="${'卖出'==v.fentrustType_s}">pay</c:if>"><i>${v.fentrustType_s}</i>${v.fisLimit==true?'[市价]':'' }${coin1.fSymbol}<fmt:formatNumber value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></em></span>
                            <span>${coin2.fSymbol}<fmt:formatNumber value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></span>
                            <span>${coin1.fSymbol}<fmt:formatNumber value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${fentrusts2.size() <= 0 }">
                    <div class="no_cont">
                        <img src="/static/front/app/images/no_cont.png">
                        <p>暂无数据</p>
                    </div>
                </c:if>