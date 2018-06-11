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
   						location.href = 'trade.html'
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
   				$('.ulList li:last').html('<a href="Register.html">Register</a>')
   				location.href = "trade.html"

   			})

   			$("#regForm").validate({
   				rules: {
   					firstname1: "required",
   					emailVerificationCode: "required",
   					verificationCode: {
   						required: true,
   						minlength: 2,

   					},
   					password1: {
   						required: true,
   						minlength: 5
   					},
   					confirm_password1: {
   						required: true,
   						minlength: 5,
   						equalTo: "#password1"
   					},
   					email1: {
   						required: true,
   						email: true
   					},
   					agree1: "required"
   				},
   				messages: {
   					firstname1: "Please enter your firstname",
   					emailVerificationCode: "Please enter email Verification Code",
   					verificationCode: {
   						required: "Please enter Verification Code",
   						minlength: "Verification Code must consist of at least 2 characters"
   					},
   					password1: {
   						required: "Please provide a password",
   						minlength: "Your password must be at least 5 characters long"
   					},
   					confirm_password1: {
   						required: "Please provide a password",
   						minlength: "Your password must be at least 5 characters long",
   						equalTo: "Please enter the same password as above"
   					},
   					email1: "Please enter a valid email address",
   					agree1: "Please accept our policy"
   				},
   				errorElement: "em",
   				errorPlacement: function(error, element) {

   					error.addClass("help-block");

   					element.parents(".col-sm-6").addClass("has-feedback");

   					if(element.prop("type") === "checkbox") {
   						error.insertAfter(element.parent("label"));
   					} else {
   						error.insertAfter(element);
   					}

   					if(!element.next("span")[0]) {
   						$("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
   					}
   				},
   				success: function(label, element) {

   					if(!$(element).next("span")[0]) {
   						$("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
   					}
   				},
   				highlight: function(element, errorClass, validClass) {
   					$(element).css('margin-bottom', 0)
   					$(element).parents(".col-sm-6").addClass("has-error").removeClass("has-success");
   					$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
   					$('.imgCode').css('bottom', '64px')

   				},
   				unhighlight: function(element, errorClass, validClass) {
   					$(element).css('margin-bottom', '20px')
   					$(element).parents(".col-sm-6").addClass("has-success").removeClass("has-error");
   					$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
   					$('.imgCode').css('bottom', '50px')

   				},
   				submitHandler: function() {

   					sendRegisterData()
   					stompClient.subscribe('/gateway/registry-' + sessionId, function(data) {

   						var status = jQuery.parseJSON(data.body).Status
   						//            	  
   						if(status == 0) {
   							alert("Register Successfully");
   							$('#myModal').modal('show')
   							$('#regForm')[0].reset()
   							resetStyle()
   						} else if(status == -100) {
   							alert("Email Code Error!");
   						} else if(status == -1) {
   							alert('Register Error!')
   						}
   					});
   				}
   			});
   			//          stompClient.subscribe('/gateway/registry-' + sessionId, function(data) {
   			//          	 
   			//            	var status = jQuery.parseJSON(data.body).Status
   			////            	  
   			//            	if(status == 0){
   			//            		alert("Register Successfully");
   			//            		  $('#myModal').modal('show')
   			//            		  $('#regForm')[0].reset()
   			//            		  resetStyle()
   			//            	}else if(status == -100){
   			//            		alert("Email Code Error!");
   			//            	}
   			//          });
   			$('#emailCodeBtn').click(function() {
   				if($('#email1').val() == '') {
   					alert('Please enter your email address')
   				} else {
   					sendSomething($(this))
   					sendEmail()
   				}

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

   			function sendEmail() {
   				stompClient.send("/ws/message/sendEmail", {}, JSON.stringify({
   					'RequestID': RequestId,
   					'Email': $('#email1').val(),
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

   			function sendRegisterData() {
   				$.get("/register/verifyCode?vcode=" + $('#verificationCode').val(), function(data) {

   					if(data == true) {
   						stompClient.send("/ws/user/registry", {}, JSON.stringify({
   							"Tag": 4097,
   							"Email": $('#email1').val(),
   							"Password": $('#password1').val(),
   							"EmailCode": $('#emailVerificationCode').val(),
   							"Code": $('#verificationCode').val(),
   							"RequestID": RequestId
   						}));
   					} else if(data == false) {
   						alert("Image Code Error!");
   					}
   				});
   			}

   		})
   	}

   	connect()
   })