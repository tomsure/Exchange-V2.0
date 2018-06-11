
   var someEle=".col-sm-8"||".col-sm-7"||".col-sm-6"||".col-sm-5"

  function customValidate(formEle,rules,messages,submitHandler){
  	   validator=formEle.validate({
	 	rules:rules,
	 	messages:messages,
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
                        $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
                    }
                },
         success: function(label, element) {
             if (!$(element).next("span")[0]) {
                 $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
             }
            
              },
        highlight: function(element, errorClass, validClass) {
           	$(element).css('margin-bottom',0)
             $(element).parents(someEle).addClass("has-error").removeClass("has-success");
             $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
               if($(element).hasClass('passwordText')){
               	    $(element).removeClass('passwordText')

               }
               
               
        },
         unhighlight: function(element, errorClass, validClass) {
         	  $(element).css('margin-bottom','25px')
          $(element).parents(someEle).addClass("has-success").removeClass("has-error");
             

             $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
              if($(element).hasClass('passwordText')){
              	  $(element).addClass('passwordText')
              }
              
            },
	 	submitHandler:submitHandler
	 })
  	  
  }

  