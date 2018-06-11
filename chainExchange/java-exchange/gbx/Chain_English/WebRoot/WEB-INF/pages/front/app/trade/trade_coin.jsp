<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../comm/include.inc.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <title>交易中心</title>
    <link rel="stylesheet" href="/static/front/app/css/css.css" />
    <link rel="stylesheet" href="/static/front/app/css/style.css" />
    <link rel="stylesheet" href="/static/front/app/css/swiper-3.4.2.min.css" />
    <script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
</head>
<body>
<nav>
    <div class="pay-centre">
        <a href="javascript:;" onClick="javascript :history.back(-1)">
					<span>
						<em>
							<i></i>
							<i></i>
						</em>
						<strong>返回</strong>
					</span>
        </a>
        <p><strong>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fname }  ${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</strong></p>
    </div>
</nav>
<section class="cont-padding">
    <div class="centre_bi clearfix">
        <dl>
            <dt>
                <img src="${ftrademapping.fvirtualcointypeByFvirtualcointype2.furl }"/>
                <span>
							<select style="width: 120px;" onchange="changeC(this.value);">
                                <c:forEach var="v" varStatus="vs" items="${ftrademappings }">
                                    <option ${v.fid==ftrademapping.fid?'selected':'' } value="${v.fvirtualcointypeByFvirtualcointype2.fid}">${v.fvirtualcointypeByFvirtualcointype2.fname }${v.fvirtualcointypeByFvirtualcointype2.fShortName }</option>
                                </c:forEach>
							</select>
						</span>
            </dt>
            <dd><img src="/static/front/app/images/chart.png"></dd>
        </dl>
    </div>
    <div class="centre-bargain">
        <div class="bargain-title bgfff">
            <ul>
                <li>最新价格</li>
                <li>&nbsp;&nbsp;最低价</li>
                <li>&nbsp;&nbsp;最高价</li>
                <li>&nbsp;&nbsp;成交量</li>
            </ul>
        </div>
        <div class="bargain-con border-bottom">
            <ul>
                <li id="lastprice">-</li>
                <li id="now_low">-</li>
                <li id="now_high">-</li>
                <li id="now_total">-</li>
            </ul>
        </div>
    </div>
    <div class="forbid">

        <div class="centre-tab">
            <ul>
                <li class="active">买入</li>
                <li>卖出</li>
                <li>我的委托</li>
            </ul>
        </div>

        <div class="contre-tab-con" style="display: block;">
            <div class="centre-con border-top border-bottom">
                <div class="centre-left">
                    <div class="centre-title border-bottom">
                        <ul>
                            <li>委托</li>
                            <li>单价</li>
                            <li>委托量</li>
                        </ul>
                    </div>
                    <div class="centre-cont border-bottom">
                        <ul class="sellbox">
                        </ul>
                    </div>
                    <div class="centre-cont">
                        <ul class="buybox">
                        </ul>
                    </div>
                    <div class="more-entrust">
                        <a href="gengduoweituo.html" class="green-1px">更多委托</a>
                    </div>
                </div>
                <div class="centre-right">
                    <div class="centre-bg border-bottom">
                        <p>${coin1.fShortName }可用 <em id="toptradecnymoney">0.00</em></p>
                    </div>
                    <div class="centre">买入价格/CNY</div>
                    <div class="centre-input">
                        <input type="number" id="tradebuyprice" value="${recommendPrizebuy }">
                        <em>CNY</em>
                    </div>
                    <div class="centre">买入数量</div>
                    <div class="centre-input">
                        <input type="number" id="tradebuyamount" placeholder="0">
                        <em>${coin2.fShortName }</em>
                    </div>
                    <div class="proportion">
                        <div class="proportion_bg">
                            <ul>
                                <li class="active">30% <span>30%</span></li>
                                <li>60% <span>60%</span></li>
                                <li>90% <span>90%</span></li>
                            </ul>
                        </div>
                    </div>
                    <div class="centre clearfix">交易额<em>手续费:0.00</em></div>
                    <div class="centre-input">
                        <input type="number" id="tradesellTurnover" placeholder="0">
                        <em>CNY</em>
                    </div>
                    <div class="centre">交易密码</div>
                    <div class="centre-input">
                        <input type="password" placeholder="">
                    </div>
                    <div class="pay-entrust">
                        <a href="javascript:;">买入</a>
                    </div>
                    <div class="Recharge"><a href="dizhichongzhi.html">立即充值→</a></div>
                </div>
            </div>
            <div class="hint border-bottom">数字货币交易具有极高的风险，投资需谨慎！</div>
        </div>

        <div class="contre-tab-con">
            <div class="centre-con border-top border-bottom">
                <div class="centre-left">
                    <div class="centre-title border-bottom">
                        <ul>
                            <li>委托</li>
                            <li>单价</li>
                            <li>委托量</li>
                        </ul>
                    </div>
                    <div class="centre-cont border-bottom">
                        <ul class="sellbox">
                        </ul>
                    </div>
                    <div class="centre-cont">
                        <ul class="buybox">
                        </ul>
                    </div>
                    <div class="more-entrust">
                        <a href="gengduoweituo.html" class="green-1px">更多委托</a>
                    </div>
                </div>
                <div class="centre-right">
                    <div class="centre-bg border-bottom">
                        <p>TXC可用 <em>100.00</em></p>
                    </div>
                    <div class="centre">卖出价格/CNY</div>
                    <div class="centre-input">
                        <input type="number" placeholder="3.606">
                        <em>CNY</em>
                    </div>
                    <div class="centre">卖出数量</div>
                    <div class="centre-input">
                        <input type="number" placeholder="0">
                        <em>TXC</em>
                    </div>
                    <div class="centre clearfix">交易额<em>手续费:0.00</em></div>
                    <div class="centre-input">
                        <input type="number" placeholder="0">
                        <em>CNY</em>
                    </div>
                    <div class="centre">交易密码</div>
                    <div class="centre-input">
                        <input type="text" placeholder="0">
                    </div>
                    <div class="pay-entrust">
                        <a href="javascript:;" class="active">卖出</a>
                    </div>
                    <div class="Recharge"><a href="dizhichongzhi.html" class="active">立即充值→</a></div>
                </div>
            </div>
            <div class="hint border-bottom">数字货币交易具有极高的风险，投资需谨慎！</div>
        </div>
        <div class="contre-tab-con">
            <div class="record">
                <div class="record-info">
                    <div class="record-more">
                        <dl>
                            <dt>
                            <h2>(檀香币/CNY) 买入</h2>
                            <p>数量：<span>T1000</span></p>
                            </dt>
                            <dd>
                                <h2><span>总金额：</span>￥100.86</h2>
                                <p>单价：<span>￥3.069</span></p>
                            </dd>
                        </dl>
                    </div>
                    <div class="record-state">状态：<span>已成交</span><em><i></i><i></i></em></div>
                </div>
            </div>
            <div class="record">
                <div class="record-info">
                    <div class="record-more">
                        <dl>
                            <dt>
                            <h2>(檀香币/CNY) 买入</h2>
                            <p>数量：<span>T1000</span></p>
                            </dt>
                            <dd>
                                <h2><span>总金额：</span>￥100.86</h2>
                                <p>单价：<span>￥3.069</span></p>
                            </dd>
                        </dl>
                    </div>
                    <div class="record-state">状态：<span>未成交</span><a href="javascript:;">取消</a><em><i></i><i></i></em></div>
                </div>
            </div>
            <div class="record">
                <div class="record-info">
                    <div class="record-more">
                        <dl>
                            <dt>
                            <h2>(檀香币/CNY) 买入</h2>
                            <p>数量：<span>T1000</span></p>
                            </dt>
                            <dd>
                                <h2><span>总金额：</span>￥100.86</h2>
                                <p>单价：<span>￥3.069</span></p>
                            </dd>
                        </dl>
                    </div>
                    <div class="record-state">状态：<span>未成交</span><a href="javascript:;">取消</a><em><i></i><i></i></em></div>
                </div>
            </div>
            <div class="record">
                <div class="record-info">
                    <div class="record-more">
                        <dl>
                            <dt>
                            <h2>(檀香币/CNY) 买入</h2>
                            <p>数量：<span>T1000</span></p>
                            </dt>
                            <dd>
                                <h2><span>总金额：</span>￥100.86</h2>
                                <p>单价：<span>￥3.069</span></p>
                            </dd>
                        </dl>
                    </div>
                    <div class="record-state">状态：<span>未成交</span><a href="javascript:;">取消</a><em><i></i><i></i></em></div>
                </div>
            </div>
            <div class="record">
                <div class="record-info">
                    <div class="record-more">
                        <dl>
                            <dt>
                            <h2>(檀香币/CNY) 买入</h2>
                            <p>数量：<span>T1000</span></p>
                            </dt>
                            <dd>
                                <h2><span>总金额：</span>￥100.86</h2>
                                <p>单价：<span>￥3.069</span></p>
                            </dd>
                        </dl>
                    </div>
                    <div class="record-state">状态：<span>未成交</span><a href="javascript:;">取消</a><em><i></i><i></i></em></div>
                </div>
            </div>
        </div>

        <div class="forbid-info">
            <h2>暂未开启交易</h2>
            <p>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fname }暂未开启交易，详情请留意官方公告</p>
        </div>
    </div>
    <div class="Bid-History">
        <div class="Histor-title">成交记录</div>
        <div class="Histor-cont border-bottom border-top">
            <div class="centre-title border-bottom">
                <ul>
                    <li>时间</li>
                    <li>成交价</li>
                    <li>成交量</li>
                    <li>总金额(CNY)</li>
                </ul>
            </div>
            <div class="cont">
                <ul id="entrustInfo">

                </ul>
            </div>
        </div>
    </div>
