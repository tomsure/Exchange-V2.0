$(function() {
	var stompClient = null;

	function connect() {
		var socket = new SockJS('/websocket');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {

			init();
			var sessionId = socket._transport.url.split("/")[5];
			//               如果有userid
			if($.cookie('UserId')) {
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
					$.cookie('email', $('#lgEmail').val(), {
						expires: 7,
						path: '/'
					})
					$.cookie('UserId', jQuery.parseJSON(data.body).UserID, {
						path: '/'
					})
					var status = jQuery.parseJSON(data.body).Status
					if(status == 0) {
						$('#myModal').modal('hide')
						$('#loginLi').html('<span>' + $.cookie('email') + '</span>')

						$('#liLast').html('<span id="logout">Logout</span>')

						resetStyle()
						$('#userItem').removeClass('hide')
					} else if(status == -1) {
						alert('Login Error!')
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
				$.removeCookie('UserId', {
					path: '/'
				})
				$.removeCookie('email', {
					path: '/'
				})
				$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
				$('.ulList li:last').html('<a href="Register.html"> Register  </a>')

				location.href = "trade.html"
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
					striped: true,
					pagination: 'true',
					pageSize: 10,
					columns: [{
							field: "CoinName",
							title: "Token"
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
					data: response.AddrList.reverse(),
					onPageChange: function() {
						if(response.AddrList != 0) {
							$('#addressTabale tbody tr').children("td:last-child").html('<button   class="deleteBtn" >Delete</button>')
						}

					},
					onClickCell: function(field, value, row, $element) {
						if($element.text() == 'Delete') {
							$('#deleteModal').modal('show')
							$('#deleteBtn').click(function() {
								$('#deleteModal').modal('hide')
								stompClient.send("/ws/assets/deleteWalletAddr", {}, JSON.stringify({ //删除出金地址请求
									"Tag": 94208,
									"UserID": parseInt($.cookie('UserId')),
									"CoinName": $element.parent().children('td:first-child').text(),
									"WithdrawAddr": $element.parent().children('td:nth-child(2)').text(),
									"RequestID": RequestId,
									"SessionID": sessionId
								}))

								location.reload()
								//                             getwithdrawalAddr()
								//                          $('#addressTabale').bootstrapTable('load',response.AddrList)
								//				 if(response.AddrList!=0){
								//				               	$('#addressTabale tbody tr').children("td:last-child").html('<button   class="deleteBtn" >Delete</button>')
								//				               }      

							})

						}

					}

				})
				$('#addressTabale').bootstrapTable('load', response.AddrList)
				$('#selectCoin').html('')
				$('#selectCoin').on('change', function() {
					$('#addressTabale tbody tr').children("td:first-child").text($('#selectCoin option:selected').text())
				})

				if(response.AddrList != 0) {
					$('#addressTabale tbody tr').children("td:last-child").html('<button   class="deleteBtn" >Delete</button>')
				}

			});
			stompClient.subscribe('/gateway/c2cCoinList-' + sessionId, function(data) {
				var response = jQuery.parseJSON(data.body);
//				var coinlist = document.getElementById("coinList") //CoinList
				$.each(response.CoinList, function(i, el) {
//					coinlist.innerHTML += '<option>' + response.CoinList[i].CoinName + '</option>'
               $('#dropdownMenuList').append('<li><a href="#">'+response.CoinList[i].CoinName+ '</a></li>')

				})
				$('#dropdownMenuList').prepend('<li><a>All</a></li>')
				 $('.coinListNav .dropdown .dropdown-menu li a').click(function(){
    	 $('#dropdownMenu2').html($(this).text() + "<b class='caret caret pull-right'  ></b>")
    })
			})
			stompClient.subscribe('/gateway/addWithdrawalAddr-' + sessionId, function(data) {
				var response = jQuery.parseJSON(data.body)
				if(response.Status == 0) {
					//                      layer(4,'Add Withdrawal address successfully!',function(index){
					//               	      var layer=layui.layer
					//               	      layer.close(index)
					//                       })

					alert("Add Withdrawal address successfully!")
					$('#addressArea').val('')
					$('#nodeText').val('')
					getwithdrawalAddr()
					//            	          	  location.reload()
				} else if(response.Status == -1) {
					alert("The address already exist!")
					$('#addressArea').val('')
					$('#nodeText').val('')
				}

			})
//			$('#addBtn').click(function() {
//				var reg=/^[0-9A-Za-z]$/
//				if($('#addressArea').val() == '') {
//					layerTips('Please enter the withdrawal address.', '#addressArea')
//
//				} else if($('#nodeText').val() == '') {
//					layerTips('Please enter nodeText!', '#nodeText')
//				}else if(reg.test($('#addressArea').val())){
//					alert('Please enter the correct characters')
//				}else if(reg.test($('#nodeText').val())){
//					alert('Please enter the correct characters!')
//				}else {
//					sendaddWalletAddr()
//				}
//			})
          customValidate($('#addAddressForm'),{
          	"addressArea":{
          		required:true,
          		rightAddressText:true
          	},
          	"nodeText":{
          		required:true,
          		rightAddressText:true
          	}
          },{},function(){
          	sendaddWalletAddr()
          	$('.glyphicon-ok').remove()
          })
			
			
			

			function init() {
				stompClient.send("/ws/c2c/trade", {}, JSON.stringify({ //用来获取比币种列表
					'RequestID': 'testtesttest',
					'Tag': 20497,
				}));

				getwithdrawalAddr()
			}

			function getwithdrawalAddr() {
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
					"username": $.trim($('#lgEmail').val()),
					"loginpassword": $.trim($('#lgPassword').val()),
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

                    "CoinName":$('#dropdownMenu2').text(),
					// TODO Get these info from the table which selected
					"WithdrawAddr": $('#addressArea').val(),
					"Note": $('#nodeText').val(),
					"RequestID": RequestId
				}))
			}

		})
	}
	connect()
   
})