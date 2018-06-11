<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath2 = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort();
%>

<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<link rel="stylesheet" href="${oss_url}/static/front/css/fullkline/fullScreenKline.css" type="text/css"></link>
</head>
<!-- Chart Container -->
<div id="chart_container" class="dark">
	<!-- Dom Element Cache -->
	<div id="chart_dom_elem_cache"></div>
	<!-- ToolBar -->
	<div id="chart_toolbar">
		<div class="chart_toolbar_minisep"></div>
		<!-- Symbol Selector -->
		<div class="chart_dropdown" id="chart_dropdown_symbols" style="display:none;">
			<div class="chart_dropdown_t">
				<a>
					<span class="chart_str_btc_this_week">当周 BTC</span>
					<span></span>
				</a>
			</div>
			<div class="chart_dropdown_data">
				<table>
					<tr>
						<td>BTC</td>
						<td>
							<ul id="chart_symbols_btc">
								<li>
									<a>
										<span class="chart_str_btc_this_week">当周 BTC</span>
										<span></span>
									</a>
								</li>
								<li>
									<a>
										<span class="chart_str_btc_next_week">次周 BTC</span>
										<span></span>
									</a>
								</li>
								<li>
									<a>
										<span class="chart_str_btc_month">全月 BTC</span>
										<span></span>
									</a>
								</li>
								<li>
									<a>
										<span class="chart_str_btc_quarter">季度 BTC</span>
										<span></span>
									</a>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<td>LTC</td>
						<td>
							<ul id="chart_symbols_ltc">
								<li>
									<a>
										<span class="chart_str_ltc_this_week">当周 LTC</span>
										<span></span>
									</a>
								</li>
								<li>
									<a>
										<span class="chart_str_ltc_next_week">次周 LTC</span>
										<span></span>
									</a>
								</li>
								<li>
									<a>
										<span class="chart_str_ltc_month">全月 LTC</span>
										<span></span>
									</a>
								</li>
								<li>
									<a>
										<span class="chart_str_ltc_quarter">季度 LTC</span>
										<span></span>
									</a>
								</li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- Periods -->
		<div class="chart_dropdown" id="chart_toolbar_periods_vert">
			<div class="chart_dropdown_t">
				<a class="chart_str_period"></a>
			</div>
			<div class="chart_dropdown_data">
				<table>
					<tr>
						<td>
							<ul>
								<li id="chart_period_line_v" name="line">
									<a class="chart_str_period_line"></a>
								</li>
								<li id="chart_period_1m_v" name="1m">
									<a class="chart_str_period_1m"></a>
								</li>
								<li id="chart_period_5m_v" name="5m">
									<a class="chart_str_period_5m"></a>
								</li>
								<li id="chart_period_15m_v" name="15m">
									<a class="chart_str_period_15m"></a>
								</li>
								<li id="chart_period_30m_v" name="30m">
									<a class="chart_str_period_30m"></a>
								</li>
								<li id="chart_period_1h_v" name="1h">
									<a class="chart_str_period_1h"></a>
								</li>
								<li id="chart_period_1d_v" name="1d">
									<a class="chart_str_period_1d"></a>
								</li>
								<li id="chart_period_1w_v" name="1w">
									<a class="chart_str_period_1w"></a>
								</li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- Periods -->
		<div id="chart_toolbar_periods_horz">
			<ul class="chart_toolbar_tabgroup" style="padding-left:5px; padding-right:11px;">
				<li id="chart_period_line_h" name="line">
					<a class="chart_str_period_line"></a>
				</li>
				<li id="chart_period_1m_h" name="1m">
					<a class="chart_str_period_1m"></a>
				</li>
				<li id="chart_period_5m_h" name="5m">
					<a class="chart_str_period_5m"></a>
				</li>
				<li id="chart_period_15m_h" name="15m">
					<a class="chart_str_period_15m"></a>
				</li>
				<li id="chart_period_30m_h" name="30m">
					<a class="chart_str_period_30m"></a>
				</li>
				<li id="chart_period_1h_h" name="1h">
					<a class="chart_str_period_1h"></a>
				</li>
				<li id="chart_period_1d_h" name="1d">
					<a class="chart_str_period_1d"></a>
				</li>
				<li id="chart_period_1w_h" name="1w">
					<a class="chart_str_period_1w"></a>
				</li>
			</ul>
		</div>
		<!-- Open TabBar -->
		<div id="chart_show_indicator" class="chart_toolbar_button chart_str_indicator_cap"></div>
		<!-- Open ToolPanel -->
		<div id="chart_show_tools" class="chart_toolbar_button chart_str_tools_cap"></div>
		<!-- Theme -->
		<div id="chart_toolbar_theme">
			<div class="chart_toolbar_label chart_str_theme_cap"></div>
			<a name="dark" class="chart_icon chart_icon_theme_dark"></a>
			<a name="light" class="chart_icon chart_icon_theme_light"></a>
		</div>
		<!-- Settings -->
		<div class="chart_dropdown" id="chart_dropdown_settings">
			<div class="chart_dropdown_t">
				<a class="chart_str_settings">SETTINGS</a>
			</div>
			<div class="chart_dropdown_data">
				<table>
					<tr id="chart_select_main_indicator">
						<td class="chart_str_main_indicator">Main Indicator</td>
						<td>
							<ul>
								<li>
									<a name="MA">MA</a>
								</li>
								<li>
									<a name="EMA">EMA</a>
								</li>
								<li>
									<a name="BOLL">BOLL</a>
								</li>
								<li>
									<a name="SAR">SAR</a>
								</li>
								<li>
									<a class="chart_str_none" name="NONE"></a>
								</li>
							</ul>
						</td>
					</tr>
					<tr id="chart_select_chart_style">
						<td class="chart_str_chart_style">Chart Style</td>
						<td>
							<ul>
								<li>
									<a>CandleStick</a>
								</li>
								<li>
									<a>CandleStickHLC</a>
								</li>
								<li>
									<a>OHLC</a>
								</li>
							</ul>
						</td>
					</tr>
					<tr id="chart_select_theme">
						<td class="chart_str_theme">Theme</td>
						<td>
							<ul>
								<li>
									<a name="dark" class="chart_icon chart_icon_theme_dark"></a>
								</li>
								<li>
									<a name="light" class="chart_icon chart_icon_theme_light"></a>
								</li>
							</ul>
						</td>
					</tr>
					<tr id="chart_enable_tools">
						<td class="chart_str_tools">Tools</td>
						<td>
							<ul>
								<li>
									<a name="on" class="chart_str_on"></a>
								</li>
								<li>
									<a name="off" class="chart_str_off"></a>
								</li>
							</ul>
						</td>
					</tr>
					<tr id="chart_enable_indicator">
						<td class="chart_str_indicator">Indicator</td>
						<td>
							<ul>
								<li>
									<a name="on" class="chart_str_on"></a>
								</li>
								<li>
									<a name="off" class="chart_str_off"></a>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<ul>
								<li>
									<a id="chart_btn_parameter_settings" class="chart_str_indicator_parameters">Indicator Parameters</a>
								</li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="chart_updated_time">
			<span class="chart_str_updated"></span>
			<span id="chart_updated_time_text">0s</span>
			<span class="chart_str_ago"></span>
		</div>
	</div>
	<!-- ToolPanel -->
	<div id="chart_toolpanel">
		<div class="chart_toolpanel_separator"></div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_Cursor" name="Cursor"></div>
			<div class="chart_toolpanel_tip chart_str_cursor"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_CrossCursor" name="CrossCursor"></div>
			<div class="chart_toolpanel_tip chart_str_cross_cursor"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_SegLine" name="SegLine"></div>
			<div class="chart_toolpanel_tip chart_str_seg_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_StraightLine" name="StraightLine"></div>
			<div class="chart_toolpanel_tip chart_str_straight_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_RayLine" name="RayLine"></div>
			<div class="chart_toolpanel_tip chart_str_ray_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_ArrowLine" name="ArrowLine"></div>
			<div class="chart_toolpanel_tip chart_str_arrow_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_HoriSegLine" name="HoriSegLine"></div>
			<div class="chart_toolpanel_tip chart_str_horz_seg_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_HoriStraightLine" name="HoriStraightLine"></div>
			<div class="chart_toolpanel_tip chart_str_horz_straight_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_HoriRayLine" name="HoriRayLine"></div>
			<div class="chart_toolpanel_tip chart_str_horz_ray_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_VertiStraightLine" name="VertiStraightLine"></div>
			<div class="chart_toolpanel_tip chart_str_vert_straight_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_PriceLine" name="PriceLine"></div>
			<div class="chart_toolpanel_tip chart_str_price_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_TriParallelLine" name="TriParallelLine"></div>
			<div class="chart_toolpanel_tip chart_str_tri_parallel_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_BiParallelLine" name="BiParallelLine"></div>
			<div class="chart_toolpanel_tip chart_str_bi_parallel_line"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_BiParallelRayLine" name="BiParallelRayLine"></div>
			<div class="chart_toolpanel_tip chart_str_bi_parallel_ray"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_DrawFibRetrace" name="DrawFibRetrace"></div>
			<div class="chart_toolpanel_tip chart_str_fib_retrace"></div>
		</div>
		<div class="chart_toolpanel_button">
			<div class="chart_toolpanel_icon" id="chart_DrawFibFans" name="DrawFibFans"></div>
			<div class="chart_toolpanel_tip chart_str_fib_fans"></div>
		</div>
	</div>
	<!-- Canvas Group -->
	<div id="chart_canvasGroup">
		<canvas class="chart_canvas" id="chart_mainCanvas"></canvas>
		<canvas class="chart_canvas" id="chart_overlayCanvas"></canvas>
	</div>
	<!-- TabBar -->
	<div id="chart_tabbar">
		<ul>
			<li>
				<a name="MACD">MACD</a>
			</li>
			<li>
				<a name="KDJ">KDJ</a>
			</li>
			<li>
				<a name="StochRSI">StochRSI</a>
			</li>
			<li>
				<a name="RSI">RSI</a>
			</li>
			<li>
				<a name="DMI">DMI</a>
			</li>
			<li>
				<a name="OBV">OBV</a>
			</li>
			<li>
				<a name="BOLL">BOLL</a>
			</li>
			<li>
				<a name="SAR">SAR</a>
			</li>
			<li>
				<a name="DMA">DMA</a>
			</li>
			<li>
				<a name="TRIX">TRIX</a>
			</li>
			<li>
				<a name="BRAR">BRAR</a>
			</li>
			<li>
				<a name="VR">VR</a>
			</li>
			<li>
				<a name="EMV">EMV</a>
			</li>
			<li>
				<a name="WR">WR</a>
			</li>
			<li>
				<a name="ROC">ROC</a>
			</li>
			<li>
				<a name="MTM">MTM</a>
			</li>
			<li>
				<a name="PSY">PSY</a>
			</li>
		</ul>
	</div>
	<!-- Parameter Settings -->
	<div id="chart_parameter_settings">
		<h2 class="chart_str_indicator_parameters"></h2>
		<table>
			<tr>
				<th>MA</th>
				<td>
					<input name="MA" />
					<input name="MA" />
					<input name="MA" />
					<input name="MA" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>DMA</th>
				<td>
					<input name="DMA" />
					<input name="DMA" />
					<input name="DMA" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>EMA</th>
				<td>
					<input name="EMA" />
					<input name="EMA" />
					<input name="EMA" />
					<input name="EMA" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>TRIX</th>
				<td>
					<input name="TRIX" />
					<input name="TRIX" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>VOLUME</th>
				<td>
					<input name="VOLUME" />
					<input name="VOLUME" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>BRAR</th>
				<td>
					<input name="BRAR" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>MACD</th>
				<td>
					<input name="MACD" />
					<input name="MACD" />
					<input name="MACD" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>VR</th>
				<td>
					<input name="VR" />
					<input name="VR" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>KDJ</th>
				<td>
					<input name="KDJ" />
					<input name="KDJ" />
					<input name="KDJ" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>EMV</th>
				<td>
					<input name="EMV" />
					<input name="EMV" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>StochRSI</th>
				<td>
					<input name="StochRSI" />
					<input name="StochRSI" />
					<input name="StochRSI" />
					<input name="StochRSI" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>WR</th>
				<td>
					<input name="WR" />
					<input name="WR" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>RSI</th>
				<td>
					<input name="RSI" />
					<input name="RSI" />
					<input name="RSI" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>ROC</th>
				<td>
					<input name="ROC" />
					<input name="ROC" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>DMI</th>
				<td>
					<input name="DMI" />
					<input name="DMI" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>MTM</th>
				<td>
					<input name="MTM" />
					<input name="MTM" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>OBV</th>
				<td>
					<input name="OBV" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
				<th>PSY</th>
				<td>
					<input name="PSY" />
					<input name="PSY" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
			<tr>
				<th>BOLL</th>
				<td>
					<input name="BOLL" />
				</td>
				<td>
					<button class="chart_str_default">default</button>
				</td>
			</tr>
		</table>
		<div id="close_settings">
			<a class="chart_str_close"></a>
		</div>
	</div>
	<!-- Loading -->
	<div id="chart_loading" class="chart_str_loading"></div>
