$(function() {
    //  $('#coinList').html('')
//    $('#coinList').html('')
    var userId = null;


    var stompClient = null;

    function connect() {
        var socket = new SockJS('/websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            var sessionId = socket._transport.url.split("/")[5];
            //               如果有userid
             if($.cookie('UserId')){
             	    $('#loginLi').html('<span>' + $.cookie('email') + '</span>')
                       $('#liLast').html('<span id="logout">Logout</span>')
                       $('#userItem').removeClass('hide')
                      }
            customValidate($('#lgPassWordForm'), {
                "lgEmail": {
                    required: true,
                    email: true
                },
                "lgPassword": {
                    required: true
                }
            }, {}, function() {
                stompClient.subscribe('/gateway/login-' + sessionId, function(data) {
                      $.cookie('email',$('#lgEmail').val())
                    var status = jQuery.parseJSON(data.body).Status
                    if (status == 0){

                    	$('#myModal').modal('hide')
                           
                        $('#loginLi').html('<span>' + $.cookie('email') + '</span>')
                           
                        $('#liLast').html('<span id="logout">Logout</span>')

                        
                        resetStyle()
                        $('#userItem').removeClass('hide')
                        } else if (status == -1) {
                        alert('login error!')
                                                 }
                })
                sendLoginData() //login	
       
            })
            stompClient.subscribe('/gateway/logout-' + sessionId, function(data) {
                  	var status = jQuery.parseJSON(data.body).Status
//                	      alert(data.body)
                  	   $('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
                  	   
                        $('.ulList li:last').html('<a href="Register.html">  Register  </a>')
                         $('#userItem').addClass('hide')
                  })
                 $('#liLast').click(function() {
                     sendLogoutData() //logout
                        $.removeCookie('UserId')
                        $('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
               $('.ulList li:last').html('<a href="Register.html">  Register  </a>')
                    })
                    
            stompClient.subscribe('/gateway/userHistoricalOrders-' + sessionId, function(data) {
                var historyTrading = jQuery.parseJSON(data.body); 

                   
                    $.each(historyTrading.OrderList,function(i,el){
         	      	    
         	 if(historyTrading.OrderList[i].ordertime==0){
         	 	 historyTrading.OrderList[i].ordertime=0
         	 }else{
         	 	historyTrading.OrderList[i].ordertime=timestampToTime(historyTrading.OrderList[i].ordertime)
         	 }
         	  if(historyTrading.OrderList[i].lasttradertime==0){
         	  	historyTrading.OrderList[i].lasttradertime=0
         	  }else{
         	  	historyTrading.OrderList[i].lasttradertime=timestampToTime(historyTrading.OrderList[i].lasttradertime)
         	  }
         	  
           	 if(historyTrading.OrderList[i].canceltime==0){
         	 	historyTrading.OrderList[i].canceltime=0
         	 }else{
         	 	historyTrading.OrderList[i].canceltime=timestampToTime(historyTrading.OrderList[i].canceltime)
         	 }
         	           	           	  
         	  if(historyTrading.OrderList[i].Transtype==1){
         	  	historyTrading.OrderList[i].Transtype='Buy'
         	  }else if(historyTrading.OrderList[i].Transtype==2){
         	  	historyTrading.OrderList[i].Transtype='Sell'
         	  }
         	  
         	  if(historyTrading.OrderList[i].status==0){
         	  	   historyTrading.OrderList[i].status='Canceled'
         	  }else if(historyTrading.OrderList[i].status==3){
         	  	  historyTrading.OrderList[i].status='Completed'
         	  }
         	  
         	  
         	      })
                   
                   
                   
                   
                   
                   
                	$('#tradingHistoryTabale').bootstrapTable({
                		  
                          rowStyle: function(row, index) {
                              var style = {};
                              style = {
                                  css: {
                                      'text-align': 'center'
                                  }
                              };
                              return style;
                          },
                          columns: [{
                                  field: 'id',
                                  title: 'Order ID'
                              },  {
                                  field: 'Transtype',
                                  title: 'Trans Type'
                              }, {
                                  field: 'price',
                                  title: 'Price'
                              },
                              {
                                  field: 'amount',
                                  title: 'Amount'
                              },
                              {
                                  field: 'tradeamount',
                                  title: 'Trade Amount'
                              },
                              
                              {
                                  field: 'ordertime',
                                  title: 'Order Time'
                              },
                              {
                                  field: 'lasttradertime',
                                  title: 'Last Traded Time'
                              },
                              {
                                  field: 'canceltime',
                                  title: 'Cancel Time'
                              }, {
                                  field: 'status',
                                  title: 'Status'
                              }
                          ],
                          data:historyTrading.OrderList,
                      });
                	
                	$('#tradingHistoryTabale').bootstrapTable('load', historyTrading.OrderList);
            });
            
            stompClient.subscribe('/gateway/getAllTokens-' + sessionId, function(data) {  
            	var response = jQuery.parseJSON(data.body);
			   var coinlist=document.getElementById("coinList")  //CoinList
			     $.each(response.SymbolList,function(i,el){
			     	 coinlist.innerHTML+='<option>'+ response.SymbolList[i].Symbol +'</option>'
			     	 
			     })
            })
            
            init()
            
             /**
             * 时间段选择
             */
            SelectTimePeriod();
            
            $('#searchBtn').click(function() { //点击搜索
            	// 格式化时间
            	var beginDate = $('#startDate').val();
            	var endDate = $('#endDate').val();
//            	alert(beginDate);
            	(beginDate == "")? beginTime=0 : beginTime= Date.parse(new Date(beginDate+" 00:00:00"))/1000;
            	(endDate =="")? endTime=0 : endTime = Date.parse(new Date(endDate+ " 23:59:59"))/1000;
            	//发起請求
            	var symbol = $('#coinList option:selected').val();
                sendSearchData(beginTime,endTime,symbol);
//                window.location.reload(true);
            })

            function init() {
            	stompClient.send("/ws/token/reportService",  {}, JSON.stringify({  //用来获取比币种列表
					'RequestID' : 'testtesttest',
					'Tag' : 24613,
	            }));
                sendSearchData(0,0,"All");
            }

            function sendSearchData(beginTime,endTime,symbol) { //search
                stompClient.send("/ws/token/reportService", {}, JSON.stringify({
                    "Tag": 24577,
                    "UserID": parseInt($.cookie('UserId')),
                    "Symbol": symbol,
                    "OrderType" : 0,
                    "BeginTime": beginTime,
                    "EndTime": endTime,
                    "RequestID": RequestId
                }));
                
            }

            function sendModalTableData() {
                stompClient.send("/ws/trade/home", {}, JSON.stringify({ //弹窗表单请求
                    "Tag": 111222,
                    "UserID": parseInt($.cookie('UserId')),
                    "OrderID": 12233,
                    "SessionId": sessionId,
                    "RequestID": RequestId,
                    "WsId": 1
                }))
            }

            function sendLoginData() {
                stompClient.send("/ws/user/login", {},JSON.stringify({ //login
                    "Tag": 8193,
                    "username": $('#lgEmail').val(),
                    "loginpassword": $('#lgPassword').val(),
                    "srcip": "192.168.0.1",
                    "RequestID": RequestId
                }));

            }

            function sendLogoutData() {

                stompClient.send("/ws/user/logout", {}, JSON.stringify({ //logout
                    "Tag": 131089,
                    "srcip": "192.168.0.1",
                    "RequestID": RequestId,
                    "UserID": parseInt($.cookie('UserId'))
                }));
            }

        })
    }

    connect()
})