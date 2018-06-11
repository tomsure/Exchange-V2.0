var stompClient = null;
var userId = '';
var email = "";

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		var sessionId = socket._transport.url.split("/")[5];
		//               如果有userid
		if($.cookie('UserId')) {
			$('#loginLi').html('<span>' + $.cookie('email') + '</span>')
			$('#liLast').html('<span id="logout">Logout</span>')
			$('#userItem').removeClass('hide')
		}
		stompClient.subscribe('/gateway/kyc-' + sessionId, function(data) {
			var status = jQuery.parseJSON(data.body).Status

			if(status == 0) {
				alert('Authentication Successfully !')
				location.href = 'security.html'
			} else if(status == -1) {
				alert('Authentication Error !')
			}
		})
		stompClient.subscribe('/gateway/userKycInfo-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			$('#frontImg').attr("src", response.Image1);
			$('#backImg').attr("src", response.Image2);
			$('#handImg').attr("src", response.Image3);
		})
		init();

		//login
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
				//挂单

			})
			sendLoginData() //login	
		})

		//louout
		$('#liLast').click(function() { //logout

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
			location.href = 'trade.html'

		})

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

		function sendIdentity2Data() {

			stompClient.send('/ws/security/identity2', {}, JSON.stringify({
				'UserID': parseInt($.cookie('UserId')),
				"SessionID": sessionId,
				'img1': $('#frontImg').attr('src'),
				'img2': $('#backImg').attr('src'),
				'img3': $('#handImg').attr('src')
			}))
		}

		$('#identy2SubmitBtn').click(function() {
			//         alert($('#argeeMark').attr('checked'))
			if($('#img1').attr('data-upload') != 'true' && $('#img2').attr('data-upload') != 'true' && $('#img2').attr('data-upload') != 'true') {
				alert('Please upload file')
			} else if($('#argeeMark').is(':checked') == false) {
				alert('Please click the check box!')
			} else {
				sendIdentity2Data()
			}

		})

		frontImg = document.getElementById("img1")

		$(frontImg).change(function() {
			var ftp = frontImg.files[0]
			$('#frontImg').attr('src', window.URL.createObjectURL(ftp))
			alert('Please click the "upload" button to upload the file.')
		})

		backImg = document.getElementById("img2")

		$(backImg).change(function() {
			var ftp1 = backImg.files[0]
			$('#backImg').attr('src', window.URL.createObjectURL(ftp1))
			alert('Please click the "upload" button to upload the file.')
		})

		handImg = document.getElementById("img3")

		$(handImg).change(function() {
			var ftp2 = handImg.files[0]
			$('#handImg').attr('src', window.URL.createObjectURL(ftp2))
			alert('Please click the "upload" button to upload the file.')
		})

	});

}

function init() {
	stompClient.send("/ws/security/identity", {}, JSON.stringify({
		'Tag': 155648,
		'UserID': parseInt($.cookie('UserId')),
		'RequestID': 'testtest'
	}));
}

function upload(uploadForm) {

	var formData = new FormData(uploadForm[0]);
	$.ajax({
		url: "/upload/kyc",
		type: 'POST',
		data: formData,
		async: false,
		processData: false,
		contentType: false,
		success: function(data) {
			uploadForm.parent().parent().find('div:nth-child(3)').find('img').attr('src', data)
			uploadForm.find('input[type="file"]').attr('data-upload', 'true')
		}
	});
}

$('#uploadBtn1').click(function(e) {
	if($('#img1').val() == '') {
		alert('Please select file')
		return false
	} else {
		upload($('#uploadForm1'))
	}

})

$('#uploadBtn2').click(function(e) {

	if($('#img2').val() == '') {
		alert('Please select file')
		return false
	} else {
		upload($('#uploadForm2'))
	}
})

$('#uploadBtn3').click(function(e) {
	if($('#img3').val() == '') {
		alert('Please select file')
		return false
	} else {
		upload($('#uploadForm3'))
	}

})

$('#argeeBtn').click(function() {

	$('#.icon.icon-argee:before').css('color', 'red')

})

$(function() {

	connect();
	//	argeeBtn
});