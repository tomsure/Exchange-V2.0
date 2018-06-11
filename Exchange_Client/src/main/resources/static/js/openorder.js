$(function() {

	var stompClient = null;
	var userId = null;

	function connect() {
		var socket = new SockJS('/websocket');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			var sessionId = socket._transport.url.split("/")[5];
			// 如果有userid
			if($.cookie('UserId')) {
				$('#loginLi').html('<span>' + $.cookie('email') + '</span>')
				$('#liLast').html('<span id="logout">Logout</span>')
				$('#userItem').removeClass('hide')
			}
			customValidate($('#lgPassWordForm'), {
				"lgEmail": {
					required: true,
					email: true
				},
				"lgPassword": {
					required: true
				}
			}, {}, function() {
				stompClient.subscribe('/gateway/login-' + sessionId, function(data) {
					$.cookie('email', $('#lgEmail').val(), {
						expires: 7,
						path: '/'
					})
					$.cookie('UserId', jQuery.parseJSON(data.body).UserID, {
						path: '/'
					})
					var status = jQuery.parseJSON(data.body).Status
					if(status == 0) {

						$('#myModal').modal('hide')

						$('#loginLi').html('<span>' + $.cookie('email') + '</span>')

						$('#liLast').html('<span id="logout">Logout</span>')

						resetStyle()
						$('#userItem').removeClass('hide')
					} else if(status == -1) {
						alert('Login Error!')
					}
				})
				sendLoginData() // login

			})
			stompClient.subscribe('/gateway/logout-' + sessionId, function(data) {
				var status = jQuery.parseJSON(data.body).Status

				$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')

				$('.ulList li:last').html('<a href="Register.html">Register  </a>')
				$('#userItem').addClass('hide')
			})
			$('#liLast').click(function() {
				sendLogoutData() // logout
				$.removeCookie('UserId', {
					path: '/'
				})
				$.removeCookie('email', {
					path: '/'
				})
				$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
				$('.ulList li:last').html('<a href="Register.html">  Register  </a>')
				location.href = "trade.html"
			})

			stompClient.subscribe('/gateway/userPendingOrders-' + sessionId, function(data) {
				// alert(typeof data.body)
				var pendingOrder = jQuery.parseJSON(data.body);
				$.each(pendingOrder.SearchTradings, function(i) {
					if(pendingOrder.SearchTradings[i].Status == 1 || pendingOrder.SearchTradings[i].Status == 2) {
						pendingOrder.SearchTradings[i].Status = 'Pending'
					}
					pendingOrder.SearchTradings[i].Time = timestampToTime(pendingOrder.SearchTradings[i].Time)
					//               pendingOrder.SearchTradings[i].Total=pendingOrder.SearchTradings[i].Price*pendingOrder.SearchTradings[i].Amount
					pendingOrder.SearchTradings[i].Total = accMul(pendingOrder.SearchTradings[i].Price, pendingOrder.SearchTradings[i].Amount)

					if(pendingOrder.SearchTradings[i].Status == 0) {
						pendingOrder.SearchTradings[i].Status = 'Completed'
					} else if(pendingOrder.SearchTradings[i].Status == 3) {
						pendingOrder.SearchTradings[i].Status = 'Canceled'
					}
					if(pendingOrder.SearchTradings[i].OrderType == 1) {
						pendingOrder.SearchTradings[i].OrderType = 'Market Order'
					} else if(pendingOrder.SearchTradings[i].OrderType == 2) {
						pendingOrder.SearchTradings[i].OrderType = 'Limit Order'
					}
					if(pendingOrder.SearchTradings[i].TransType == 1) {
						pendingOrder.SearchTradings[i].TransType = 'Buy'
					} else if(pendingOrder.SearchTradings[i].TransType == 2) {
						pendingOrder.SearchTradings[i].TransType = 'Sell'
					}

				})
				$('#orderTable').bootstrapTable({
					// search:true,
					searchOnEnterKey: true,
					rowStyle: function(row, index) {
						var style = {};
						style = {
							css: {
								'text-align': 'center',

							}
						};
						return style;
					},
					pageSize: 10,
					columns: [{
							field: 'OrderID',
							title: 'Order ID'
						}, {
							field: 'Time',
							title: 'Time'
						}, {
							field: 'Symbol',
							title: 'Symbol'
						}, {
							field: 'OrderType',
							title: 'Order Type'
						}, {
							field: 'TransType',
							title: 'Trans Type'
						}, {
							field: 'Price',
							title: 'Price'
						}, {
							field: 'Amount',
							title: 'Amount'
						},
						{
							field: 'Total',
							title: 'Total'
						},

						{
							field: 'Status',
							title: 'Status'
						},
						//                      {
						//                          field: 'button',
						//                          title: "<button class='buttonColor  cancelAll' data-toggle='modal' data-target='#cancelAllModal'>Cancel All</button>"
						//                      }

					],
					data: pendingOrder.SearchTradings,
					onClickCell: function(field, value, row, $element) {

						if($element.text() == 'Cancel') {

							// alert($element.parent().children('td:nth-child(2)').text())
							// //获取点击按钮当前行的第一个子元素
							$('#cancelModal').modal('show')
							$('#cancelBtn').click(function() {
								var orderID = $element.parent().children('td:nth-child(1)').text();
								sendDelData(orderID);
								stompClient.subscribe('/gateway/cancelOrder-' + sessionId, function(data) {
									var status = jQuery.parseJSON(data.body).Status
									alert(status)
									if(status == 0) {
										alert('delete success')
										$('#loginHistoryTable').bootstrapTable('refresh');
										$('#cancelModal').modal('hide')
										return false
									} else if(status == -1) {
										alert('Delete Error')
										return false
									}
								})
							})

						}
					}
				});
				$('#orderTable').bootstrapTable('load', pendingOrder.SearchTradings);
				//              $('#orderTable tbody tr').children("td:last-child").html('<button   class="deleteBtn" >Cancel</button>')

			});

			stompClient.subscribe('/gateway/getAllTokens-' + sessionId, function(data) {
				var response = jQuery.parseJSON(data.body);
				$.each(response.SymbolList, function(i, el) {
$('#dropdownMenuList').append('<li><a href="#">' + response.SymbolList[i].Symbol + '</a></li>')
				})
				$('#dropdownMenuList').prepend('<li><a>All</a></li>')
				$('.coinListNav .dropdown .dropdown-menu li a').each(function(i, el) {
					$(el).click(function() {
					$('#dropdownMenu2').html($(this).text() + "<b class='caret caret pull-right'  ></b>")
					                 })
					//  	
				})
			})

			init()

			/**
			 * 时间段选择
			 */
			SelectTimePeriod();

			/**
			 * 搜索按钮
			 */
			$('#searchBtn').click(function() {
				// 格式化时间
				var beginDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				//            	alert(beginDate);
				(beginDate == "") ? beginTime = 0: beginTime = Date.parse(new Date(beginDate + " 00:00:00")) / 1000;
				(endDate == "") ? endTime = 0: endTime = Date.parse(new Date(endDate + " 23:59:59")) / 1000;
				//发起請求
				sendSearchData(beginTime, endTime)
			})

			function init() {
				stompClient.send("/ws/token/reportService", {}, JSON.stringify({ // 用来获取比币种列表
					'RequestID': 'testtesttest',
					'Tag': 24613,
				}));
				stompClient.send("/ws/token/reportService", {}, JSON.stringify({ // 打开页面默认发送的数据
					"Tag": 24579,
					"UserID": parseInt($.cookie('UserId')),
					"RequestID": RequestId,
					"Symbol": 'All',
					"Ordertype": 0,
					"BeginTime": 0,
					"EndTime": 0
				}));
			}

			function sendDelData(orderID) {
				stompClient.send("/ws/token/cancelPendingOrder", {}, JSON.stringify({ // 删除
					"Tag": 16385,
					"UserID": parseInt($.cookie('UserId')),
					"OrderID": orderID,
					"RequestID": RequestId
				}))
			}

			/**
			 * pending order条件查询
			 */
			function sendSearchData(beginTime, endTime) {
				stompClient.send("/ws/token/reportService", {}, JSON.stringify({
					"Tag": 24579,
					"UserID": parseInt($.cookie('UserId')),
					"Symbol": $('#dropdownMenu2').text(),
					"BeginTime": beginTime,
					"EndTime": endTime,
					//                    "limitedays": limitedays[0],
					"RequestID": RequestId
				}))
			}

			function sendLoginData() {
				stompClient.send("/ws/user/login", {}, JSON.stringify({ // login
					"Tag": 8193,
					"username": $.trim($('#lgEmail').val()),
					"loginpassword": $.trim($('#lgPassword').val()),
					"srcip": "192.168.0.1",
					"RequestID": RequestId
				}));

			}

			function sendLogoutData() {
				stompClient.send("/ws/user/logout", {}, JSON.stringify({ // logout
					"Tag": 131089,
					"srcip": "192.168.0.1",
					"RequestID": RequestId,
					"UserID": parseInt($.cookie('UserId'))
				}));
			}
		})
	}

	connect()

})