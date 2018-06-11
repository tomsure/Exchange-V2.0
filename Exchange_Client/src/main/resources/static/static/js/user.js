function resetStyle() {
      $('div.has-success input.grayBorder').css('border-color', 'gray')
      $('span.glyphicon-ok').remove()
}
var stompClient = null;
var email = $.cookie('email');
$('#email').text(email)
$('#userId').text($.cookie('UserId'))
function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		  
		console.log('Connected: ' + frame);
		var sessionId = socket._transport.url.split("/")[5];
		//               如果有userid
             if($.cookie('UserId')){
             	    $('#loginLi').html('<span>' + $.cookie('email') + '</span>')
                       $('#liLast').html('<span id="logout">Logout</span>')
                       $('#userItem').removeClass('hide')
                      }
		init();
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
                	    
                      $.cookie('email',$('#lgEmail').val())
                      $.cookie('UserId',jQuery.parseJSON(data.body).UserID)
                      var userId=jQuery.parseJSON(data.body).UserID
                      
                       
                    var status = jQuery.parseJSON(data.body).Status
                    
                      if (status == 0){
                    	$('#myModal').modal('hide')
                        $('#loginLi').html('<span>' + $.cookie('email') + '</span>')
                        $('#liLast').html('<span id="logout">Logout</span>')
                        resetStyle()
                        $('#userItem').removeClass('hide')
                        $('#userId').text(userId)
                      
                        } else if (status == -1) {
                        alert('login error!')
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
           stompClient.subscribe('/gateway/userKycStatus-' + sessionId, function(data) {
               var reponse = jQuery.parseJSON(data.body);
               var verifyCount = 0;
               if(reponse.loginpassword == 1){
            	   $('#loginPwdVerified').addClass('icon icon-makesure');
            	   verifyCount = verifyCount + 1;
               }else{
            	   $('#loginPwdVerified').addClass('icon icon-');
               }
               if(reponse.tradpassword == 1){
            	   $('#tradpasswordVerified').addClass('icon icon-makesure');
            	   verifyCount = verifyCount + 1;
               }else{
            	   $('#tradpasswordVerified').addClass('icon icon-');
               }
               if(reponse.mobphonenum == 1){
            	   $('#mobileNumberVerified').addClass('icon icon-makesure');
            	   verifyCount = verifyCount + 1;
               }else{
            	   $('#mobileNumberVerified').addClass('icon icon-');
               }
               if(reponse.iskyc == 1){
            	   $('#kycVerified').addClass('icon icon-makesure');
            	   verifyCount = verifyCount + 1;
               }else{
            	   $('#kycVerified').addClass('icon icon-');
               }
               $('#verifyNum').text(verifyCount);
               $('#verifyLeftNum').text(5 - verifyCount);
           });
                 $('#liLast').click(function() {
                     sendLogoutData() //logout
                     $.removeCookie('UserId')
                     $('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
                     $('.ulList li:last').html('<a href="Register.html">  Register  </a>')
                     $('#userEmail').text('')
                     $('#userId').text('')
                     location.href='trade.html'
                  })
		stompClient.subscribe('/gateway/changeMobile-' + sessionId, function(data) {
			
			var response = jQuery.parseJSON(data.body);
			if(response.Status == "0"){
				alert("success");
				 $('#mobileNumber').text($('#changedPhoneNumber').val())  
				$('#changeMobileModal').modal('hide')
			}else if(response.Status == "-100"){
				alert("Email Code Error");
			}
		});
		stompClient.subscribe('/gateway/changeLoginPwd-' + sessionId, function(data) {
			var response =jQuery.parseJSON(data.body);
			if(response.Status == "0"){
				alert("success");
				$('#changeLoginPasswordModal').modal('hide')
			}else if(response.Status == "-1"){
				alert("Original Password Error");
			}
		});
		stompClient.subscribe('/gateway/changeTransPwd-' + sessionId, function(data) {
 			var response =jQuery.parseJSON(data.body);
			if(response.Status == "0"){
				alert("success");
				$('#changeTrasactionModal').modal('hide')
			}else if(response.Status == "-1"){
				alert("Original Transcation Password Error");
			}
		});
		    $('#emailCodeBtn').click(function(){
		   	   sendEmail()
		   	   sendSomething($(this))
		   })
	       $('#changeMobileBtn').click(function(){
	       	   sendSms()
	    	   sendSomething($(this))
   	       })
	        $('#AuthenticationCodeBtn1').click(function(){
	       	   sendSms()
	    	   sendSomething($(this))
	    	  
	       })
	         $('#authenticationCodeBtn').click(function(){
	       	   sendSms()
	    	   sendSomething($(this))
	    	  
	       })
	       customValidate($('#mobileForm'), {
				   	 "emailCode":{required:true},
				     "smsCode": {required: true,},
				     "phoneNumber": {
				         required: true,
				         number: true,
				     },
				     "verifyCode":{
				     	required:true,
				     	number:true
				     }
				   },{},function(){
					   	changeMobile()
//					   	$('#mobileForm').resetForm()
				   }
	       )
	       customValidate($('#chlgPassWordForm'), {
				     "loginOldPassword": {
				         required: true
				     },
				     "loginNewPassword": {
				         required: true
				     },
				     "loginConfirmPassword": {
				         required: true
				     },
				     "smsAuthenticationCode": {
				         required: true
				     }
					 }, {}, function() {
     
					   changeLoginPwd()
//					   $('#chlgPassWordForm').resetForm()
					 });
	       customValidate($('#trasactionPasswordForm'), {
					     "transcNewPassword": {
					         required: true
					                   },
					     "transcConfirmPassword": {
					         required: true
					                           },
					     "changeTranscPwdSms": {
					         required: true
					                           }
 						},{},
              	         function(){
 							changeTranscPwd()
// 							$('#trasactionPasswordForm').resetForm()
//            	         	resetStyle()
              	         	 
              	         	           	   
              	         }
	                    )
	 function sendLoginData() {
              stompClient.send("/ws/user/login", {}, JSON.stringify({ //login
                  "Tag": 8193,
                  "username": $('#lgEmail').val(),
                  "loginpassword": $('#lgPassword').val(),
                  "srcip": "192.168.0.1",
                  "RequestID": RequestId
                  }));
          }
	 function sendLogoutData() {
                stompClient.send("/ws/user/logout", {}, JSON.stringify({ //logout
               	 "Tag": 131089,
                 "srcip": "192.168.0.1",
                 "RequestID": RequestId,
                 "UserID":parseInt($.cookie('UserId'))
                }));
            }
	 
	     $('#kycAuthBtn').click(function(){
	     	 location.href='identity1.html'
	     })
	});
	
	
}
function init(){
	stompClient.send("/ws/security/home",{}, JSON.stringify({
		'Tag' : 24609,
		'RequestID' : RequestId,
		'UserID':parseInt($.cookie('UserId'))
	}));
}
function sendEmail(){
	stompClient.send("/ws/message/sendEmail",{}, JSON.stringify({
		'RequestID' : RequestId,
		'Email' : email,
	}));
}
function sendSms(){
	stompClient.send("/ws/message/sendSms",{}, JSON.stringify({
		'Tag' : 69634,
		'RequestID' : RequestId,
		'UserID' : parseInt($.cookie('UserId')),
		'MobilePhone' : $('#changedPhoneNumber').val()
	}));
}
function changeMobile(){
	  stompClient.send("/ws/security/changeMobile",  {}, JSON.stringify({
		'RequestID' : RequestId,
		'Tag' : 81920,
		'UserID' : parseInt($.cookie('UserId')),
		'Email' : email,
		'EmailCode' : $('#emailCode').val(),
		'MobilePhone' : $('#changedPhoneNumber').val(),
		'SmsCode' : $('#changeMobileSms').val(),
		'VerifyCode' : $('#verifyCode').val()
	  }));
}
function changeLoginPwd(){   
	stompClient.send("/ws/security/changeLoginPwd",  {},JSON.stringify({
		'RequestId' : RequestId,
		'Tag' : 73728,
		'UserID' : parseInt($.cookie('UserId')),
		'OldPwd' : $('#loginOldPassword').val(),
		'NewPwd' : $('#loginNewPassword').val(),
		//'ConfirmPwd': $('#loginConfirmPassword').val(),
		'SmsCode' : $('#changeLoginPwdSms').val()
	     }))
}
function changeTranscPwd(){
	stompClient.send("/ws/security/changeTransactionPwd",  {},JSON.stringify({
		'RequestID' : RequestId,
		'Tag' : 77824,
		'UserID' : parseInt($.cookie('UserId')),
		'OldPwd' : $('#transcOldPassword').val(),
		'NewPwd' : $('#transcNewPassword').val(),
	//	'ConfirmPwd' : $('#transcConfirmPassword').val(),
		'SmsCode' : $('#changeTranscPwdSms').val()
	         }))
}
function sendRegisterData() {
                stompClient.send("/ws/registry", {}, JSON.stringify({
                    "Tag": "4097",
                    "Email": $('#email1').val(),
                    "Password": $('#lgPassword').val(),
                    "Code": $('#verificationCode').val(),
                    "RequestID": RequestId
                }));
            }

$(function() {
	$('#email').text(email)
	$('#userEmail').text(email)
	$('#changeEmail').text(email)
	connect();
});

