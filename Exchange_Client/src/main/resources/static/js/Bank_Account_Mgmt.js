$(function() {

	var stompClient = null;
	var userId = null;

	function connect() {
		var socket = new SockJS('/websocket');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			var sessionId = socket._transport.url.split("/")[5];
			//               如果有userid
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
				sendLoginData() //login	

			})
			stompClient.subscribe('/gateway/logout-' + sessionId, function(data) {
				var status = jQuery.parseJSON(data.body).Status

				$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')

				$('.ulList li:last').html('<a href="Register.html">  Register  </a>')
				$('#userItem').addClass('hide')
			})
			$('#liLast').click(function() {
				sendLogoutData() //logout
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

			stompClient.subscribe('/gateway/bankAccount-' + sessionId, function(data) {
				var bankData = jQuery.parseJSON(data.body)

				$('#accountTable').bootstrapTable({
					rowStyle: function(row, index) {
						var style = {};
						style = {
							css: {
								'text-align': 'center'
							}
						};
						return style;
					},
					striped: true,
					pagination: 'true',
					pageSize: 10,
					columns: [{
							field: 'BankName',
							title: 'Bank Name'
						},
						{
							field: 'CardNumber',
							title: 'Card Number'
						},
						{
							field: 'OpenBank',
							title: 'Open Bank'
						},
						{
							field: 'UserName',
							title: 'User Name'
						},
						{
							field: 'button',
							title: ''
						}
					],
					data: bankData.BankList,
					onPageChange: function() {
						if(bankData.BankList.length > 0) {
							$('#accountTable tbody tr').children("td:last-child").html('<button >Delete</button>')
						}

					},
					onClickCell: function(field, value, row, $element) {
						if($element.text() == 'Delete') {
							//alert($element.parent().children('td:nth-child(2)').text())  //获取点击按钮当前行的第一个子元素
							$('#deleteModal').modal('show')
							$('#deleteBtn').click(function() {
								stompClient.subscribe('/gateway/deleteBankAccount-' + sessionId, function(data) {
									var status = jQuery.parseJSON(data.body).Status
									if(status == 0) {
										$('#deleteModal').modal('hide')
										alert('Delete Successfully')
										window.location.reload()

										
										return false
									} else if(status == -1) {
										$('#deleteModal').modal('hide')
										alert('Delete Error')
										
										return false
									}
								})
								stompClient.send("/ws/assets/deleteBankAccount", {}, JSON.stringify({ //删除银行账户
									"Tag": 131072,
									"UserID": parseInt($.cookie('UserId')),
									"BankAccount": $element.parent().children('td:nth-child(2)').text(),
									"RequestID": RequestId
								}))
							})
						}
					}
				})

				stompClient.subscribe('/gateway/addBankAccount-' + sessionId, function(data) {
					var data = jQuery.parseJSON(data.body)
					if(data.Status == 0) {
						alert('Add Account Successfully!')

						location.reload()

						$('#accountTable').bootstrapTable('load', bankData.BankList);
					} else if(data.Status == -1) {
						alert('Add Account Error!')
					}
				})

				if(bankData.BankList.length > 0) {
					$('#accountTable tbody tr').children("td:last-child").html('<button >Delete</button>')
				}
			});

			$('#AddAccountBtn').click(function() {
				$('#AddAccountModal').modal('show')

			})

			addAccountForm(function() {
				stompClient.send("/ws/assets/addBankAccount", {}, JSON.stringify({
					"Tag": 102400,
					"UserID": parseInt($.cookie('UserId')),
					"HolderName": $('#holderName').val(),
					"BankName": $('#bankName').val(),
					"BankAccout": $('#bankAccout').val(),
					"OpenBranchBank": $('#openBranchBank').val(),
					"SessionId": sessionId,
					"RequestID": RequestId,
					"WsId": 1

				}))
				init()
				$('#AddAccountModal').modal('hide')
			})

			init()

			function init() {
				stompClient.send("/ws/assets/getBankAccount", {}, JSON.stringify({
					"Tag": 98304,
					"UserID": parseInt($.cookie('UserId')),
					"SessionID": sessionId,
					"requestId": RequestId
				}));
			}

			function sendSms() {
				stompClient.send("/ws/message/sendSms", {}, JSON.stringify({
					'Tag': 69634,
					'RequestID': RequestId,
					'UserID': parseInt($.cookie('UserId')),
					'MobilePhone': $('#changedPhoneNumber').val()
				}));
			}

			function sendLoginData() {
				stompClient.send("/ws/user/login", {}, JSON.stringify({ //login
					"Tag": 8193,
					"username": $.trim($('#lgEmail').val()),
					"loginpassword": $.trim($('#lgPassword').val()),
					"srcip": "192.168.0.1",
					"RequestID": RequestId
				}));
			}

			function sendLogoutData() {
				stompClient.send("/ws/user/logout", {}, JSON.stringify({ //logout
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