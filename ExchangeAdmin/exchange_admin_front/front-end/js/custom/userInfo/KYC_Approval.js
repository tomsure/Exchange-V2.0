$(function(){
                function kycList(data){
                	$('#kycApprovalTable').bootstrapTable('destroy')
                	$.ajax({
             	type:"POST",
             	url:"http://47.75.72.244:8080/kyc/list",
             	async:true,
             	beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                     },               	                                                  data:JSON.stringify(data),

             	success:function(d){
             		 console.log(d)
             		 
             		
             		
             		 var kycTable=$('#kycApprovalTable').bootstrapTable({

           	toolbar: '#tableToolBar',
//         	queryParams: oTableInit.queryParams,
            pagination:true,
            pageSize:3,
             search:true,
            rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center'
                                }
                               };
                      return style;
                  },
           	 columns: [
           	          {
                          field: 'OrderID',
                          title: 'Order ID',
                          checkbox : true,
                          formatter:function(){
//                        	addClass('')
                          }
                      },
                      {
                          field: 'userId',
                          title: 'User ID'
                      },
                      {
                          field: 'userName',     
                          title: 'User Name'
                      },
                    
                      {
                          field: 'idType',
                          title: 'ID type'
                      },
                      {
                          field: 'idNo',
                          title: 'ID No.'
                      },
                      {
                      	 field: 'button',
                          title: '',
                           formatter:function(){
                           	     
                           	     return[ '<button class="btn btn-info" id="viewBtn">View</button>' ]
                           	     
                         },
                           events:{
					     	  "click #viewBtn":function(ev,value,row,index){
					     	  	  $.ajax({
					     	  	  	type:"GET",
					     	  	  	url:"http://47.75.72.244:8080/kyc/details",
					     	  	  	async:true,
					     	  	  	beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                     }, 
//					     	  	  	data:JSON.stringify({"userId":row.userId}),
                                    data:$.param({"userId":row.userId}),
					     	  	  	success:function(d){
					     	  	  		    	 layer.open({
      	 	
type: 1,
shade: false,
title: ['KYC Authentication', 'font-size:18px;text-align:center;'],
area:['550px','700px'],
content: $('#kycAuthModal'), 
btn:['Approved','Rejected'],
yes:function(index,layero){
	  $.ajax({
	  	type:"get",
	  	url:"http://47.75.72.244:8080/kyc/auth",
	  	async:true,
//	  	data:JSON.stringify({"userId" : 123, "status" : "1"}),
        data:$.param({"userId" : 1, "status" : "1"}),
	  	success:function(d){
	  		 if(d.status==200){
	  		 	layer.msg('success')
	  		 }
	  		
	  	}
	  });
	layer.close(index)
},

cancel: function(){
	 $.ajax({
	  	type:"get",
	  	url:"http://47.75.72.244:8080/kyc/auth",
	  	async:true,
//	  	data:JSON.stringify({"userId" : 123, "status" : "1"}),
        data:$.param({"userId" : 1, "status" : "0"}),
	  	success:function(d){
	  		 if(d.status==200){
	  		 	layer.msg('success')
	  		 }
	  		
	  	}
	  });
},
success: function(layero, index){
      $('#kycAuthModal').removeClass('hide')
}
});
					     	  	  		
					     	  	  		
					     	  	  	}
					     	  	  });
					     	  	 
					     	                         }
					                              }
                      }
                      ],
                 data:d,
                 
             		 })
             		 

                }
             });
             
 
                }
                
                 kycList({'userId':0,'userName':'','isNo':''})
                 
                 $('#searchBtn').click(function(){
                 	 kycList({'mobile':$('#mobile').val(),'userName':$('#userName').val(),'idNo':$('#idNumber').val()}) 
                 })

           	  
})