</section>
<article>
    <div class="popup">
        <div class="popup-cont">
            <h2>请先登录再进行此操作</h2>
            <p><a href="javascript:;">确定</a></p>
        </div>
    </div>
</article>
<input id="coinshortName" type="hidden" value="${coin2.fShortName }">
<input id="symbol" type="hidden" value="${ftrademapping.fid }">
<input id="isopen" type="hidden" value="${needTradePasswd }">
<input id="tradeType" type="hidden" value="0">
<input id="userid" type="hidden" value="${userid }">

<input id="tradePassword" type="hidden" value="${isTradePassword }">
<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">


<input id="coinCount1" type="hidden" value="${ftrademapping.fcount1 }">
<input id="coinCount2" type="hidden" value="${ftrademapping.fcount2 }">
<input id="minBuyCount" type="hidden" value="<fmt:formatNumber
									value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15"
									maxFractionDigits="8" />">
<input id="limitedType" type="hidden" value="0">
<input id="fhasRealValidate" type="hidden" value="${fuser.fhasRealValidate }">
<jsp:include page="../comm/menu.jsp"></jsp:include>
</body>


<script type="text/javascript" src="/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="/static/front/app/js/trade.js?r=<%=new java.util.Date().getTime() %>"></script>
</html>
<script>
    function changeC(value)
    {
        window.location.href = "/m/trade/coin.html?coinType="+value+"&tradeType=0";
    }

    $(function(){
        $(".classification-tab ul li").on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
            $(".classification").hide();
            $(".pay-centre p strong").html($(this).html());
        });
        $(".munu ul li").on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
        });
        $(".centre-tab ul li").on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
            var index =  $(".centre-tab ul li").index(this);
            $(".contre-tab-con").eq(index).show().siblings('.contre-tab-con').hide();
        });
        $(".record").on('click',function(){
            window.location.href="weituoguanli_xiangxi.html";
        });
        $(".centre_bi dl dd").on('click',function(){
            window.location.href="Kxiantu.html";
        });

        $(".pay-entrust").on('click',function(){
            $(".popup").show();
        });
        $(".popup-cont p").on('click',function(){
            $(".popup").hide();
            window.location.href="login.html";
        });
        $(".popup").on('click',function(){
            $(this).hide();
        });
        $(".proportion_bg ul li").on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
        });
    });
</script>