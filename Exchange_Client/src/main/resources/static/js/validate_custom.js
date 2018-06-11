 var someEle = ".col-sm-8" || ".col-sm-7" || ".col-sm-6" || ".col-sm-5"
 $.validator.addMethod('diffCode', function(value, element, param) {
 	var regCode = $('#loginOldPassword').val()
 	return this.optional(element) || value != regCode;
 }, "New password should not the same as the old one !")
 $.validator.addMethod('validNumber', function(value, element, param) {

 	var reg = /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/
 	var reg1 = /^[^~`!@#\$%\^&\*\(\)_\-\+=\{\[\}\]\|\\:;\"\'\<,\>\?\.\/]/

 	return this.optional(element) || reg1.test(value) && reg.test(value)

 }, 'Please enter the correct number')
 $.validator.addMethod('rightAddressText', function(value, element, param) {
 	var reg2=/^[a-zA-Z0-9]+$/
 	return this.optional(element) || reg2.test(value)
 }, "Text Error!")
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
 			"loginConfirmPassword": {
 				required: 'Please input confirm password!'
 			}
 		},
 		errorElement: 'em',
 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(someEle).addClass("has-feedback");

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
 			$(element).parents(someEle).addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
 			if($(element).hasClass('passwordText')) {
 				$(element).removeClass('passwordText')

 			}

 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).css('margin-bottom', '25px')
 			$(element).parents(someEle).addClass("has-success").removeClass("has-error");

 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
 			if($(element).hasClass('passwordText')) {
 				$(element).addClass('passwordText')
 			}

 		},
 		submitHandler: submitHandler,
 		invalidHandler: function() {

 		}
 	})

 }

 //exchange
 //buyForm
 function entrustBuyForm(submitHandler) {
 	$("#buyForm").validate({
 		rules: {
 			buyPrice: {
 				required: true,
 				number: true,
 				min: 0
 			},
 			amount: {
 				required: true,
 				digits: true
 			}
 		},
 		messages: {
 			buyPrice: {
 				required: 'Please enter price!',
 				number: 'Please enter the number!',
 				min: 'Please enter a number greater than 0!'
 			},
 			amount: {
 				required: 'Please enter amount!',
 				digits: 'Please enter an integer!'
 			}

 		},
 		errorElement: "em",

 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(".col-sm-5").addClass("has-feedback");

 			if(element.prop("type") === "checkbox") {
 				error.insertAfter(element.parent("label"));
 			} else {
 				error.insertAfter(element);
 			}

 			if(!element.next("span")[0]) {
 				$("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
 			}
 		},
 		submitHandler: submitHandler,
 		success: function(label, element) {

 			if(!$(element).next("span")[0]) {
 				$("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
 			}
 		},
 		highlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
 		},

 	});
 }

 // indenty1

 //sellForm
 function entrustSellFrom(submitHandler) {
 	$("#sellForm").validate({
 		rules: {
 			sellPrice: {
 				required: true,
 				number: true,
 				min: 0
 			},
 			amount: {
 				required: true,
 				digits: true
 			}
 		},
 		messages: {
 			sellPrice: {
 				required: 'Please enter price!',
 				number: 'Please enter the number!',
 				min: 'Please enter a number greater than 0!'
 			},
 			amount: {
 				required: 'Please enter amount!',
 				digits: 'Please enter an integer!'
 			}

 		},
 		errorElement: "em",
 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(".col-sm-5").addClass("has-feedback");

 			if(element.prop("type") === "checkbox") {
 				error.insertAfter(element.parent("label"));
 			} else {
 				error.insertAfter(element);
 			}

 			if(!element.next("span")[0]) {
 				$("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
 			}
 		},
 		submitHandler: submitHandler,
 		success: function(label, element) {

 			if(!$(element).next("span")[0]) {
 				$("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
 			}
 		},
 		highlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
 		},

 	});
 }

 //otc
 //otcbuyform
 function otcBuyform(submitHandler) {
 	$("#otcbuyForm").validate({
 		rules: {
 			otcBuyAmount: {
 				required: true,
 				number: true,
 				min: 0
 			},
 			otcBuyPassword: {
 				required: true,
 				digits: true
 			}
 		},
 		messages: {
 			otcBuyAmount: {
 				required: 'Please enter Amount!',

 			},
 			otcBuyPassword: {
 				required: 'Please enter Password!',

 			}

 		},
 		submitHandler: submitHandler,
 		errorElement: "em",

 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(".col-sm-5").addClass("has-feedback");

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
 			$(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
 		},

 	});
 }
 //otcsellform
 function otcSellForm(submitHandler) {
 	$("#otcSellForm").validate({
 		rules: {
 			otcSellAmount: {
 				required: true,

 			},
 			otcSellPassword: {
 				required: true,

 			}
 		},
 		messages: {
 			otcSellAmount: {
 				required: 'Please enter Amount!',

 			},
 			otcSellPassword: {
 				required: 'Please enter Password!',

 			}

 		},
 		submitHandler: submitHandler,
 		errorElement: "em",

 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(".col-sm-5").addClass("has-feedback");

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
 			$(element).parents(".col-sm-7").addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-7").addClass("has-success").removeClass("has-error");
 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
 		},

 	});
 }

 //add account
 function addAccountForm(submitHandler) {
 	$("#addAccountForm").validate({
 		rules: {
 			holderName: {
 				required: true,

 			},
 			bankName: {
 				required: true,

 			},
 			bankAccout: {
 				required: true,
 				number: true,
 				validNumber: true,

 			},
 			openBranchBank: {
 				required: true,
 			}
 		},
 		messages: {
 			holderName: {
 				required: 'Please enter holder name!',

 			},
 			bankName: {
 				required: 'Please enter bank name!',

 			},
 			bankAccout: {
 				required: 'Please enter bank account!',
 			},
 			openBranchBank: {
 				required: 'Please enter open branch bank!',
 			}

 		},
 		submitHandler: submitHandler,
 		errorElement: "em",

 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(".col-sm-5").addClass("has-feedback");

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
 			$(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
 		},

 	});
 }

 customValidate($('#feedbackForm'), {
 	"type": {
 		required: true
 	},
 	"problemDescription": {
 		required: true
 	},
 	"emailAddress": {
 		required: true
 	},
 	"phoneNumber": {
 		required: true
 	}
 })

 //token_trade

 //

 function validateTokenTrade(formEle, submitHandler) {
 	formEle.validate({

 		rules: {
 			"price": {
 				required: true,
 				number: true,
 				validNumber: true

 			},
 			"amount": {
 				required: true,
 				number: true,
 				validNumber: true
 			},
 			"password": {
 				required: true
 			}
 		},

 		errorElement: 'em',
 		errorPlacement: function(error, element) {

 			error.addClass("help-block");

 			element.parents(someEle).addClass("has-feedback");

 			if(element.prop("type") === "checkbox") {
 				error.insertAfter(element.parent("label"));
 			} else {
 				error.insertAfter(element);
 			}

 			if(!element.next("span")[0]) {
 				$("<span class='glyphicon glyphicon-remove textError form-control-feedback'></span>").insertAfter(element);
 			}
 		},
 		success: function(label, element) {
 			if(!$(element).next("span")[0]) {
 				$("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
 			}

 		},
 		highlight: function(element, errorClass, validClass) {
 			$(element).css('margin-bottom', '0')
 			$(element).parents(someEle).addClass("has-error").removeClass("has-success");
 			$(element).next(".textError").addClass("glyphicon-remove").removeClass("glyphicon-ok");

 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).css('margin-bottom', '20px')
 			$(element).parents(someEle).addClass("has-success").removeClass("has-error");

 			$(element).next(".textError").addClass("glyphicon-ok").removeClass("glyphicon-remove");

 		},
 		submitHandler: submitHandler

 	})
 }

 function publicValidate(formEle, success, submitHandler) {
 	var validator = formEle.validate({
 		rules: {

 			email: {
 				required: true,
 				email: true
 			},
 			emailCode: {
 				required: true,

 			},
 			resetPassword: {
 				required: true,

 			},
 			confirmPassword: {
 				required: true,
 				equalTo: '#resetPassword'
 			},
 			verificationCode: {
 				required: true
 			}

 		},
 		messages: {
 			//                email:{
 			//                	required:'dsafsf',
 			//                },
 			//                emailCode:{
 			//                	required:'dsafsf',
 			//                },
 			//                resetPassword:{
 			//                	required:'dsafsf',
 			//                }

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
 		success: success,

 		highlight: function(element, errorClass, validClass) {
 			$(element).css('margin-bottom', 0)
 			$(element).parents(".col-sm-6").addClass("has-error").removeClass("has-success");
 			$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");

 		},
 		unhighlight: function(element, errorClass, validClass) {
 			$(element).css('margin-bottom', '20px')
 			$(element).parents(".col-sm-6").addClass("has-success").removeClass("has-error");
 			$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");

 		},
 		submitHandler: submitHandler
 	})
 }
 

  