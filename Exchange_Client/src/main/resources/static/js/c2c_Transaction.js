var stompClient = null;

var sessionId;

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		sessionId = socket._transport.url.split("/")[5];
		//               如果有userid
		if($.cookie('UserId')) {
			$('#loginLi').html('<span>' + $.cookie('email') + '</span>')
			$('#liLast').html('<span id="logout">Logout</span>')
			$('#userItem').removeClass('hide')
		}
		//
		//如果没有userID
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
				// TODO store userId
				$.cookie('email', $('#lgEmail').val(), {
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
		$('#liLast').click(function() { //logout

			sendLogoutData() //send logout Data
			$.removeCookie('UserId', {
				path: '/'
			})
			$.removeCookie('email', {
				path: '/'
			})

			$('#loginLi').html('<a id="loginBtn" data-toggle="modal" data-target="#myModal"> Login </a>')
			$('.ulList li:last').html('<a href="Register.html">  Register  </a>')
			$('#userItem').addClass('hide')
			$('#c2cUserOpenOrderTable').find('tbody').html('')
			$('#c2cUserPendingOrderTable').find('tbody').html('')
			$('#c2cUserHistoryOrderTable').find('tbody').html('')

			$('#userItem').removeClass('hide')
			console.log($.cookie('UserId'))
		})

		//

		init();

		stompClient.subscribe('/gateway/c2cGetOpenOrders-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			$('#buyBox').find('tbody').html('')
			$('#sellBox').find('tbody').html('')
			$.each(response.Buy, function(i, el) {
				var buyBox = document.getElementById("buyBox")
				var buyBoxTbody = document.getElementById("buyBoxTbody")

				buyBoxTbody.innerHTML += '<tr><td>' + response.Buy[i].Id + '</td>' + '<td>' + response.Buy[i].Price + '</td>' + '<td>' + response.Buy[i].Amount + '</td>' + '<td>' + response.Buy[i].CoinName + '</td>' + '<td>' + response.Buy[i].Amount * response.Buy[i].Price + '</td>' + '<td><button class="buttonColor tradingButton cancelBtn cancelAll" >Buy</button></td>' + '</tr>'
			});

			$('#buyBox tbody tr button').each(function(i, el) {
				$(el).click(function(e) {
					$('#buyModal').modal('show')
					$('#buyCoinPrice').text($(e.target).parents('tr').find('td:nth-child(2)').text())
					$('#buyCoinText').text($(e.target).parents('tr').find('td:nth-child(4)').text())
					$('#buyCoinBtn').text($(e.target).parents('tr').find('td:nth-child(4)').text())
				})
			})

			$.each(response.Sell, function(i, el) {
				var sellBox = document.getElementById("sellBox")
				var sellBoxTbody = document.getElementById("sellBoxTbody")
				sellBoxTbody.innerHTML += '<tr><td>' + response.Sell[i].Id + '</td>' + '<td>' + response.Sell[i].Price + '</td>' + '<td>' + response.Sell[i].Amount + '</td>' + '<td>' + response.Sell[i].CoinName + '</td>' + '<td>' + response.Sell[i].Amount * response.Sell[i].Price + '</td>' + '<td><button class="buttonColor tradingButton cancelBtn cancelAll" >Sell</button></td>' + '</tr>'
			});
			$('#sellBox tbody tr button').each(function(i, el) {
				$(el).click(function(e) {

					$('#sellModal').modal('show')

					$('#sellCoinPrice').text($(e.target).parents('tr').find('td:nth-child(2)').text())

					$('#sellCoinText').text($(e.target).parents('tr').find('td:nth-child(4)').text())
					$('#sellCoinBtn').text($(e.target).parents('tr').find('td:nth-child(4)').text())

				})
			})
		});
		stompClient.subscribe('/gateway/c2cGetUserPendingOrder-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			var historyOrderTable = document.getElementById("c2cUserPendingOrderTable")
			var openOrderTbody = document.getElementById("openOrderTbody")
			$(historyOrderTable).find('tbody').html('')
			$.each(response.OrderList, function(i) {
				openOrderTbody.innerHTML += '<tr><td>' + response.OrderList[i].Id + '</td>' + '<td>' + response.OrderList[i].CoinName + '</td>' + '<td>' + response.OrderList[i].Price + '</td>' + '<td>' + response.OrderList[i].Amount + '<td>' + response.OrderList[i].Status + '</td></tr>'
			})

		});
		stompClient.subscribe('/gateway/c2cGetUserOpenOrder-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			var c2cUserOpenOrderTable = document.getElementById("c2cUserOpenOrderTable")
			var pendingOrderTbody = document.getElementById("pendingOrderTbody")
			$(c2cUserOpenOrderTable).find('tbody').html('')
			$.each(response.OrderList, function(i, el) {

				pendingOrderTbody.innerHTML += '<tr><td>' + response.OrderList[i].Id + '</td>' + '<td>' + response.OrderList[i].CoinName + '</td>' + '<td>' + response.OrderList[i].Price + '</td>' + '<td>' + response.OrderList[i].Amount + '<td>' + response.OrderList[i].Status + '</td></tr>'
			})

		});
		stompClient.subscribe('/gateway/c2cGetUserHistoricalOrder-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			var historyOrderTable = document.getElementById("c2cUserHistoryOrderTable")
			var historyOrderTbody = document.getElementById("historyOrderTbody")
			$(historyOrderTable).find('tbody').html('')
			$.each(response.HistoryMarket, function(i) {
				historyOrderTbody.innerHTML += '<tr><td>' + response.HistoryMarket[i].Id + '</td>' + '<td>' + response.HistoryMarket[i].CoinName + '</td>' + '<td>' + response.HistoryMarket[i].Price + '</td>' + '<td>' + response.HistoryMarket[i].Amount + '<td>' + response.HistoryMarket[i].TradeStatus + '</td></tr>'
			})

		});
		stompClient.subscribe('/gateway/c2cCoinList-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			var coinlist = document.getElementById("coinlist") //CoinList
			$('#coinlist').html('')
			$.each(response.CoinList, function(i, el) {
				coinlist.innerHTML += '<li ><a href="#' + response.CoinList[i].CoinName + '"' + 'data-toggle="tab">' + response.CoinList[i].CoinName + '</li>'

			})
			$('#coinlist').find('li a').click(function(e) {
				$('#selfBuyBtn').text($(e.target).text())
				$('#selfSellBtn').text($(e.target).text())
				$('#selfSellTabText').text($(e.target).text())
				$('#selfBuyTabText').text($(e.target).text())

				stompClient.send("/ws/c2c/home", {}, JSON.stringify({
					'RequestID': 'testtesttest',
					'UserID': $.cookie('UserId'),
					'Token': $(e.target).text(),
				}));

			})
		});
		stompClient.subscribe('/gateway/c2cCoinMarket-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			$('#newPrice').html(response.CurrentPrice);
			$('#ydTop').html(response.Yesterday.Top);
			$('#tdTop').html(response.Today.Top);
			$('#ydBottom').html(response.Yesterday.Bottom);
			$('#tdBottom').html(response.Today.Bottom);
			$('#ydVol').html(response.Yesterday.Volume);
			$('#tdVol').html(response.Today.Volume);
		});
		stompClient.subscribe('/gateway/c2cCoinHistoricalMarket-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
			var coinMarketTab = document.getElementById("coinMarketTab")

		});

		otcBuyform(function() {
			buy()
			trade(0)
			$('#buyModal').modal('hide')
		})
		otcSellForm(function() {
			sell()
			trade(1)
			$('#sellModal').modal('hide')
		})
		entrustBuyForm(function() {
			buy()
			entrust(0)
			$('#releaseModal').modal('hide')
		})
		entrustSellFrom(function() {
			sell()
			entrust(1)
			$('#releaseModal').modal('hide')
		})

		$('#buyAmount').blur(function() {

			$('#otcBuyTotal').text(Number($('#buyPrice').val()) * Number($('#buyAmount').val()))

		})
		$('#buyPrice').blur(function() {

			$('#otcBuyTotal').text(Number($('#buyPrice').val()) * Number($('#buyAmount').val()))

		})

		$('#sellPrice').change(function(e) {

			$('#otcSellTotal').text(Number($('#sellPrice').val()) * Number($('#sellAmount').val()))

		})
		$('#sellAmount').blur(function() {

			$('#otcSellTotal').text(Number($('#sellPrice').val()) * Number($('#sellAmount').val()))

		})

		stompClient.subscribe('/gateway/c2cTrade-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
		});
		stompClient.subscribe('/gateway/c2cCancelTrade-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
		});
		stompClient.subscribe('/gateway/c2cPay-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
		});
		stompClient.subscribe('/gateway/c2cPayConfirm-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);

		});
		stompClient.subscribe('/gateway/c2cCancelOpenOrder-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
		});
		stompClient.subscribe('/gateway/c2cOpenOrder-' + sessionId, function(data) {
			var response = jQuery.parseJSON(data.body);
		});
	});
}

