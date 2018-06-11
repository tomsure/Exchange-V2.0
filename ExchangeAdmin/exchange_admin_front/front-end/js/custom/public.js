       function timestampToTime(timestamp) {  // 时间戳转换时间
        var date = new Date(timestamp * 1000);// 时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';

         D= date.getDate()<10 ? "0" + date.getDate() + ' ' : date.getDate()+ ' ';

         h=date.getHours()<10 ? '0'+ date.getHours() + ':': date.getHours() + ':';

          m=date.getMinutes()<10 ? '0'+ date.getMinutes() + ':': date.getMinutes() + ':';

          s=date.getSeconds()<10 ? '0'+ date.getSeconds() : date.getSeconds();
        return Y+M+D+h+m+s;
   } 
     var index=''
    var enable='<button type="button"  class="RoleOfdelete btn btn-success  btn-md" style="margin-right:15px;">Enable</button>';
     var disable='<button type="button"  class="RoleOfdelete btn btn-danger  btn-md" style="margin-right:15px;">Disable</button>';
     
     
   function operateFormatter(status) {   //操作按钮
   	 
   	  if(status==1){
   	  	return [ enable ]
   	  }else if(status==0){
   	  	return [ disable ]
   	  }
      
      }
   

   
  
function enabledFun(checkedBox){
                   $.each(checkedBox, function(i,el) {
                   	  if($(el).parents('tr').find('td button').text()=='Disable'){
                   	  	   $(el).parents('tr').find('td button').text('Enable').addClass('btn-success').removeClass('btn-danger')
                      }else{
                      	
                      }
                      });
                    
}
  function disabledFun(checkedBox){
                   $.each(checkedBox, function(i,el) {
                   	 
 if($(el).parents('tr').find('td button').text()=='Enable'){
                      	  $(el).parents('tr').find('td button').text('Disable').addClass('btn-danger').removeClass('btn-success')
                      }else {
                      	
                      }
                      });
                    
}
    
      function userEnable(url,sendData,successFn){
      	        $.ajax({
               	type:"POST",
               	url:url,
                beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                      },  
                data:JSON.stringify(sendData),
                success:successFn,	
                error:function(err){
               		console.log(err)
               	}
               });     
      }

//    function layerView(){
//    	 layer.open({
//    	 	
//type: 1,
//shade: false,
//title: ['KYC Authentication', 'font-size:18px;text-align:center;'],
//area:['550px','700px'],
//content: $('#kycAuthModal'), 
//btn:['Approved','Rejected'],
//yes:function(index,layero){
//	  $.ajax({
//	  	type:"get",
//	  	url:"http://47.75.72.244:8080/kyc/auth",
//	  	async:true,
////	  	data:JSON.stringify({"userId" : 123, "status" : "1"}),
//      data:$.param({"userId" : 1, "status" : "1"}),
//	  	success:function(d){
//	  		 if(d.status==200){
//	  		 	layer.msg('success')
//	  		 }
//	  		
//	  	}
//	  });
//	layer.close(index)
//},
//
//cancel: function(){
//	 $.ajax({
//	  	type:"get",
//	  	url:"http://47.75.72.244:8080/kyc/auth",
//	  	async:true,
////	  	data:JSON.stringify({"userId" : 123, "status" : "1"}),
//      data:$.param({"userId" : 1, "status" : "0"}),
//	  	success:function(d){
//	  		 if(d.status==200){
//	  		 	layer.msg('success')
//	  		 }
//	  		
//	  	}
//	  });
//},
//success: function(layero, index){
//    $('#kycAuthModal').removeClass('hide')
//}
//});
//    			
      			
//    		}
//    	})
//    	
      	
      	
      	
      	
//    }
      function viewTable(){
//    	     layer.open({
//    	 	
//type: 1,
//shade: false,
//title: ['KYC Authentication', 'font-size:18px;text-align:center;'],
//area:['550px','700px'],
//content: $('#historyTableModalBox'), 
//btn:['Approved','Rejected'],
//yes:function(index,layero){
//	  
//	layer.close(index)
//},
//
//cancel: function(){
//},
//success: function(layero, index){
//    $('#historyTableModalBox').removeClass('hide')
//    $.ajax({
//    	type:"GET",
//    	url:"http://47.75.72.244:8080/historyOrder/details",
//    	beforeSend: function (XMLHttpRequest) {  
//              XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
//                                                    },  
//    	
//    	data:JSON.stringify({"orderId":0}),
//    	async:true,
//    	success:function(){
//    		 alert(1111)
//    	}
//    });
//    $('#historyTableLayer').bootstrapTable({
//    	  rowStyle: function(row, index) {
//                    var style = {};
//                    style = {
//                        css: {
//                            'text-align': 'center'
//                              }
//                             };
//                    return style;
//                },
//                checkBox:true,
//         	 columns: [
//         	         {
//                        field: 'coinName',     
//                        title: 'Trade Order ID'
//                    },
//                    {
//                        field: 'coinName',     
//                        title: 'User Name'
//                    },
//                    {
//                        field: 'coinName',     
//                        title: 'Price'
//                    },
//                    {
//                        field: 'coinName',     
//                        title: 'Qty'
//                    },
//                    {
//                        field: 'coinName',     
//                        title: 'Amount'
//                    },
//                     {
//                        field: '',     
//                        title: 'Time'
//                    },
//         	    
//         	 
//         	 
//         	 
//         	 
//         	 ],
////         	 data:d
//    })
//}
//});
      }

  function formReset(form){
  	   form.find('input').val('')
  	    
  }
