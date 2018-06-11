$(function () {
    function currencyTable(data){
    	$.ajax({
        url: 'http://47.75.72.244:8080/crypto/list',
        type: 'POST',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
        },
        data: JSON.stringify(data),

        success: function (d) {
                   $('#currencyInfoTable').bootstrapTable('destroy')
            var table = $('#currencyInfoTable').bootstrapTable({
                rowStyle: function (row, index) {
                    var style = {};
                    style = {
                        css: {
                            'text-align': 'center'
                        }
                    };
                    return style;
                },
                checkBox: true,
                columns: [
                    {
                        checkbox: true,
                        events:{
                            'change input':function(ev,value,row,index){
                               
                                $('#editBtn').click(function(){
                                    console.log(row)
                                    layer.open({
                                        skin:['addStyle publicStyle'],
                                        title:['Edit Crypto Currency'],
                                        type:1,
                                        content:$('#editCryptoCurrency'),
                                        area:['500px','550px'],
                                        shade:0,
                                        success: function(layero, index){
                                            $('#editCryptoCurrency').removeClass('hide')

                                            $('#cryptId1').val(row.id)
                                            $('#coinName1').val(row.coinName)
                                            $('#chainId1').val(row.chainId)
                                            $('#withdrawalFee1').val(row.withdrawalFee)
                                            $('#status1').val(row.status)
                                            $('#baseCoin1').val(row.baseCoin)
                                            $('#description1').val(row.descrip)
                                        
                                        },
                                        end:function(){
                                          	validatorEdit.resetForm()
                                          	$('#editCryptoCurrencyForm').find('input').val('')
                    	$('#editCryptoCurrencyForm').find('input').removeClass('error')
                                        }
                                        
                                    })
                                })
                            }
                        }
                    },
                    {
                        field: 'id',
                        title: 'Id'
                    },
                    {
                        field: 'coinName',
                        title: 'Coin Name'
                    },
                    {
                        field: 'chainId',
                        title: 'Chain Id'
                    },
                    {
                        field: 'withdrawalFee',
                        title: 'Withdrawal Fee'
                    },
                    {
                        field: 'status',
                        title: 'status'
                    },
                    {
                        field: 'baseCoin',
                        title: 'Base Coin'
                    },
                    {
                        field: 'descrip',
                        title: 'Descrip'
                    },
                    {
                        field: 'status',
                        title: 'Operate',
                        formatter: operateFormatter
//                        events:
                    }

                ],
                data: d,
                onClickCell: function (field, value, row, $element) {
                    if ($($element.context).text() == 'Enable') {
                        userEnable("http://47.75.72.244:8080/crypto/enable", {
                            "ids": [row.id],
                            'status': 0
                        }, function (d) {
                            if (d.status == 200) {
                                $($element.context).find('button').removeClass('btn-success').addClass('btn-danger')
                                $($element.context).find('button').text('Disable')
                            }

                        })

                    } else if ($($element.context).text() == 'Disable') {
                        userEnable("http://47.75.72.244:8080/crypto/enable", {
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
                var checkedBox = $('#currencyInfoTable').find('tr td input[type="checkbox"]:checked')
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
                var checkedBox = $('#currencyInfoTable').find('tr td input[type="checkbox"]:checked')
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
            $('#addBtn').click(function(){
                layer.open({
                    skin:['addStyle publicStyle'],
                    title:['Add Crypto Currency'],
                    type:1,
                    content:$('#addCryptoCurrency'),
                    area:['500px','550px'],
                    shade:0,
                    success: function(layero, index){
                        $('#addCryptoCurrency').removeClass('hide')
                    },
                    end:function(){
                    	validatorAdd.resetForm()
                    	$('#addCryptoCurrencyForm').find('input').val('')
                    	$('#addCryptoCurrencyForm').find('input').removeClass('error')
                    }
                })
            })

        },
        error: function (err) {
            console.log(err)
        }
    })
    	
    	
    }
    currencyTable({"coinName": "", "chainId": 0, "status": -1})
     $('#searchBtn').click(function(){
     currencyTable({"coinName":$('#SearchCoinName').val(), "chainId":$('#searchChainId').val(), "status":$('#searchStatus').val() })
     })
     
     var validatorAdd=$('#addCryptoCurrencyForm').validate({
     	rules:{
     		'cryptId':{
     			required:true
     		},
     		'coinName':{
     			required:true
     		},
     		'withdrawalFee':{
     			required:true
     		},
     		'status':{
     			required:true
     		},
     		'baseCoin':{
     			required:true
     		},
     		'description':{
     			required:true
     		}
     		
     	},
     	submitHandler:function(){
     		  alert($('#coinName').val())
addSave({"id":Number($('#cryptId').val()),"coinName":$('#coinName').val(),"chainId":Number($('#chainId').val()), "withdrawalFee":Number($('#withdrawalFee').val()), "descrip" :$('#description').val(),"baseCoin":Number($('#baseCoin').val()),"status":Number($('#status').val())})
     	}
     	
     })
     var validatorEdit=$('#editCryptoCurrencyForm').validate({
     	rules:{
     		'cryptId':{
     			required:true
     		},
     		'coinName':{
     			required:true
     		},
     		'withdrawalFee':{
     			required:true
     		},
     		'status':{
     			required:true
     		},
     		'baseCoin':{
     			required:true
     		},
     		'description':{
     			required:true
     		}
     		
     	},
     	submitHandler:function(){
     	addSave({"id" : $('#cryptId1').val(),"coinName" :$('#coinName1').val(),"ChainId" :$('#chainId1').val(), "withdrawalFee":$('#withdrawalFee1').val(), "descrip" :$('#description1').val(), "baseCoin":$('#baseCoin1').val(), "status":$('#status1').val()})
     	}
     })


     function addSave(data){
     	  $.ajax({
     			type:"POST",
     			url:"http://47.75.72.244:8080/crypto/save",
     			async:true,
     			beforeSend: function(XMLHttpRequest) {
 				XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
 			                                         },
     			data:JSON.stringify(data),
     			success:function(){
     				layer.msg('Successfully!')
     				layer.closeAll(index)
 					currencyTable({"coinName": "", "chainId": 0, "status": -1})
     			}
     		});
     }
})