function init() {
	stompClient.send("/ws/c2c/home", {}, JSON.stringify({
		'RequestID': 'testtesttest',
		'UserID': $.cookie('UserId'),
		'Token': null,
	}));
}

function trade(type) {
	var amount;
	if(type == 0) {
		amount = $('#buyAmount').val();
	} else {
		amount = $('#sellAmount').val();
	}
	stompClient.send("/ws/c2c/trade", {}, JSON.stringify({
		'Tag': 20773,
		'requestID': '123456789',
		'entrustId': $(this).parents('tr').find('td').eq(0).text(),
		'userID': $.cookie('UserId'),
		'amount': amount
	}));
}

function cancelPendingOrder() {
	stompClient.send("/ws/c2c/cancelPendingOrder", {}, JSON.stringify({
		'Tag': 20739,
		'RequestID': 'testtesttest',
		'UserID': $.cookie('UserId'),
		'tradeId': $(this).parents('tr').find('td').eq(0).text(),
	}));
}

function entrust(type) {
	var price;
	var amount;
	if(type == 0) {
		price = $('#buyPrice').val();
		amount = $('#entrustBuyAmount').val();
	} else {
		price = $('#sellAmount').val();
		amount = $('#entrustSellAmount').val();
	}
	stompClient.send("/ws/c2c/entrust", {}, JSON.stringify({
		'Tag': 20481,
		'RequestID': 'testtesttest',
		'UserID': $.cookie('UserId'),
		'price': price,
		'entrustType': type,
		'amount': amount,
		'coinName': $('#selfBuyTabText').text()
	}));
}

function sendLoginData() {
	stompClient.send("/ws/user/login", {}, JSON.stringify({ //login
		"Tag": 8193,
		"username": $.trim($('#lgEmail').val()),
		"loginpassword": $.trim($('#lgPassword').val()),
		"srcip": "192.168.0.1",
		"UserID": parseInt($.cookie('UserId')),
		"RequestID": RequestId
	}));
}

function sendLogoutData() {
	stompClient.send("/ws/user/logout", {}, JSON.stringify({ //logout
		"Tag": 131089,
		"UserID": parseInt($.cookie('UserId')),
		"srcip": "192.168.0.1",
		"RequestID": RequestId
	}));
}

function buy() {
	alert("buy");
}

function sell() {
	alert("sell");
}

$(function() {
	connect();

});