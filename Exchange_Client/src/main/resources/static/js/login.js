function resetStyle() {
	$('div.has-success input.grayBorder').css('border-color', 'gray')
	$('span.glyphicon-ok').remove()
}

function layerMsg(content) {
	layui.use('layer', function() {

		var layer = layui.layer
		layer.config({
			skin: ''
		})
		layer.msg(content, {
			time: 1000,
			offset: ['15%']
		}, function() {

		})

	})

}

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

		//如果没有userID
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
				// TODO store userId

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
					layerMsg('Login Successfully!')
					$('#userItem').removeClass('hide')
				} else if(status == -1) {
					alert('Login Error!')
					//                      
				}

			})
			sendLoginData() //login	
		})
		$('#liLast').click(function() { //logout
			console.log($.cookie('UserId'))
			sendLogoutData() //send logout Data
			$.removeCookie('UserId', {
				path: '/'
			})
			$.removeCookie('email', {
				path: '/'
			})

			$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
			$('.ulList li:last').html('<a href="Register.html">  Register  </a>')
			$('#userItem').addClass('hide')

			console.log($.cookie('UserId'))

			console.log(location.pathname)

			if(location.pathname == '/') {
				location.href = 'html/trade.html'
			} else {
				location.href = 'trade.html'
			}

		})

		init()
		//

		function init() {
			stompClient.send("/ws/token/home", {}, JSON.stringify({ //打开页面时候发送的数据

				"UserID": parseInt($.cookie('UserId')),
				"RequestID": RequestId,
				"Token": null
			}));
		}

		function sendLoginData() {
			stompClient.send("/ws/user/login", {}, JSON.stringify({ //login
				"Tag": 8193,
				"username": $.trim($('#lgEmail').val()),
				"loginpassword": $.trim($('#lgPassword').val()),
				"srcip": "192.168.0.1",
				"UserID": parseInt($.cookie('UserId')),
				"RequestID": RequestId
			}));
		}

		function sendLogoutData() {
			stompClient.send("/ws/user/logout", {}, JSON.stringify({ //logout
				"Tag": 131089,
				"UserID": parseInt($.cookie('UserId')),
				"srcip": "192.168.0.1",
				"RequestID": RequestId
			}));
		}

	});

}
connect();