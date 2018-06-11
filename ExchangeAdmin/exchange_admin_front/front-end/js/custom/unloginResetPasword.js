$(function(){
	customValidate($('#resetPasswordOForm'),{
		'email':{
			required:true
		},
		"password":{
			required:true
		},
		"confirmPassword":{
			required:true
		}
	},{
		
	},function(){
		$.ajax({
			type:"POST",
			url:"http://47.75.72.244:8080/user/resetPwd",
			async:true,
			beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json"); 
               },
			data:JSON.stringify({"email":$('#email').val(),"password":$('#password').val()}),
			success:function(d){
				 if(d.status==200){
				 	
				 	location.href='login_v2.html'
				 }
				
			}
			
		});
	})
})
