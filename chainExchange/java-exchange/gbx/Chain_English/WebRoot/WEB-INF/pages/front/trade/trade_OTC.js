$(function(){
	

	
	
	 function accMul(arg1,arg2)   //精准乘法计算
  {   
  var m=0,s1=arg1.toString(),s2=arg2.toString();   
  try{m+=s1.split(".")[1].length}catch(e){}   
  try{m+=s2.split(".")[1].length}catch(e){}   
  return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)   
  }   
  
   // toFixed兼容方法
Number.prototype.toFixed = function (n) {
    if (n > 20 || n < 0) {
        throw new RangeError('toFixed() digits argument must be between 0 and 20');
    }
    var number = this;
    if (isNaN(number) || number >= Math.pow(10, 21)) {
        return number.toString();
    }
    if (typeof (n) == 'undefined' || n == 0) {
        return (Math.round(number)).toString();
    }

    var result = number.toString();
     arr = result.split('.');

    // 整数的情况
    if (arr.length < 2) {
        result += '.';
        for (var i = 0; i < n; i += 1) {
            result += '0';
        }
        return result;
    }

    var integer = arr[0];
    var decimal = arr[1];
    if (decimal.length == n) {
        return result;
    }
    if (decimal.length < n) {
        for (var i = 0; i < n - decimal.length; i += 1) {
            result += '0';
        }
        return result;
    }
    result = integer + '.' + decimal.substr(0, n);
    var last = decimal.substr(n, 1);

    // 四舍五入，转换为整数再处理，避免浮点数精度错误
    if (parseInt(last, 10) >= 5) {
        var x = Math.pow(10, n);
        result = (Math.round((parseFloat(result) * x)) + 1) / x;
        result = result.toFixed(n);
    }

    return result;
};
      
	  //
	  
	     function buyTotal(){
      
     if( !isNaN($('#transBuyPrice').val()) && !isNaN($('#transBuyAmount').val())){
     	$('#buyTotal').val(accMul($('#transBuyPrice').val(),$('#transBuyAmount').val()).toFixed(8))
     }else{
     	  $('#buyTotal').val('')
     }
       
              }
              function sellTotal(){

        if(!isNaN($('#transSellPrice').val())&&!isNaN($('#transSellAmount').val())){
   $('#sellTotal').val(accMul($('#transSellPrice').val(),$('#transSellAmount').val()).toFixed(8))
        }else{
        	$('#sellTotal').val('') 
        }
              }
              //buy 计算总数
                $('#transBuyPrice').on("input propertychange",function(){
                                    buyTotal()
                                         })
               $('#transBuyAmount').on("input propertychange",function(){
                                  buyTotal()
                                        })

               //sell 计算总数
               $('#transSellPrice').on("input propertychange",function(){
               	                    
                                    sellTotal()
                                         })
               $('#transSellAmount').on("input propertychange",function(){
                                  sellTotal()
                                        })
               
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  //
	
	 function buyFormatter(status) {   //操作按钮
   	 
   	  return ['<button class="btn btnBuy " >Buy</button>']
     
      
      }
	 function sellFormatter(status) {   //操作按钮
   	 
   	  return '<button class="btn btnSell">Sell</button>'
      
      }
	 function buyFromLayer(price){
	 	  
	 	layui.use('layer',function(){
	 		var layer=layui.layer
		 		layer.open({
	 			skin:'tradeBuyStyle',
	 			area:['400px','520px'],
	 			title:['Buy','font-size:18px;'],
    	 		type:1,
	    		content:$('#tradingBuyModal'),
	 	    	cancel:function(){
	 			$('#tradingBuyModal').addClass('hide')
	 		                   },
	 		    
	 	})
	 	})
	 	$('#tradingBuyModal').removeClass('hide')
	 	
	 	
	 	
	 }
	 function sellFormLayer(price){
	 	layui.use('layer',function(){
	 		var layer=layui.layer
	 		
	 		
	 		layer.open({
	 			skin:'tradeSellStyle',
	 			area:['400px','480px'],
	 			title:['Sell','font-size:18px;'],
	 		type:1,
	 		content:$('#tradingSellModal'),
	 		cancel:function(){
	 			$('#tradingSellModal').addClass('hide')
	 		                },
	 		success:function(){
	 			
	 		}
	 	})
	 	})
	 	$('#tradingSellModal').removeClass('hide')
	 }
     function resetBuyForm(){
     	$('#buyForm').find('input').val('').parent().removeClass('has-feedback has-success')
				  	 $('#priceText').text('')
     }
     function resetSellForm(){
     	$('#sellForm').find('input').val('').parent().removeClass('has-feedback has-success')
				  $('#priceText').text('') 
     }
     function resetTransBuy(){
     	$('#transBuy').find('input').val('').parent().removeClass('has-feedback has-success')
				$('#buyTotal').val('')
     }
     function resretTransSell(){
     	$('#transSell').find('input').val('').parent().removeClass('has-feedback has-success')
				$('#sellTotal').val('')
     }
     
   $('#releaseBtn').click(function(){
		
		layui.use('layer',function(){
			var layer=layui.layer
			  layer.open({
			  	type:1,
			  	title:['Release Transaction','text-align:center;'],
			  	content:$('#tabBox'),
			  	area:['400px','425px'],
			  	cancel:function(){
			  		
			  	}
			  })
		})
		
	})
	
	
	layui.use('element', function(){
    var $ = layui.jquery
    ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    });
    $('#sellLi').click(function(){
		$(this).addClass('sellColor')
		 $('#buyLi').removeClass('buyColor')
	})
	$('#buyLi').click(function(){
		$(this).addClass('buyColor')
		 $('#sellLi').removeClass('sellColor')
	})
	
	
	
	
	 function tokenTradeTable(coin){
	 	 $('#buyTable').bootstrapTable('destroy');
	 	 $('#sellTable').bootstrapTable('destroy');
	 	 $.ajax({
		type:"get",
//		url:"jsonData/token_tradeTable.json",
        url:"http://10.10.10.98:8080/otc/getAllEntrust.html",
		async:true,
		data:$.param({"coinId":coin}),
//      data:JSON.stringify({"coinId":coin}),
		success:function(d){
			  console.log('Buy' +  d)
			var d=$.parseJSON(d)
        
        $('#buyTable').bootstrapTable({
				rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center',
                              'border':'none',
                              
                                }
                               };
                      return style;
                  },
                  pagination:true,
                  pageSize:5,

                  columns:[
                   {
                   	field: 'Price',
                    title: 'Price'
                   },
                   {
                   	field: 'Amount',
                    title: 'Amount'
                   },

                   {
                   	field: 'Total',
                    title: 'Total',
                    formatter:function(val,row,index){
                    	return Number(row.Price)*Number(row.Amount)
                    }
                   },
                   {
                   	field: 'operate',
                    title: 'Operate',
                   
                    formatter: buyFormatter,
                    events:{
                    	'click .btnBuy':function(ev,value,row,index){
                    	$('#buyFormBox').html('<span class="priceText">'+ row.Price +'</span>')
                         $.ajax({
                         	url:"http://10.10.10.98:8080/otc/getSellerInfo.html",
                         	type:"GET",
                         	data:$.param({"entrustId":row.Id}),
                         	success:function(d){
                         		var d=JSON.parse(d)
                         		  $('#buyuserName').val(d.userName)
                         		  $('#buybankName').val(d.bankName)
                         		  $('#buybankNumber').val(d.BankAccount)
                         		  $('#buymobileNumber').val(d.mobileNum)
                         		  
                         	}
                         })
                     buyFromLayer()
                      customValidate($('#buyForm'),{
				"buyAmount":{
					       required:true,
					      number:true,
                          validNumber:true
				},
				"buyPassword":{
					      required:true,
					      
//                        validNumber:true
				}
		
			},{},function(){
				$.ajax({
					type:"GET",

                    url:"http://10.10.10.98:8080/otc/trade.html",
					async:true,

		data:$.param({"entrustId":row.Id,"userId":204,"amount":$('#buyAmount').val(),"pwd":$('#buyPassword').val()}),

				  success:function(d){
				  	var d=JSON.parse(d)
				  	resetBuyForm()
					if(d.code=='200'){
						layer.msg('success')
						
						function close(){
							
							
							layer.closeAll()
							$('#tradingBuyModal').addClass('hide')
						}
						setTimeout(close,1000)
						clearTimeout(close)
						
						
					}
					}
				});
			})
                    	}
                    }
                   }
                  ],
                  data:d.buy,
                  onClickCell:function(field, value, row, $element){


                  }
                  
			})

			$('#sellTable').bootstrapTable({
				rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center',
                              'border':'none'
                                }
                               };
                      return style;
                  },
                   pagination:true,
                   pageSize:5,

                 
                  columns:[
                   {
                   	field: 'Price',
                    title: 'Price'
                   },
                   {
                   	field: 'Amount',
                    title: 'Amount'
                   },

                   {
                   	field: 'Total',
                    title: 'Total',
                    formatter:function(val,row,index){
                    	return Number(row.Price)*Number(row.Amount)
                    }
                   },
                   {
                   	field: 'operate',
                    title: 'Operate',
                    formatter: sellFormatter,
                    events:{
                    	'click .btnSell':function(ev,value,row,index){
                    		$('#sellFormBox').html('<span class="priceText">'+ row.Price +'</span>')
                   	        sellFormLayer()
   			customValidate($('#sellForm'),{
				"sellAmount":{
					required:true,
					number:true,
                          validNumber:true
				},
				"sellPassword":{
					required:true,
					
//                  validNumber:true
				}
			},{},function(){
				 
				  
		            $.ajax({
					type:"GET",
					url:"http://10.10.10.98:8080/otc/trade.html",
					async:true,
					data:$.param(
						{"entrustId":row.Id,"userId":204 ,"amount":$('#sellAmount').val(),"pwd":$('#sellPassword').val()}
						
					),
					success:function(d){
						var d=JSON.parse(d)
						 console.log(d)
						  resetSellForm()
						  
					if(d.code=='200'){
						layer.msg('success')
						function close(){
							layer.closeAll()
							$('#tradingSellModal').addClass('hide')
						}
						setTimeout(close,1000)
						clearTimeout(close)
						
					}
				},
				   error:function(err){
				   	 console.log(err)
				   }
				}); 
	      })
                    	}
                    }
                    
                    
                   }
                  ],
                  data:d.sell,
                  onClickCell:function(field, value, row, $element){
                   if($($element.context).text()=='Sell'){
//                 	  $('#sellFormBox').html('<span class="priceText">'+ row.Price +'</span>')
//                 	  sellFormLayer()
                   }
                  }
                  
			})

		}
	});
	 } 
     tokenTradeTable(1)
	
	

	
	function historyOrderTable(){
	 	$.ajax({
		type:"get",
//		url:"jsonData/historyOrder.json",
		url:"http://10.10.10.98:8080/otc/getHistoryOrder.html",
		async:true,
		data:$.param({"userId":204}),
		success:function(d){
			  console.log("history" + d)
			  var d=JSON.parse(d)
			 $('#historyOrderTable').bootstrapTable({
			 	rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center',
                              'border':'none',
                               
                                }
                               };
                      return style;
                  },
                   pagination:true,
                   pageSize:5,
                   striped:true,
                columns:[
                  {
                   	field: 'Amount',
                    title: 'Amount'
                   },
                   {
                   	field: 'CoinName',
                    title: 'CoinName'
                   },
                   
                   {
                   	field: 'EntrustType',
                    title: 'EntrustType',
                    formatter:function(value,row,index){
                    	if(value==0){
                    		return 'Buy'
                    	}else if(value==1){
                    		return 'Sell'
                    	}
                    },
                   
                   },
                   {
                   	field: 'Id',
                    title: 'Id'
                   },
                   {
                   	field: 'Price',
                    title: 'Price'
                   },
//                 {
//                 	field: 'Status',
//                  title: 'Status',
//                  formatter:function(value,row,index){
//                  	if(value==0){
//                  		return 'Finished'
//                  	}else if(value==1){
//                  		return 'Canceled'
//                  	}
//                  }
//                 }
                   
                ],
                data:d.orderList
			 })
		}
	});
	 }
    historyOrderTable()	
	
	
	function openOrderTable(){
		  
	 	$.ajax({
		type:"GET",
//		url:"jsonData/open_Order.json",
        url:"http://10.10.10.98:8080/otc/getBuyOrder.html",
		async:true,
		beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                      },  
//		data:JSON.stringify({
//			"userId":"204"
//		 }),
         data:$.param({
			"userId":204
		}),
		success:function(d){
			console.log('openorder ' + d )
			var d=$.parseJSON(d)
			console.log(typeof d)
			$('#openOrderTable').bootstrapTable({
				rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center',
                              'border':'none'
                                }
                               };
                      return style;
                  },
                  pagination:true,
                   pageSize:5,
                  striped:true,
                columns:[
                {
                   	field: 'Id',
                    title: 'Id'
                   },
                  {
                   	field: 'Amount',
                    title: 'Amount'
                   },

                   {
                   	field: 'CoinName',
                    title: 'CoinName'
                   },
                  
                   {
                   	field: 'TradeUser',
                    title: 'Trade User'
                   },
                   {
                   	field: 'EntrustType',
                    title: 'EntrustType',
                     formatter:function(value, row, index){
                     	if(value==0){
                     		return 'Buy'
                     	}else if(value==1){
                     		return 'Sell'
                     	}
                     }
                    
                   },
                   {
                   	field: 'TradeTime',
                    title: 'Trade Time'
                   },

                   
                   {
                   	field: 'Status',
                    title: 'Status',
                    formatter:function(value, row, index){
                    	if(value==0){
                     return "Waitting pay"
                    	}else if(value==1){
                    		
                    	 return  '<button class="btn btn-danger withdrawBtn" id="">Withdraw</button>'
                    	}
                    	
                    },

                    events:{
						'click .withdrawBtn':function(ev,value,row,index){
							
							$.ajax({
								type:"GET",

                                 url:"http://10.10.10.98:8080/otc/confrimReceive.html",

                                data:$.param({"userId":204,"tradeId": row.Id}),
								async:true,
								success:function(d){

									   var d=JSON.parse(d)
									if(d.code==200){
										alert('success')
										$(ev.target.parentElement).next().find('button').addClass('disabledBtn').removeClass('btn-danger').attr('disabled','false')
										$(ev.target.parentElement).html('MoneyReceipt')
										
									}
								}
							});
						},
						'click #payBtn':function(ev,value,row,index){
						   $.ajax({
								type:"get",

                                 url:"http://10.10.10.98:8080/otc/confrimPay.html",
								data:$.param(
							{"userId":204,"tradeId": row.Id}
								),
								async:true,
								success:function(d){
									var d=JSON.parse(d)
									if(d.resStatus==200){
										alert('success')
										$(ev.target.parentElement).html('Paid')
									}
								}
							});
						},
	                       }
                    
                   },
                   {
                   	title:'Operate',
                   	formatter:function(value, row, index){
                   		
                   		if(row.Status==0){
                   			return '<button class="btn btn-danger cancelTradeBtn" >Cancel</button>'
                   		}else if(row.Status==1){
                   		return 	'<button class="btn  disabledBtn" disabled=false id="cancelTradeBtn">Cancel</button>'
                   		}
                   		
                   		
                   	},
                   	events:{
                   		'click .cancelTradeBtn':function(ev,value,row,index){
                   			  
                   			$.ajax({
								type:"get",
//								url:"jsonData/response/cancel_trade_res.json",
                                 url:"http://10.10.10.98:8080/otc/canceltrade.html",
								data:$.param({"tradeId": row.Id}),
								async:true,
								success:function(d){
									var d=JSON.parse(d)
									if(d.code==200){
										
	$(ev.target.parentElement).prev().find('button').addClass('disabledBtn').removeClass('btn-danger').attr('disabled','false')
										$(ev.target.parentElement).html('Canceled')
										alert('success')
//										
									}
								},
								error:function(error){
									console.log(error.status)
								}
							});
                   		}
                   	}
                   	
                   }
                   
                  ],
                  data:d.orderList
			})
		},
		
	    error:function(err){
	 		console.log(err.status)
	 	}
	 	
	 	});
	 	
	                         }
	openOrderTable()
	
	
	$.ajax({
		type:"get",
//		url:"jsonData/response/coinList.json",
        url:"http://10.10.10.98:8080/otc/getCoinList.html",
		async:true,
		beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                      },  

		success:function(d){
			 
			var d=JSON.parse(d)
			console.log(typeof d)
//			   d.coinList.reverse()
			  $.each(d.coinList,function(i,el){
			  	 
			  	 if(d.coinList[i].otcFlag!=0){
			  	 	$('#coinList').append('<li data-coinid='+ d.coinList[i].Id  +'>'+ d.coinList[i].CoinName +'</li>')
			  	 }
           
			  })
              $('#coinList').find('li:first-child').addClass("coinBackColor")
          $('#coinList li').each(function(i,el){
          	
		$(el).click(function(e){
		     $(this).addClass('coinBackColor')
			 $(this).siblings().removeClass('coinBackColor')
			 $('#sellLi').text('Sell  '+$(this).text() )
			  $('#buyLi').text('Buy  '+$(this).text() )
                realTimeMarket($(e.target).attr('data-coinid'))
                tokenTradeTable($(e.target).attr('data-coinid'))
                $('#releaseBtn').attr('data-coinid',$(e.target).attr('data-coinid'))
                              })
	                                            })
		               }
	});

	

	
	 
	  function pendingOrderTable(){
	 	$.ajax({
		type:"get",
//		url:"jsonData/pending_Order.json",  
        url:"http://10.10.10.98:8080/otc/getEntrustByUserId.html",
		async:true,
		data:$.param({"coinName":"28","userId":204}),
		success:function(d){
			console.log( d)
			 var d=JSON.parse(d)
			 
			$('#pendingOrderTable').bootstrapTable({
				rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center',
                              'border':'none'
                                }
                               };
                      return style;
                  },
                  pagination:true,
                   pageSize:5,
                columns:[
                {
                   	field: 'Amount',
                    title: 'Amount'
                   },
                   {
                   	field: 'CoinName',
                    title: 'CoinName'
                   },
                   {
                   	field: 'EntrustType',
                    title: 'EntrustType',
                    formatter:function(value,row,index){
                    	if(value==0){
                    		return 'Buy'
                    	}else if(value==1){
                    		return 'Sell'
                    	}
                    }
                   },
                   {
                   	field: 'Id',
                    title: 'Id'
                   },
                   {
                   	field: 'Price',
                    title: 'Price'
                   },

                   {
                   	field: 'UserName',
                    title: 'User Name'
                   },
                   {
                   	field: '',
                    title: 'Operate',
                     formatter:function(value, row, index){
                      return ['<button class="btn btn-danger cancelPendingOrderBtn">Cancel</button>']
                    },
                    events:{
                    	'click .cancelPendingOrderBtn':function(ev,value,row,index){
                    		$.ajax({
                    			type:"get",
                    			
                    			url:"http://10.10.10.98:8080/otc/cancelEntrust.html",
                    			async:true,
                    			data:$.param({"id":row.Id}),
                    			success:function(d){
                    				   var d=JSON.parse(d)
                    				if(d.code==200){
                    					alert('success')
                    					$(ev.target.parentElement).html('Canceled')
                    				}
                    			}
                    		});
                    	}
                    }
                   }
                   
                ],
                data:d.orderList
			})
		}
	});
	 }
	 pendingOrderTable()
	
	
	function realTimeMarket(coinId){
		
		$.ajax({
		type:"get",
//		url:"jsonData/response/real_timeMarket.json",  
		url:'http://10.10.10.98:8080/otc/currentPrice.html',
		async:true,
		data:$.param({"coinId":coinId}),

		success:function(d){
			 
			 var d=JSON.parse(d)
			$('#ydTop').text(d.yesterdayTop)
			$('#ydBottom').text(d.yesterdayBottom)
			$('#ydVol').text(d.yesterdayVolume)
			$('#tdTop').text(d.todayTop)
			$('#tdBottom').text(d.todayBottom)
			$('#tdVol').text(d.todayVolume)
			$('#priceData').text(d.currentPrice)
		}
	});
	 
	}
     realTimeMarket(1)
	
	 
	customValidate($('#transBuy'),{
		"transBuyPrice":{
			              required:true,
					      number:true,
                          validNumber:true
		},
		"transBuyAmount":{
			              required:true,
					      number:true,
                          validNumber:true
		}
	},{},function(){
		 
		$.ajax({
			type:"get",
//			url:"jsonData/response/release_transaction_res.json",
            url:'http://10.10.10.98:8080/otc/addEntrust.html',
//			beforeSend: function (XMLHttpRequest) {  
//              XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
//                                                    },  
			data:$.param(
//				{"price":$('#transBuyPrice').val(),"entrustType":0,"amount":$('#transBuyAmount').val(),"coinId":$('#releaseBtn').attr('data-coinid'),"userId":204}
             {"price":$('#transBuyPrice').val(),"entrustType":0,"amount":$('#transBuyAmount').val(),"coinId":$('#releaseBtn').attr('data-coinid'),"userId":204}
			),
			async:true,
			success:function(d){
				d=JSON.parse(d)
				resetTransBuy()
				if(d.code==200){
					tokenTradeTable($('#releaseBtn').attr('data-coinid'))
					layer.msg('success')
					function close(){
						layer.closeAll()
						
					}
					setTimeout(close,1000)
					clearTimeout(close)
				}
			}
		});
	})
	customValidate($('#transSell'),{
		"transSellPrice":{
			required:true
		},
		"transSellAmount":{
			required:true
		}
	},{},function(){
		$.ajax({
			type:"get",
//			url:"jsonData/response/release_transaction_res.json",
            url:"http://10.10.10.98:8080/otc/addEntrust.html",
			beforeSend: function (XMLHttpRequest) {  
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                                      },  
			data:$.param(
//				{"price":$('#transSellPrice').val(),"entrustType":0,"amount":$('#transSellAmount').val(),"coinId":$('#releaseBtn').attr('data-coinid'),"userId":204}),
				{"price":$('#transSellPrice').val(),"entrustType":1,"amount":$('#transSellAmount').val(),"coinId":$('#releaseBtn').attr('data-coinid'),"userId":204}),
			async:true,
			success:function(d){
				var d=JSON.parse(d)
				resretTransSell()
				
				if(d.code==200){
					tokenTradeTable($('#releaseBtn').attr('data-coinid'))
					layer.msg('success')
					function close(){
						layer.closeAll()
						
					}
					setTimeout(close,1000)
					clearTimeout(close)
				}
			}
		});
	})
	
	  function tradeOrder(){
	  	$.ajax({
	   	type:"get",
//	   	url:"jsonData/response/trade_order.json",
        url:"http://10.10.10.98:8080/otc/getSellOrder.html",
	   	async:true,
	   	data:$.param({"userId":204}),
	   	success:function(  d){
	   		
              var d=JSON.parse(d)
	   		$('#tradeOrderTable').bootstrapTable({
	   			rowStyle: function(row, index) {
                      var style = {};
                      style = {
                          css: {
                              'text-align': 'center',
                              'border':'none'
                                }
                               };
                      return style;
                  },
                  pagination:true,
                   pageSize:5,
                columns:[
                 {
                 	field: 'CoinName',
                    title: 'CoinName'
                 },
                 {
                 	field: 'EntrustUser',
                    title: 'EntrustUser'
                 },
                 {
                 	field: 'Amount',
                    title: 'Amount'
                 },
                 {
                 	field: 'Price',
                    title: 'Price'
                 },
                 {
                 	field: 'EntrustType',
                    title: 'EntrustType',
                    formatter:function(val,row,index){
                    	if(val==0){
                    		return 'Buy'
                    	}else if(val==1){
                    			return 'Sell'
                    		}
                    }
                 },
                 
                 {
                 	field: 'BankName',
                    title: 'BankName'
                 },
                 {
                 	field: 'BankAccount',
                    title: 'BankAccount'
                 },
                 {
                 	field: 'TradeTime',
                    title: 'TradeTime'
                 },
                 {
                 	field: 'Status',
                    title: 'Status',
                    formatter:function(value, row, index){
                    	if(value==0){
                    		return '<button class="btn btn-success tradeOrderPayBtn" >Pay</button>'
                    	}else if(value==1){
                    		return 'Paid'
                    	}
                    },
                    events:{
                    	'click .tradeOrderPayBtn':function(ev,value,row,index){
                    		
                    		$.ajax({
								type:"GET",
//								url:"jsonData/response/ConfirmToPay.json",
                                 url:"http://10.10.10.98:8080/otc/confrimPay.html",
								
//								data:$.param(
//							{"userId":row.UserId,"tradeId": row.Id}
//								),
                                 data:$.param({"userId":204,"tradeId":row.Id}),
								async:true,
								success:function(d){
									   var d=JSON.parse(d)
									if(d.code==200){
										alert('success')
										$(ev.target.parentElement).html('Paid')
//										console.log($('ev.target').html())
									}
								},
								error:function(err){
									console.log(err.statusText)
								}
							});
                    	}
                    }
                    
                 }
                ],
                data:d.orderList
	   		})
	   	}
	   });
	  }
	  tradeOrder()
	   
	
	
//	$('#dropdownTabMenu li').click(function(){
//		$('#tabTitle').html($(this).text() + "<b class='caret'></b>")
//	})
})
