$(function() {
	function history(data){
		$.ajax({
		type: "POST",
		url: "http://47.75.72.244:8080/historyOrder/list",
		async: true,
		beforeSend: function(XMLHttpRequest) {
			XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
		},
		data: JSON.stringify(data),

		success: function(d) {
			 $('#historyOrderTable').bootstrapTable('destroy')
			$('#historyOrderTable').bootstrapTable({
				pagination: true,
				pageSize: 3,
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
						checkbox: true,
					},
					{
						field: 'orderId',
						title: 'Order ID',
					},
					{
						field: 'userName',
						title: 'User Name',
					},
					{
						field: 'cryptoPair',
						title: 'Crypto.',
					},
					{
						field: 'type',
						title: 'Type',
					},
					{
						field: 'status',
						title: 'Status',
					},
					{
						field: 'price',
						title: 'Price',
					},
					{
						field: 'qty',
						title: 'Qty',
					},
					{
						field: 'amount',
						title: 'Amount',
					},
					{
						field: 'time',
						title: 'Time',
					},
					{
						field: 'status',
						title: 'Operate',
						formatter: function() {
							return ['<button class="btn btn-info viewBtn" >View Details</button>']

						},
						events: {
							'click .viewBtn': function() {

							}
						}

					}

				],
				data: d,
				onClickCell: function(field, value, row, $element) {
					if($($element.context).text() == 'View Details') {
						layer.open({
                            type: 1,
							shade: false,
							title: ['KYC Authentication', 'font-size:18px;text-align:center;'],
							area: ['550px', '700px'],
							content: $('#historyTableModalBox'),
							btn: ['Approved', 'Rejected'],
							yes: function(index, layero) {
                                 layer.close(index)
							},
                            cancel: function() {},
							success: function(layero, index) {
								 console.log(row.orderId)
								$('#historyTableModalBox').removeClass('hide')
								$.ajax({
									type: "GET",
									url: "http://47.75.72.244:8080/historyOrder/details",
									beforeSend: function(XMLHttpRequest) {
										XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
									},

									data:JSON.stringify({"orderId":0}),
									async: true,
									success: function(d) {
										 console.log(d)
                                       $('#historyTableLayer').bootstrapTable({
									rowStyle: function(row, index) {
										var style = {};
										style = {
											css: {
												'text-align': 'center'
											}
										};
										return style;
									},
									checkBox: true,
									columns: [{
											field: 'coinName',
											title: 'Trade Order ID'
										},
										{
											field: 'coinName',
											title: 'User Name'
										},
										{
											field: 'coinName',
											title: 'Price'
										},
										{
											field: 'coinName',
											title: 'Qty'
										},
										{
											field: 'coinName',
											title: 'Amount'
										},
										{
											field: '',
											title: 'Time'
										},

									],
									     data:d
								})
									}
								});
								
							}
						});

					}
				}

			})
		},
		error: function(err) {
			console.log(err)
		}
	});
	}
	
	history({
			"orderId": 0,
			"userName": "",
			"crypto": "",
			"type": 0,
			"status": -1
		})
	$('#searchBtn').click(function(){
		history({
			"orderId":Number($('#orderId').val()),
			"userName":$('#userName').val(),
			"crypto":$('#crypto').val(),
			"type":Number($('#type').val()),
			"status":Number($('#status').val())
		})
	})
})