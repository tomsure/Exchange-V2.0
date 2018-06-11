$(function() {
	var stompClient = null;

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
				sendLoginData() //login	

			})
			stompClient.subscribe('/gateway/logout-' + sessionId, function(data) {
				var status = jQuery.parseJSON(data.body).Status

				$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')

				$('.ulList li:last').html('<a href="Register.html">  Register </a>')
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
				$('.ulList li:last').html('<a href="Register.html">  Register</a>')
				location.href = "trade.html"
			})

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