</div>
<!-- End Of ChartContainer -->
<div style="display: none">
	<form style="display: inline" id="chart_input_interface">
		<input style="display: inline" type="text" id="chart_input_interface_text">
		<input style="display: inline" type="submit" id="chart_input_interface_submit">
	</form>
	<form style="display: inline" id="chart_output_interface">
		<input style="display: inline" type="text" id="chart_output_interface_text">
		<input style="display: inline" type="submit" id="chart_output_interface_submit">
	</form>
</div>
<div style="display: none" id="chart_language_switch_tmp">
	<span name="chart_str_btc_this_week" zh_tw="當周 BTC" zh_cn="当周 BTC" en_us="BTC" />
	<span name="chart_str_btc_next_week" zh_tw="次周 BTC" zh_cn="次周 BTC" en_us="BTC" />
	<span name="chart_str_btc_month" zh_tw="全月 BTC" zh_cn="全月 BTC" en_us="BTC" />
	<span name="chart_str_btc_quarter" zh_tw="季度 BTC" zh_cn="季度 BTC" en_us="BTC" />
	<span name="chart_str_ltc_this_week" zh_tw="當周 LTC" zh_cn="当周 LTC" en_us="LTC" />
	<span name="chart_str_ltc_next_week" zh_tw="次周 LTC" zh_cn="次周 LTC" en_us="LTC" />
	<span name="chart_str_ltc_month" zh_tw="全月 LTC" zh_cn="全月 LTC" en_us="LTC" />
	<span name="chart_str_ltc_quarter" zh_tw="季度 LTC" zh_cn="季度 LTC" en_us="LTC" />
	<span name="chart_str_period" zh_tw="週期" zh_cn="周期" en_us="TIME" />
	<span name="chart_str_period_line" zh_tw="分時" zh_cn="分时" en_us="Line" />
	<span name="chart_str_period_1m" zh_tw="1分钟" zh_cn="1分钟" en_us="1m" />
	<span name="chart_str_period_3m" zh_tw="3分钟" zh_cn="3分钟" en_us="3m" />
	<span name="chart_str_period_5m" zh_tw="5分钟" zh_cn="5分钟" en_us="5m" />
	<span name="chart_str_period_15m" zh_tw="15分钟" zh_cn="15分钟" en_us="15m" />
	<span name="chart_str_period_30m" zh_tw="30分钟" zh_cn="30分钟" en_us="30m" />
	<span name="chart_str_period_1h" zh_tw="1小時" zh_cn="1小时" en_us="1h" />
	<span name="chart_str_period_2h" zh_tw="2小時" zh_cn="2小时" en_us="2h" />
	<span name="chart_str_period_4h" zh_tw="4小時" zh_cn="4小时" en_us="4h" />
	<span name="chart_str_period_6h" zh_tw="6小時" zh_cn="6小时" en_us="6h" />
	<span name="chart_str_period_12h" zh_tw="12小時" zh_cn="12小时" en_us="12h" />
	<span name="chart_str_period_1d" zh_tw="日線" zh_cn="日线" en_us="1d" />
	<span name="chart_str_period_1w" zh_tw="周線" zh_cn="周线" en_us="1w" />
	<span name="chart_str_settings" zh_tw="更多" zh_cn="更多" en_us="MORE" />
	<span name="chart_setting_main_indicator" zh_tw="均線設置" zh_cn="均线设置" en_us="Main Indicator" />
	<span name="chart_setting_main_indicator_none" zh_tw="關閉均線" zh_cn="关闭均线" en_us="None" />
	<span name="chart_setting_indicator_parameters" zh_tw="指標參數設置" zh_cn="指标参数设置" en_us="Indicator Parameters" />
	<span name="chart_str_chart_style" zh_tw="主圖樣式" zh_cn="主图样式" en_us="Chart Style" />
	<span name="chart_str_main_indicator" zh_tw="主指標" zh_cn="主指标" en_us="Main Indicator" />
	<span name="chart_str_indicator" zh_tw="技術指標" zh_cn="技术指标" en_us="Indicator" />
	<span name="chart_str_indicator_cap" zh_tw="技術指標" zh_cn="技术指标" en_us="INDICATOR" />
	<span name="chart_str_tools" zh_tw="畫線工具" zh_cn="画线工具" en_us="Tools" />
	<span name="chart_str_tools_cap" zh_tw="畫線工具" zh_cn="画线工具" en_us="TOOLS" />
	<span name="chart_str_theme" zh_tw="主題選擇" zh_cn="主题选择" en_us="Theme" />
	<span name="chart_str_theme_cap" zh_tw="主題選擇" zh_cn="主题选择" en_us="THEME" />
	<span name="chart_str_none" zh_tw="關閉" zh_cn="关闭" en_us="None" />
	<span name="chart_str_theme_dark" zh_tw="深色主題" zh_cn="深色主题" en_us="Dark" />
	<span name="chart_str_theme_light" zh_tw="淺色主題" zh_cn="浅色主题" en_us="Light" />
	<span name="chart_str_on" zh_tw="開啟" zh_cn="开启" en_us="On" />
	<span name="chart_str_off" zh_tw="關閉" zh_cn="关闭" en_us="Off" />
	<span name="chart_str_close" zh_tw="關閉" zh_cn="关闭" en_us="CLOSE" />
	<span name="chart_str_default" zh_tw="默認值" zh_cn="默认值" en_us="default" />
	<span name="chart_str_loading" zh_tw="正在讀取數據..." zh_cn="正在读取数据..." en_us="Loading..." />
	<span name="chart_str_indicator_parameters" zh_tw="指標參數設置" zh_cn="指标参数设置" en_us="Indicator Parameters" />
	<span name="chart_str_cursor" zh_tw="光標" zh_cn="光标" en_us="Cursor" />
	<span name="chart_str_cross_cursor" zh_tw="十字光標" zh_cn="十字光标" en_us="Cross Cursor" />
	<span name="chart_str_seg_line" zh_tw="線段" zh_cn="线段" en_us="Trend Line" />
	<span name="chart_str_straight_line" zh_tw="直線" zh_cn="直线" en_us="Extended" />
	<span name="chart_str_ray_line" zh_tw="射線" zh_cn="射线" en_us="Ray" />
	<span name="chart_str_arrow_line" zh_tw="箭頭" zh_cn="箭头" en_us="Arrow" />
	<span name="chart_str_horz_seg_line" zh_tw="水平線段" zh_cn="水平线段" en_us="Horizontal Line" />
	<span name="chart_str_horz_straight_line" zh_tw="水平直線" zh_cn="水平直线" en_us="Horizontal Extended" />
	<span name="chart_str_horz_ray_line" zh_tw="水平射線" zh_cn="水平射线" en_us="Horizontal Ray" />
	<span name="chart_str_vert_straight_line" zh_tw="垂直直線" zh_cn="垂直直线" en_us="Vertical Extended" />
	<span name="chart_str_price_line" zh_tw="價格線" zh_cn="价格线" en_us="Price Line" />
	<span name="chart_str_tri_parallel_line" zh_tw="價格通道線" zh_cn="价格通道线" en_us="Parallel Channel" />
	<span name="chart_str_bi_parallel_line" zh_tw="平行直線" zh_cn="平行直线" en_us="Parallel Lines" />
	<span name="chart_str_bi_parallel_ray" zh_tw="平行射線" zh_cn="平行射线" en_us="Parallel Rays" />
	<span name="chart_str_fib_retrace" zh_tw="斐波納契回調" zh_cn="斐波纳契回调" en_us="Fibonacci Retracements" />
	<span name="chart_str_fib_fans" zh_tw="斐波納契扇形" zh_cn="斐波纳契扇形" en_us="Fibonacci Fans" />
	<span name="chart_str_updated" zh_tw="更新於" zh_cn="更新于" en_us="Updated" />
	<span name="chart_str_ago" zh_tw="前" zh_cn="前" en_us="ago" />
</div>
</body>
<input type="hidden" id="coinId" value="${ftrademapping.fid}"/>
<input type="hidden" id="coinName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}"/>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/fullkline/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/fullkline/fullKilne-min.js?t=2016-12-16"></script>

<script type="text/javascript">
    _set_current_language("en_us");
    _setCaptureMouseWheelDirectly(false);
    _set_current_coinshortName($('#coinName').val());
    _set_current_coin($('#coinId').val());
    _set_current_contract_unit($('#coinId').val());
    onPushingStarted();
</script>
</html>
