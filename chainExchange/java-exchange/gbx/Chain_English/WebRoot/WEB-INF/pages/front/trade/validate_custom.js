var someEle=".col-sm-8"||".col-sm-7"||".col-sm-6"||".col-sm-5"||".col-sm-9"
$.validator.addMethod('diffCode',function(value,element,param){
		var regCode=$('#loginOldPassword').val()
            return this.optional(element)||value!=regCode;
		},"New password should not the same as the old one !")
$.validator.addMethod('validNumber',function(value,element,param){
	     
	     var reg=/^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/ 
        var reg1=/^[^~`!@#\$%\^&\*\(\)_\-\+=\{\[\}\]\|\\:;\"\'\<,\>\?\.\/]/
        
	        return this.optional(element)|| reg1.test(value) && reg.test(value)
	         
},'Please enter the correct number')
 function customValidate(formEle, formRules, formMessages, submitHandler, invalidHandler) { //封装
      	  
     formEle.validate({
         rules: formRules,
         //messages:formMessages,
         messages: {
             "lgemail": {
                 required: function() {
                 return 'Please enter your email address!'
                 },
                 email: 'Please enter the mailbox in the correct format!'
             },
             "lgpassword": {
                 required: function() {
                     return 'Please enter the correct password.'
                 }
             },
             "smsCode": {
                 required: 'Please enter SMS verification code!',
                 remote: 'verification code error, please input again！'
             },
             "phoneNumber": {
                 required: 'The phone number must be entered.',
                 number: 'Please enter the correct phone number.'
             },
             "smsCode1": {
                 required: 'smsCode1 must be entered'
             },
             "code2": {
                 required: 'code is must be entered'
             },
             "loginOldPassword": {
                 required: 'Please enter the old password!'
             },
             "loginConfirmPassword":{
             	required:'Please input confirm password!'
             }
         },
         errorElement: 'em',
         errorPlacement: function(error, element) {

                    error.addClass("help-block");

                    element.parents(someEle).addClass("has-feedback");
                     
                    if (element.prop("type") === "checkbox") {
                        error.insertAfter(element.parent("label"));
                    } else {
                        error.insertAfter(element);
                    }

                    if (!element.next("span")[0]) {
                   $("<span class='glyphicon  form-control-feedback'></span>").insertAfter(element);
                    }
                },
        success: function(label, element) {
             if (!$(element).next("span")[0]) {
                 $("<span class='glyphicon  form-control-feedback'></span>").insertAfter($(element));
             }
            
              },
        highlight: function(element, errorClass, validClass) {
           	$(element).css('margin-bottom',0)
             $(element).parents(someEle).addClass("has-error").removeClass("has-success");
          },
         unhighlight: function(element, errorClass, validClass) {
         	  $(element).css('margin-bottom','25px')
          $(element).parents(someEle).addClass("has-success").removeClass("has-error");
          $(element).next("span").removeClass("glyphicon-remove");
          },
         submitHandler:submitHandler,
         invalidHandler: function() {
            
         }
     })

 }