$(function() {
    var stompClient = null;
  
    function connect() {
        var socket = new SockJS('/websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
        	init();
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
                  	   $('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>') 
                  	   $('.ulList li:last').html('<a href="Register.html">  Register  </a>')
                         $('#userItem').addClass('hide')
                  })
                 $('#liLast').click(function() {
                     sendLogoutData() //logout
                         $.removeCookie('UserId')
                         $('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
               $('.ulList li:last').html('<a href="Register.html"> Register  </a>')
                    })

            
            stompClient.subscribe('/gateway/withdrawalAddr-' + sessionId, function(data) {
                var response = jQuery.parseJSON(data.body);
                $('#addressTabale').bootstrapTable({
                    rowStyle: function(row, index) {
                        var style = {};
                        style = {
                            css: {
                                'text-align': 'center'
                            }
                        };
                        return style;
                    },
                    striped:true,
                  pagination:'true',
                  pageSize:6,
                    columns: [
                    	{
                            field: "CoinName",
                            title: "CoinName"
                        },
                        {
                            field: "addr",
                            title: "Address"
                        },
                        {
                            field: "Notes",
                            title: "Notes"
                        },
                        {
                            field: "button",
                            title: ""
                        }
                    ],
                    data: response.AddrList,
                    onPageChange:function(){
                  	  if(response.AddrList!=0){
               	$('#addressTabale tbody tr').children("td:last-child").html('<button   class="deleteBtn" >Delete</button>')
               }
                  	
                    },
                    onClickCell: function(field, value, row, $element) {
                        if ($element.text() == 'Delete') {

                            //      			      	 alert($element.parent().children('td:first-child').text())  获取点击按钮当前行的第一个子元素
                            $('#deleteModal').modal('show')
                            $('#deleteBtn').click(function() {
                            	$('#deleteModal').modal('hide')

                                stompClient.send("/ws/assets/deleteWalletAddr", {}, JSON.stringify({ //删除出金地址请求
                                    "Tag": 94208,
                                    "UserID": parseInt($.cookie('UserId')),
                                    "CoinName": $element.parent().children('td:first-child').text(),
                                    "WithdrawAddr": $element.parent().children('td:nth-child(2)').text(),
                                    "RequestID": RequestId,
                                    "SessionID":sessionId
                                }))
                                  location.reload()

                            })

                        }

                    }

                })
                $('#selectCoin').html('')
                $('#selectCoin').on('change', function() {
                    $('#addressTabale tbody tr').children("td:first-child").text($('#selectCoin option:selected').text())
                })

                if(response.AddrList!=0){
               	$('#addressTabale tbody tr').children("td:last-child").html('<button   class="deleteBtn" >Delete</button>')
               }

            });
              stompClient.subscribe('/gateway/c2cCoinList-' + sessionId, function(data) {
            	var response = jQuery.parseJSON(data.body);
			   var coinlist=document.getElementById("coinList")  //CoinList
			     $.each(response.CoinList,function(i,el){
			     	 coinlist.innerHTML+='<option>'+ response.CoinList[i].CoinName +'</option>'
			     	 
			     })
            })

             
              stompClient.subscribe('/gateway/addWithdrawalAddr-' + sessionId, function(data){
              	      var response=jQuery.parseJSON(data.body)
              	          if(response.Status==0){
//            	          	  alert("add address success!")
              	          	  location.reload()
              	          }else if(response.Status==-1){
              	          	  alert("error!")
              	          }
              	        
              })
                                                        
            $('#addBtn').click(function() {
                if ($('#addressArea').val() == '') {
                    alert('Reminder:Please enter an address! ')
                } else if ($('#nodeText').val() == '') {
                    alert('Reminder:Please enter nodeText! ')
                } else {
                    stompClient.subscribe('/gateway/addWithdrawalAddr-' + sessionId, function(data) {                   
                    	 
                        var status = jQuery.parseJSON(data.body).Status
                        if (status == 0) {
                            alert('Add Address Successfully')
                            //	        	  	 	    	 $('#addressTabale').
                            window.location.reload()
                        } else if (status == -1) {
                            alert('Add Address Error')
                        }

                    })
                    sendaddWalletAddr()

                }
            })

            function init() {
            	 stompClient.send("/ws/c2c/trade",  {}, JSON.stringify({  //用来获取比币种列表
				'RequestID' : 'testtesttest',
				'Tag' : 20497,
	            }));

            	stompClient.send("/ws/assets/withdrawalAddr", {}, JSON.stringify({ //login
                    "Tag": 151552,
                    "UserID": parseInt($.cookie('UserId')),
                    "CoinName": 'ALL',
                    "RequestID": RequestId
                }));
            }

            function sendLoginData() {
                stompClient.send("/ws/user/login", {}, JSON.stringify({ //login
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



//addWithdrawalAddr
   
            function sendaddWalletAddr() {
                stompClient.send("/ws/assets/addWalletAddr", {}, JSON.stringify({
                    "Tag": 90112,
                    "UserID": parseInt($.cookie('UserId')),
                    "CoinName": $('#coinList option:selected').val(),
                    // TODO Get these info from the table which selected

                    "WithdrawAddr":$('#addressArea').val(),
                    "Note": $('#nodeText').val(),
                    "RequestID": RequestId
                }))
            }

         

        })
    }
    connect()

})