$(function(){
	

	var stompClient = null;
    function connect(){
		var socket = new SockJS('/websocket');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame){
			
			var sessionId = socket._transport.url.split("/")[5];
	        customValidate($('#lgPassWordForm'), {
					"lgEmail": {
						required: true,
						email:true
					},
					"lgPassword": {
						required: true
					}
				},{},function(){
                          
					 	stompClient.subscribe('/gateway/login-' + sessionId, function(data) {
					   
					   var status=jQuery.parseJSON(data.body).Status
					         if(status==0){
					         	$('#myModal').modal('hide')
					         	$('#loginBtn').text($('#lgEmail').val())
					         	$('.ulList li:last').html('<span id="logout">Logout</span>')
					         	
					         	$('#lgPassWordForm')[0].reset()
					      
					         	
					         }else if(status==-1){
					         	alert('login error!')
					         	
					         }
					   $('#logout').click(function(){
	        	 	         

					   	  $('#loginBtn').text('LOGIN')
                          $('.ulList li:last').html('<a href="Register.html">  Register  </a>')
 $('#userItem').addClass('hide')
					   	 stompClient.send("/ws/user/login", {}, JSON.stringify({ 
			                    "Tag": 8193,
			                    "username": $('#lgEmail').val(),
			                    "loginpassword": $('#lgPassword').val(),
			                    "srcip": "192.168.0.1",
			                    "RequestID": RequestId
					   	 })
                           );
					   })
				})
					 	  
					 	
					 	stompClient.send("/ws/user/login", {}, JSON.stringify({
		                    "Tag": 8193,
		                    "username": $('#lgEmail').val(),
		                    "loginpassword": $('#lgPassword').val(),
		                    "srcip": "192.168.0.1",
		                    "RequestID": RequestId
                         }));				
				})	
		})
	}
	
	 
	
	
	connect()
})





















	