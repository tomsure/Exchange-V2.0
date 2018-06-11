$(function(){
	 if($.cookie('userId')){
	 	$('#userName').text($.cookie('userName'))
	 }
	 $('#logoutBtn').click(function(){
	 	 
	 	  $.ajax({
	 	  	type:"POST",
	 	  	url:"http://47.75.72.244:8080/user/logout",
	 	  	async:true,
	 	  	beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json"); 
               },
	 	  	data:JSON.stringify({'userId':$.cookie('userId')}),
	 	  	success:function(d){
	 	  		if(d.status==200){
	 	  			$('#userName').text('')
	 	       $.removeCookie('userId')
	 	       location.href='login_v2.html'
	 	  		}
	 	  		 
	 	  	}
	 	  });
	 	 
	 })
})
