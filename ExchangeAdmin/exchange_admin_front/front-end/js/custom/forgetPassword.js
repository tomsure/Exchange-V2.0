$(function(){
	customValidate($('#forgetPasswordForm'),{
		'email':{
			required:true
		}
	},{},function(){
		 $.ajax({
		 	type:"POST",
		 	url:"http://47.75.72.244:8080/user/forgetPwd",
		 	beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json"); 
               },
		 	data:JSON.stringify({"email":$('#email').val()}),
		 	async:true,
		 	success:function(d){
		 		 if(d.status==200){
		 		 	alert('success')
		 		 	$('#email').val('')
		 		 }
		 		
		 	},
		 	error:function(e){
		 		console.log(e)
		 	}
		 });
	})
})
