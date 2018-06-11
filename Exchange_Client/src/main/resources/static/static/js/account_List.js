$(function() {

    var stompClient = null;
    var userId = null;
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
                        $.cookie('UserId',jQuery.parseJSON(data.body).UserID)
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

            stompClient.subscribe('/gateway/userAsserts-' + sessionId, function(data) {

                var accountList = jQuery.parseJSON(data.body)
                $('#accountList').bootstrapTable({
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
                            field: 'CoinName',
                            title: 'Token'
                        },
                        {
                            field: 'Total',
                            title: 'Total'
                        },
                        {
                            field: 'Available',
                            title: 'Available'
                        },
                        
                        {
                            field: 'Frozen',
                            title: 'Frozen'
                        },
                        
                        {
                            field: 'Action',
                            title: 'Action'
                        },
                        {
                            field: 'Trade',
                            title: 'Trade'
                        }

                    ],
                    onPageChange:function(){
       
                  },
                    data: accountList.SearchAsserts

                })

                $('#accountList tbody tr').children("td:nth-child(5)").html('<div class="btnBox"> <button class="depositBtn"  >Deposit</button><button class="withdrawBtn" >Withdrawal</button> </div>')
                $('#accountList tbody tr').children("td:last-child").html('<button class="refreshBtn" id="">Go</button>')

                var trRow = $('#accountList tbody tr')
                var totalArr = []
                var available = []
                $.each(trRow, function(i, el) {
                    totalArr.push($(el).find('td:nth-child(2)').text())
                    available.push($(el).find('td:nth-child(3)').text())
                }) //将需要的数组的第二个td追加到一个数组中
                //	        	               alert(arr)

                function getSum(total, num) { //高阶计算
                    return total + Math.round(num);
                }

                $('#totalAssets').text(totalArr.reduce(getSum, 0)) //执行累加
                $('#coinavAilable').text(available.reduce(getSum, 0))
                $('.refreshBtn').each(function(i, el) {

                    $(el).click(function(e) {
//                      stompClient.subscribe('/gateway/userAsserts-' + sessionId, function(data) {
//                          //							    	   
//                          
//
//                      })

                        sendGoToTradePageData()
                       function sendGoToTradePageData() {
                stompClient.send("/ws/token/home", {}, JSON.stringify({
                    "UserID": parseInt($.cookie('UserId')),
                    "Token": $(e.target).parents('tr').find('td').eq(0).text(),
                    "RequestID": RequestId
                }))
            }
                       location.href = 'trade.html'

                    })
                })

                $('.depositBtn').click(function(e) {
                	     $('#depositModal').modal('show')
                	  
                	  
                	  
                stompClient.send("/ws/assets/deposit", {}, JSON.stringify({
                    "Tag": 86016,
                    "UserID": parseInt($.cookie('UserId')),
                    "CoinName": $(e.target).parents('tr').find('td').eq(0).text(),
                    'SessionID':sessionId,
                    "RequestID": RequestId
                }))
                    stompClient.subscribe('/gateway/depositAddr-' + sessionId, function(data) {
                        var data = jQuery.parseJSON(data.body)
                       $('#depositModal').modal('show')
                        $('#addressText').text(data.DepsositAddr)
                        $('#qrCodeBox').css('display', 'block')

                        $('#qrCodeImg').attr('src', data.imgAddr)

                    })
                    
           

                })
                $('.withdrawBtn').click(function(e) {
                	 
                    $('#withdrawModal').modal('show')
                    
                    customValidate($('#withdrawForm'), {
                            "address": {
                                required: true
                            },
                            "amount": {
                                required: true
                            },
                            "fee": {
                                required: true
                            },
                            "password": {
                                required: true
                            },
                            "smsCode": {
                                required: true
                            }
                        }, {},
                        function() {
                        	 $('#withdrawModal').modal('hide')       
                            stompClient.subscribe('/gateway/withdrawal-' + sessionId, function(data) {
                            	  
                                var data = jQuery.parseJSON(data.body)
                                if (data.Status == 0) {
                                    alert('success')
                                     
                                } else if (data.Status == -1) {
                                    alert('error')
                                }
                            })
                            sendWithdrawData()
                               
                             function sendWithdrawData() {
//                           	alert(Number($('#amount').val()))
	                stompClient.send("/ws/assets/withdrawal", {}, JSON.stringify({
	                    "Tag": 135168,
	                    "UserID": parseInt($.cookie('UserId')),
	                    "CoinName": $(e.target).parents('tr').find('td').eq(0).text(),
	                    "Amount": Number($('#amount').val()),
                        
	                    "TradePwd": $('#transactionPassword').val(),
	                    "DestAddr": $('#address').val(),
	                    "SmsCode": $('#smsCode').val(), // 手机验证码
	                    "RequestID": RequestId
	                }))
            }
                                                                                    
//                          $('#trade_sellForm')[0].reset()
                            resetStyle()
                        }
                    )
                })

            });

              $('#sendBtn').click(function(){
           	 
           	  sendSms()
           	  sendSomething($(this))
           })
            init()


            



            function init() {
                stompClient.send("/ws/assets/home", {}, JSON.stringify({
                	"Tag" : 24581,
                    "UserID": parseInt($.cookie('UserId')),
                    "RequestID":'11123',
                }));
                
            }


           

           

            function sendSms(){
	stompClient.send("/ws/message/sendSms",{}, JSON.stringify({
		'Tag' : 69634,
		'RequestID' : RequestId,
		'UserID' : parseInt($.cookie('UserId')),
		'MobilePhone' : $('#changedPhoneNumber').val()
	}));
}

            function sendLoginData() {
                stompClient.send("/ws/user/login", {}, JSON.stringify({
                    "Tag": 8193,
                    "username": $('#lgEmail').val(),
                    "loginpassword": $('#lgPassword').val(),
                    "srcip": "192.168.0.1",
                    "RequestID": RequestId
                }));
            }

            function sendLogoutData() {

                stompClient.send("/ws/user/logout", {}, JSON.stringify({
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