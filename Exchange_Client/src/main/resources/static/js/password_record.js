   function resetStyle() {
   	$('div.has-success ').removeClass('has-success')
   	$('span.glyphicon-ok').remove()
   }
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

   					var status = jQuery.parseJSON(data.body).Status

   					$.cookie('email', $('#lgEmail').val(), {
   						expires: 7,
   						path: '/'
   					})
   					$.cookie('UserId', jQuery.parseJSON(data.body).UserID, {
   						path: '/'
   					})
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

   			$('#regImg').click(function() { //更新图片验证码
   				$.ajax({
   					type: "get",
   					url: "/register/getVerificationCode",
   					async: true,
   					success: function() {
   						$('#regImg').attr('src', '/register/getVerificationCode')
   					}

   				});
   			})

   			publicValidate($('#resetPasswordForm'),
   				function(label, element) {

   					if(!$(element).next("span")[0]) {
   						$("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
   					}

   					if($(element).attr('id') == 'email') {
   						$('#emailCodeBtn').click(function() {
   							sendEmail()
   							sendSomething($(this))
   						})
   					}

   				},
   				function() {
   					sendResetPassword()
   					stompClient.subscribe('/gateway/forgetPwd-' + sessionId, function(d) {
   						var data = $.parseJSON(d.body)

   						if(data.Status == -100) {
   							alert('Your email address is not registered. Please enter again! ')
   						} else if(data.Status == 0) {
   							alert('Reset password successfully!')
   							$('#resetPasswordForm')[0].reset()

   							resetStyle()
   						}

   					})

   				})

   			function sendEmail() {
   				stompClient.send("/ws/message/sendEmail", {}, JSON.stringify({
   					'RequestID': RequestId,
   					'Email': $('#email').val(),
   				}));
   			}

   			function sendResetPassword() {
   				$.get("/register/verifyCode?vcode=" + $('#verificationCode').val(), function(data) {
   					//    		      console.log(data)
   					if(data == true) {
   						stompClient.send("/ws/user/resetPwd", {}, JSON.stringify({
   							"Tag": 147456,
   							"Email": $('#email').val(),
   							"EmailCode": $('#emailCode').val(),
   							"PicCode": $('#verificationCode').val(),
   							"Password": $('#confirmPassword').val(),
   							"RequestID": RequestId
   						}));
   					} else if(data == false) {
   						alert("Image Code Error!");
   					}
   				});
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