$(function() {
	var myChart = echarts.init(document.getElementById("chartMain"));
	var myChartContainer;
	var stompClient = null;

	/**
	 * 建立连接
	 */
	function connect() {
		var socket = new SockJS('/websocket');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			var sessionId = socket._transport.url.split("/")[5];

			stompClient.subscribe('/gateway/historicalBars-' + sessionId, function(data) {
				var newData = jQuery.parseJSON(data.body);// 解析数据
				var history = newData.MarketHistory;
				var data = new Array();
				for (var i = 0; i < history.length; i++) {
					data[i] = new Array(i);
					for (var j = 0; j < 5; j++) {
						data[i][0] = history[i]['startTime'];
						data[i][1] = history[i]['open'];
						data[i][2] = history[i]['close'];
						data[i][3] = history[i]['high'];
						data[i][4] = history[i]['low'];
					}
				}
				var data0 = splitData(data);
				option = {// echarts配置
					title : {
						text : '',
						left : 0
					},
					tooltip : {
						trigger : 'axis',
						axisPointer : {
							type : 'line'
						}
					},
					legend : {
					    data : [ '日K']
					},
					grid : {
						left : '5%',
						right : '5%',
						top : '10%',
						bottom : '25%'
					},
					xAxis : {
						type : 'category',
						data : data0.categoryData,
						scale : true,
						boundaryGap : false,
						axisLine : {
							onZero : false
						},
						splitLine : {
							show : false
						},
						splitNumber : 20,
						min : 'dataMin',
						max : 'dataMax'
					},
					yAxis : {
						scale : true,
						splitArea : {
							show : true
						}
					},
					dataZoom : [ {
						type : 'inside',
						start : 50,
						end : 100
					}, {
						show : true,
						type : 'slider',
						y : '90%',
						start : 50,
						end : 100,
						top : 310

					} ],
					series : [ {
						name : '日K',
						type : 'candlestick',
						data : data0.values,
						itemStyle : {
							normal : {
								color : 'lightgreen',
								color0 : 'red',
								lineStyle : {
									width : 1,
									color : 'lightgreen',
									color0 : 'red'
								}
							},
							emphasis : {

							}

						},

					}
					]
				};
				myChart.setOption(option);
			});

			/**
			 * 客户端发起请求
			 */
			stompClient.send("/ws/trade/home", {}, {
				"userId" : null,
				"sessionId" : sessionId,
				"requestId" : RequestId,
				"token" : null
			});
		});

	}

	/**
	 * 拆分Data
	 */
	function splitData(rawData) {
		var categoryData = [];
		var values = []
		for (var i = 0; i < rawData.length; i++) {
			categoryData.push(rawData[i].splice(0, 1)[0]);
			values.push(rawData[i])
		}
		return {
			categoryData : categoryData,
			values : values
		};
	}

	/**
	 * timeframe切换事件
	 */
	getTimeFrame = function(time) {
		symbol = $("#currencyTextContent").html();
//		alert(symbol);
		stompClient.send("/ws/trade/home", {}, {
			"token" : null,
			"Tag" : 11,
			"RequestID" : RequestId,
			'Symbol' : symbol,
			"Timeframe" : time
		});

	}

	/**
	 * 起调方法
	 */
	connect();
	   window.onresize = function () {
    myChartContainer();
    myChart.resize();
};

})
