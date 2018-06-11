$(function(){
    
     function userDetails(data){
     	 $('#userDetailsTable').bootstrapTable('destroy')
     	$.ajax({
             	type:"POST",
             	url:"http://47.75.72.244:8080/user/list",
             	async:true,
             	beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                      },  
             	data:JSON.stringify(data),
             	
             	success:function(d){
            var ableTable=$('#userDetailsTable').bootstrapTable({

           	toolbar: '#tableToolBar',


            pagination:true,
            pageSize:3,
            search:true,
//          searchOnEnterKey:true,
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
                          field: 'mobile',
                          title: 'Mobile'
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
                          field: 'isKyc',
                          title: 'KYC',
                          formatter:function(val,row,index){
                          	if(val==1){
                          		return 'Yes'
                          	}else if(val==0){
                          		return 'No'
                          	}
                          }
                      },
                      {
                          field: 'isKycLocked',
                          title: 'KYC Locked',
                          formatter:function(val,row,index){
                          	 if(val==1){
                          	 	return 'Yes'
                          	 }else if(val==0){
                          	 	return 'No'
                          	 }
                          }
                      },
                      {
                          field: 'lastLoginTime',
                          title: 'Last Login Time',
                          formatter:function(val,row,index){
                          	return timestampToTime(row.lastLoginTime)
                          }
                      },
                      {
                          field: 'status',
                          title: 'Operation',
                          formatter: operateFormatter
                      }
                      
                      ],
                 data:d,
                 onClickCell:function(field, value, row, $element){
                    	   if($($element.context).text()=='Enable'){
                 	   	userEnable("http://47.75.72.244:8080/user/enable",{"ids":[row.userId],'status':0},function(d){
                 	   		if(d.status==200){
            $($element.context).find('button').removeClass('btn-success').addClass('btn-danger')
            $($element.context).find('button').text('Disable')
                 	   		}
                             
                 	   	})
                 	   		
                 	   }else if($($element.context).text()=='Disable'){
                 	   	userEnable("http://47.75.72.244:8080/user/enable",{"ids":[row.userId],'status':1},function(d){
                 	   		 if(d.status==200){
            $($element.context).find('button').removeClass('btn-danger').addClass('btn-success')
            $($element.context).find('button').text('Enable')
                 	   		 }
                            
                 	   	})
                 	  
                 	   }


                 },
             		 })
             		 

             		 
             		
              $('#enabledBtn').click(function(){
               	var result = ableTable.bootstrapTable('getSelections');
            	     var ids = [];
			    for (var i = 0; i < result.length; i++) {
			        var item = result[i];
			        ids.push(item.userId);
			    }
                 var checkedBox=$('#userDetailsTable').find('tr td input[type="checkbox"]:checked')
                 if(checkedBox.length<=0){
                 	 alert('please check the table box')
                 }else if(checkedBox.length>0){
                 	userEnable("http://47.75.72.244:8080/user/enable",{"ids":ids,"status":1},function(d){
                   	if(d.status==200){
                 		alert('success')
                 		enabledFun(checkedBox)
                    }
                 })
                 	}
                })		 
                 
                 $('#disabledBtn').click(function(){
                 	var result = ableTable.bootstrapTable('getSelections');

          	     var ids = [];
			    for (var i = 0; i < result.length; i++) {
			        var item = result[i];
			        ids.push(item.userId);
			    }
                 var checkedBox=$('#userDetailsTable').find('tr td input[type="checkbox"]:checked')
                 	if(checkedBox.length<=0){
                 	 alert('please check the table box')
                 }else if(checkedBox.length>0){
                 	userEnable("http://47.75.72.244:8080/user/enable",{"ids":ids,"status":0},function(d){
                 		 
                 	if(d.status==200){
                 		alert('success')
                 		disabledFun(checkedBox)
                    }
                 })
                 	
                 	
                 	
                 }
                 	
                 	
                 })
                 

                }
             });
     }
    
             
//           
   userDetails({"userId" : 0, "mobile" : "", "idNo": ""})
           	  $('#searchBtn').click(function(){
           	  	
             userDetails({"userName" :$('#userName').val() , "mobile" :$('#mobile').val(), "idNo":$('#idNumber').val()})
           	  })
})
