$(function(){
	customValidate($('#loginForm'),{
		"userName":{
			required:true
		},
		'password':{
			required:true
		}
	},{
		
	},function(){
		$.ajax({
			url:'http://47.75.72.244:8080/user/login',
			type:'POST',
			beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json"); 
               },
			data:JSON.stringify({"email":$('#userName').val(), "password":$('#password').val() }),         success:function(d){
				if(d.status==200){
					$.cookie('userId',d.userId,{path:'/'})
					$.cookie('userName',$('#userName').val(),{path:'/'})
					layer.msg('Login Successfully!')
					location.href='index.html'
                 
				}
			}
			
		})
	})
    
})
