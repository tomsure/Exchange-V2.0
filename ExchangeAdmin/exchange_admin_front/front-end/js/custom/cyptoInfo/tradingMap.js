$(function () {
    function tradingMap(d){
    	$.ajax({
        url: 'http://47.75.72.244:8080/cryptoPair/list',
        type: 'POST',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
        },
        data: JSON.stringify(d),

        success: function (d) {
           
                   $('#tradeMapTable').bootstrapTable('destroy')
            var table = $('#tradeMapTable').bootstrapTable({
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
                                // console.log(row.chainName)
                                $('#editBtn').click(function(){
                                    console.log(row)
                                    layer.open({
                                        skin:['addStyle publicStyle'],
                                        title:['Edit Crypto Pair'],
                                        type:1,
                                        content:$('#editCryptoPair'),
                                        area:['500px','550px'],
                                        shade:0,
                                        success: function(layero, index){
                                            $('#editCryptoPair').removeClass('hide')

                                            $('#cryptId1').val(row.id)
                                            $('#baseCoin1').val(row.baseCoin)
                                            $('#tradeCoin1').val(row.tradeCoin)
                                            $('#buyFee1').val(row.buyFee)
                                            $('#sellFee1').val(row.sellFee)
                                            $('#transacTime1').val(row.transacTime)
                                            $('#minBuyQty1').val(row.minBuyQty)
                                            $('#minSellQty1').val(row.minSellQty)

                                        },
                                        end:function(){
                                        	
                                          	validatorEdit.resetForm()
                                          	$('#editCryptoPairForm').find('input').val('')
                    	$('#editCryptoPairForm').find('input').removeClass('error')
                                        
                                        }
                                    })
                                })
                            }
                        }
                    },
                    {
                        field: 'id',
                        title: "ID"
                    },
                    {
                        field: 'baseCoin',
                        title: 'Base Coin'
                    },
                    {
                        field: 'tradeCoin',
                        title: 'Trade Coin'
                    },
                    {
                        field: 'buyFee',
                        title: 'Buy Fee'
                    },
                    {
                        field: 'sellFee',
                        title: 'Sell Fee'
                    },
                    {
                        field: 'transcationTime',
                        title: 'Transcation Time'
                    },
                    {
                        field: 'minBuyQty',
                        title: 'Min Buy Qty'
                    },
                    {
                        field: 'minSellQty',
                        title: 'Min Sell Qty'
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
                    if ($($element.context).text() == 'Enabled') {
                        userEnable("http://47.75.72.244:8080/cryptoPair/enable", {
                            "ids": [row.id],
                            'status': 0
                        }, function (d) {
                            if (d.status == 200) {
                                $($element.context).find('button').removeClass('btn-success').addClass('btn-danger')
                                $($element.context).find('button').text('Disable')
                            }

                        })

                    } else if ($($element.context).text() == 'Disable') {
                        userEnable("http://47.75.72.244:8080/cryptoPair/enable", {
                            "ids": [row.id],
                            'status': 1
                        }, function (d) {
                            if (d.status == 200) {
                                $($element.context).find('button').removeClass('btn-danger').addClass('btn-success')
                                $($element.context).find('button').text('Enabled')
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
                var checkedBox = $('#tradeMapTable').find('tr td input[type="checkbox"]:checked')
                if (checkedBox.length <= 0) {
                    alert('please check the table box')
                } else if (checkedBox.length > 0) {
                    userEnable("http://47.75.72.244:8080/cryptoPair/enable", {"ids": ids, "status": 1}, function (d) {
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
                var checkedBox = $('#tradeMapTable').find('tr td input[type="checkbox"]:checked')
                if (checkedBox.length <= 0) {
                    alert('please check the table box')
                } else if (checkedBox.length > 0) {
                    userEnable("http://47.75.72.244:8080/cryptoPair/enable", {"ids": ids, "status": 0}, function (d) {

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
                    title: ['Add Crypto Pair'],
                    type: 1,
                    content: $('#addCryptoPair'),
                    area: ['500px', '550px'],
                    shade:0,
                    success: function (layero, index) {
                        $('#addCryptoPair').removeClass('hide')
                        
                    },
                    end:function(){
                    	validatorAdd.resetForm()
                          $('#addCryptoPairForm').find('input').val('')
                    	$('#addCryptoPairForm').find('input').removeClass('error')
                    }
                })
            })
        },
        error: function (err) {
            console.log(err)
        }
    })
    }
     tradingMap({"baseCoin": "", "tradeCoin": "", "status": -1})
     $('#searchBtn').click(function(){
     	 tradingMap({"baseCoin":$('#searchBaseCoin').val(), "tradeCoin":$('#searchTradeCoin').val(), "status":$('#searchStatus').val()})
     }) 
     
         
     var validatorAdd=$('#addCryptoPairForm').validate({
     	 rules:{
     	 	"baseCoin" :{
     	 		required:true
     	 	            },
     	 	 "tradeCoin" :{
     	 	 	required:true
     	 	            },
     	 	 "status" : {
     	 	 	required:true
     	 	            }
     	 },
     	 submitHandler:function(){
     	save({"id" :Number($('#cryptId').val()),
     	      "baseCoinId" :Number($('#baseCoin').val()),
     	      "tradeCoinId" :Number($('#tradeCoin').val()),
     	      "buyFee":Number($('#buyFee').val()),
     	      "sellFee" :Number($('#sellFee').val()),
     	      "transacStartTime" :$('#transacStartTime').val(),
     	      "transactEndTime":$('#transactEndTime').val(), 
     	      "minBuyQty":Number($('#minBuyQty').val()),
     	      "maxSellQty" :Number($('#maxSellQty').val()),
     	      "maxDepositQty" :Number($('#maxDepositQty').val())
     	      })
     	 }
     })
      var validatorEdit=$('#editCryptoPairForm').validate({
      	 rules:{
     	 	"baseCoin1" :{
     	 		required:true
     	 	            },
     	 	 "tradeCoin1" :{
     	 	 	required:true
     	 	            },
     	 	 "status1" : {
     	 	 	required:true
     	 	            }
     	 },
     	 submitHandler:function(){
     	save({"id" :Number($('#cryptId').val()),
     	      "baseCoinId" :Number($('#baseCoin1').val()),
     	      "tradeCoinId" :Number($('#tradeCoin1').val()),
     	      "buyFee":Number($('#buyFee1').val()),
     	      "sellFee" :Number($('#sellFee1').val()),
     	      "transacStartTime" :$('#transacStartTime1').val(),
     	      "transactEndTime":$('#transactEndTime1').val(), 
     	      "minBuyQty":Number($('#minBuyQty1').val()),
     	      "maxSellQty" :Number($('#maxSellQty1').val()),
     	      "maxDepositQty" :Number($('#maxDepositQty1').val())
     	      })
     	 }
      })
      function save(d){
      	 $.ajax({
      	 	type:"POST",
      	 	url:"http://47.75.72.244:8080/cryptoPair/save",
      	 	async:true,
      	 	beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
            },
      	 	data:JSON.stringify(d),
      	 	success:function(data){
      	 		  if(data.status==200){
      	 		  	layer.msg('Successfully!')
     				layer.closeAll(index)
 					tradingMap({"baseCoin": "", "tradeCoin": "", "status": -1})
      	 	      }
      	 		  }
      	 		
      	 });
      }
})
