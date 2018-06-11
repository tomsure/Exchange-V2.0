$(function () {
    function adminInfo(data){
    	$.ajax({
        type: "POST",
        url: "http://47.75.72.244:8080/admin/list",
        async: true,
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
        },
        data: JSON.stringify(data),

        success: function (d) {
            
                $('#adminInfoTable').bootstrapTable('destroy')
            var table = $('#adminInfoTable').bootstrapTable({
                pagination: true,
                pageSize: 3,
                rowStyle: function (row, index) {
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
                        checkbox: true,
                        events:{
                            'change input':function(ev,value,row,index){
                                // console.log(row.chainName)
                                $('#editBtn').click(function(){
                                    console.log(row)
                                    layer.open({
                                        skin:['addStyle publicStyle'],
                                        title:['Edit Admin Info'],
                                        type:1,
                                        content:$('#editAdminInfo'),
                                        area:['500px','550px'],
                                        shade:0,
                                        success: function(layero, index){
                                            $('#editAdminInfo').removeClass('hide')

                                            $('#adminId1').val(row.id)
                                            $('#userName1').val(row.userName)
                                            $('#mobile1').val(row.mobile)
                                            $('#roleId1').val(row.roleId)
                                            $('#status1').val(row.status)
                                            $('#lastLoginTime1').val(row.lastLoginTime)
                                        },
                                        end:function(){
                                        	validatorEdit.resetForm()
                                        	$('#editAdminInfoForm').find('input').val('')
                    	$('#editAdminInfoForm').find('input').removeClass('error')
                                        }
                                    })
                                })
                                $('#delBtn').click(function(){
                                	var result = table.bootstrapTable('getSelections');
                var ids = [];
                for (var i = 0; i < result.length; i++) {
                    var item = result[i];
                    ids.push(item.id);
                }
                     var checkedBox = $('#adminInfoTable').find('tr td input[type="checkbox"]:checked')
                       if(checkedBox.length<=0){
                       	alert('please check the table box')
                       }else{
                       	  $.ajax({
                       	  	type:"GET",
                       	  	url:"http://47.75.72.244:8080/admin/delete",
                       	  	beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                              },
                       	  	data:JSON.stringify({"ids":ids}),
                       	  	async:true,
                       	  	success: function(d){
                       	  		if(d.status==200){
                       	  			adminInfo({"userName": "", "status": -1, "roleId": 0})
                       	  		}
                       	  	}
                       	  });
                       }
                                })
                            }
                        }
                    },
                    {
                        field: 'id',
                        title: 'ID',
                    },
                    {
                        field: 'userName',
                        title: 'User Name',
                    },
                    {
                        field: 'mobile',
                        title: 'Mobile',
                    },
                    {
                        field: 'roleId',
                        title: 'Role',
                    },
                    {
                        field: 'status',
                        title: 'Status',
                    },
                    {
                        field: 'lastLoginTime',
                        title: 'Last Login Time',
                    },
                    {
                        field: 'status',
                        title: 'Operate',
                        formatter: operateFormatter
                    }


                ],
                data: d,
                onClickCell: function (field, value, row, $element) {
                    if ($($element.context).text() == 'Enable') {
                        userEnable("http://47.75.72.244:8080/admin/enable", {
                            "ids": [row.id],
                            'status': 0
                        }, function (d) {
                            if (d.status == 200) {
                                $($element.context).find('button').removeClass('btn-success').addClass('btn-danger')
                                $($element.context).find('button').text('Disable')
                            }

                        })

                    } else if ($($element.context).text() == 'Disable') {
                        userEnable("http://47.75.72.244:8080/admin/enable", {
                            "ids": [row.id],
                            'status': 1
                        }, function (d) {
                            if (d.status == 200) {
                                $($element.context).find('button').removeClass('btn-danger').addClass('btn-success')
                                $($element.context).find('button').text('Enable')
                            }

                        })

                    }


                }


            })

            $('#enabledBtn').click(function () {
                var result = table.bootstrapTable('getSelections');
                var ids = [];
                for (var i = 0; i < result.length; i++) {
                    var item = result[i];
                    ids.push(item.id);
                }
                var checkedBox = $('#adminInfoTable').find('tr td input[type="checkbox"]:checked')
                if (checkedBox.length <= 0) {
                    alert('please check the table box')
                } else if (checkedBox.length > 0) {
                    userEnable("http://47.75.72.244:8080/crypto/enable", {"ids": ids, "status": 1}, function (d) {
                        if (d.status == 200) {
                            alert('success')
                            enabledFun(checkedBox)
                        }
                    })
                }
            })
//               
            $('#disabledBtn').click(function () {
                var result = table.bootstrapTable('getSelections');

                var ids = [];
                for (var i = 0; i < result.length; i++) {
                    var item = result[i];
                    ids.push(item.id);
                }
                var checkedBox = $('#adminInfoTable').find('tr td input[type="checkbox"]:checked')
                if (checkedBox.length <= 0) {
                    alert('please check the table box')
                } else if (checkedBox.length > 0) {
                    userEnable("http://47.75.72.244:8080/crypto/enable", {"ids": ids, "status": 0}, function (d) {

                        if (d.status == 200) {
                            alert('success')
                            disabledFun(checkedBox)
                        }
                    })
                }
            })

            // 添加功能
            $('#addBtn').click(function () {
                layer.open({
                    skin: ['addStyle publicStyle'],
                    title: ['Add Pending Order'],
                    type: 1,
                    content: $('#addAdminInfo'),
                    area: ['500px', '550px'],
                    shade:0,
                    success: function (layero, index) {
                        $('#addAdminInfo').removeClass('hide')
                    },
                    end:function(){
                    	validatorAdd.resetForm()
                    	$('#addAdminInfoForm').find('input').val('')
                    	$('#addAdminInfoForm').find('input').removeClass('error')
                    }
                })
            })
        },
        error: function (err) {
            console.log(err)
        }
    });
    }
    
      adminInfo({"userName": "", "status": -1, "roleId": 0})
      $('#searchBtn').click(function(){
      	adminInfo({"userName":$('#userName').val(), "status": Number($('#status').val()), "roleId":Number($('#role').val())})
      })
      var validatorAdd=$('#addAdminInfoForm').validate({
      	 rules:{
      	 	"userName":{
      	 		required:true
      	 	},
      	 	"password":{
      	 		required:true
      	 	},
      	 	"mobile":{
      	 		required:true
      	 	},
      	 	"roleId":{
      	 		required:true
      	 	},
      	 	"status":{
      	 		required:true
      	 	}
      	 	
      	 },
      	submitHandler:function(){
      		save({"userName":$('#userName').val(), "password" :$('#password').val(), "mobile":$('#mobile').val(), "roleId":Number($('#roleId').val()), "status" :Number($('#status').val())})
      	}
      })

       var validatorEdit=$('#editAdminInfoForm').validate({
      	 rules:{
      	 	"userName1":{
      	 		required:true
      	 	},
      	 	"password1":{
      	 		required:true
      	 	},
      	 	"mobile1":{
      	 		required:true
      	 	},
      	 	"roleId1":{
      	 		required:true
      	 	},
      	 	"status1":{
      	 		required:true
      	 	}
      	 	
      	 },
      	submitHandler:function(){
      		save({"userName":$('#userName1').val(), "password" :$('#password1').val(), "mobile":$('#mobile1').val(), "roleId":Number($('#roleId1').val()), "status" :Number($('#status1').val())})
      	}
      })
      
      
       function save(d){
      	 $.ajax({
      	 	type:"POST",
      	 	url:"http://47.75.72.244:8080/admin/save",
      	 	async:true,
      	 	beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
            },
      	 	data:JSON.stringify(d),
      	 	success:function(data){
      	 		  if(data.status==200){
      	 		  	layer.msg('Successfully!')
     				layer.closeAll(index)
 					adminInfo({"userName": "", "status": -1, "roleId": 0})
      	 	      }
      	 		  }
      	 		
      	 });
      }
})
