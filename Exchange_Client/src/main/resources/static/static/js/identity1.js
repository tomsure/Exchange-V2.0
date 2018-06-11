var stompClient = null;
var userId ='';
var email =$.cookie('email');


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
                      $.cookie('email',$('#lgEmail').val(),{expires: 7})
                      $.cookie('UserId',jQuery.parseJSON(data.body).UserID)
                      var status = jQuery.parseJSON(data.body).Status
                           
                      if (status == 0){
                    	$('#myModal').modal('hide')
                       $('#loginLi').html('<span>' + $.cookie('email') + '</span>')
                       $('#liLast').html('<span id="logout">Logout</span>')
                       
                         resetStyle()
                         $('#userItem').removeClass('hide')
                        } else if (status == -1) {
                        alert('login error!')
                                              }
                         //挂单
                       
               })
                sendLoginData() //login	
            })
		
		//louout
		 $('#liLast').click(function() {   //logout
                  	 
                     sendLogoutData() //send logout Data
                     $.removeCookie('UserId')
                    
                     $('#iconMark').hide()
                     $('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
               $('.ulList li:last').html('<a href="Register.html">  Register </a>')
                 $('#userItem').addClass('hide')
                 location.href='trade.html'
                 
                                               })
		
		
		function sendLoginData() {
              stompClient.send("/ws/user/login", {}, JSON.stringify({ //login
                  "Tag": 8193,
                  "username": $('#lgEmail').val(),
                  "loginpassword": $('#lgPassword').val(),
                  "srcip": "192.168.0.1",
                  "UserID" : parseInt($.cookie('UserId')),
                  "RequestID":RequestId
              }));
          }
          function sendLogoutData() {
              stompClient.send("/ws/user/logout", {}, JSON.stringify({ //logout
                  "Tag": 131089,
                  "UserID" : parseInt($.cookie('UserId')),
                  "srcip": "192.168.0.1",
                  "RequestID":RequestId
              }));
          }
		
	
		
		customValidate($('#basicInfo'), {
			"country":{
				required:true
			          },
			"fname":{
				required:true
			        },
			"lname":{
				required:true
			        },
			"type":{
				required:true
			       },
			"idNum":{
				required:true
			        }
		},{},function(){
			  kyc1()
			  location.href='identity2.html'
		})
		stompClient.subscribe('/gateway/userKycInfo-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			$('#country').val(response.Country);
			$('#fname').val(response.Firstname);
			$('#lname').val(response.Secondname);
			$('#type').val(response.Idtype);
			$('#idNum').val(response.Idno);
		})
		init();
		
	});
}

function kyc1(){
	stompClient.send("/ws/security/identity1", {}, JSON.stringify({
		'Tag' : 139264,
		'UserID' : parseInt($.cookie('UserId')),
		'country' : $('#country').val(),
		'firstName' : $('#fname').val(),
		'lastName' : $('#lname').val(),
		'idType' : $('#type').val(), 
		'idNumber' : $('#idNum').val() 
	}));
}
     
function init(){
	stompClient.send("/ws/security/identity", {}, JSON.stringify({
		'Tag' : 155648,
		'UserID' : parseInt($.cookie('UserId')),
		'RequestID' : 'testtest'
	}));
}


$(function() {
       
	 
	connect();
});