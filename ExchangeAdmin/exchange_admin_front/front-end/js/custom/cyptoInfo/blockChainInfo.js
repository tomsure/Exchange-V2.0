 $(function() {

 	function blockChainInfoTable(defaultData) {
 		$.ajax({
 			type: "POST",
 			url: "http://47.75.72.244:8080/chain/list",
 			async: true,
 			beforeSend: function(XMLHttpRequest) {
 				XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
 			},
 			data: JSON.stringify(defaultData),
 			success: function(d) {
 				  var d=d.reverse()
 				$('#blockChainInfoTable').bootstrapTable("destroy")
 				var ableTable = $('#blockChainInfoTable').bootstrapTable({

 					toolbar: '#tableToolBar',
 					//         	queryParams: oTableInit.queryParams,
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
 							formatter: function() {
 								//                        	addClass('')
 							},
 							events: {

 								'change input': function(ev, value, row, index) {
 						
 									$('#editBtn').click(function() {
 									      
 										layer.open({
 											skin: ['addStyle publicStyle'],
 											title: ['Add BlockChain'],
 											type: 1,
 											shade:0,
 											content: $('#editModal'),
 											area: ['500px', '550px'],
 											success: function(layero, index) {
 												
 												$('#editModal').removeClass('hide')
                                                
 												$('#chainId1').val(row.chainId)
 												$('#chainName1').val(row.chainName)
 												$('#url1').val(row.url)
 												$('#userName1').val(row.userName)
 												$('#password1').val(row.password)
 												$('#label1').val(row.label)
 												$('#confirmBlockNo1').val(row.confirmNo)
 												$('#scanStartBlockNo1').val(row.startScanBlockId)
 												$('#description1').val(row.descrip)

 											},
 											end:function(){
 												$('#editForm').find('input').val('')
 											}
 										})
 									})
 								}
 							}
 						},
 						{
 							field: 'url',
 							title: 'URL'
 						},
 						{
 							field: 'chainName',
 							title: 'chainName'
 						},
 						{
 							field: 'userName',
 							title: 'User Name'
 						},
 						{
 							field: 'password',
 							title: 'Password'
 						},
 						{
 							field: 'startScanBlockId',
 							title: 'Start Scan Black ID'
 						},
 						{
 							field: 'confirmNo',
 							title: 'Confirm No.'
 						},
 						{
 							field: 'label',
 							title: 'Label'
 						},
 						{
 							field: 'descrip',
 							title: 'Descrip'
 						},
 						{
 							field: 'chainId',
 							title: 'ChainId'
 						},
 						{
 							field: 'status',
 							title: 'Status',
 							formatter: function(value, row, index) {
 								if(row.status = 1) {
 									return "Enabled";
 								} else if(row.status = 0) {
 									return "Disabled"
 								}
 							}
 						},
 						{
 							field: 'status',
 							title: 'Operation',
 							//                        events: userEnable,
 							formatter: operateFormatter
 						}

 					],
 					data: d,
 					onClickCell: function(field, value, row, $element) {
 						if($($element.context).text() == 'Enable') {
 							userEnable("http://47.75.72.244:8080/chain/enable", {
 								"ids": [row.chainId],
 								"status": 1
 							}, function(d) {
 								if(d.status == 200) {
 									$($element.context).find('button').removeClass('btn-success').addClass('btn-danger')
 									$($element.context).find('button').text('Disable')
 								}

 							})

 						} else if($($element.context).text() == 'Disable') {
 							userEnable("http://47.75.72.244:8080/chain/enable", {
 								"ids": [row.chainId],
 								"status": 0
 							}, function(d) {
 								if(d.status == 200) {
 									$($element.context).find('button').removeClass('btn-danger').addClass('btn-success')
 									$($element.context).find('button').text('Enable')
 								}

 							})

 						}

 					},
 				})
 				$('#enabledBtn').click(function() {
 					var result = ableTable.bootstrapTable('getSelections');
 					var ids = [];
 					for(var i = 0; i < result.length; i++) {
 						var item = result[i];
 						ids.push(item.chainId);
 					}
 					var checkedBox = $('#blockChainInfoTable').find('tr td input[type="checkbox"]:checked')
 					if(checkedBox.length <= 0) {
 						alert('please check at least 1 chain')
 					} else if(checkedBox.length > 0) {
 						userEnable("http://47.75.72.244:8080/chain/enable", {
 							"ids": ids,
 							"status": 1
 						}, function(d) {
 							if(d.status == 200) {
 								alert('success')
 								enabledFun(checkedBox)
 							}
 						})
 					}
 				})

 				$('#disabledBtn').click(function() {
 					var result = ableTable.bootstrapTable('getSelections');

 					var ids = [];
 					for(var i = 0; i < result.length; i++) {
 						var item = result[i];
 						ids.push(item.userId);
 					}
 					var checkedBox = $('#blockChainInfoTable').find('tr td input[type="checkbox"]:checked')
 					if(checkedBox.length <= 0) {
 						alert('please check the table box')
 					} else if(checkedBox.length > 0) {
 						userEnable("http://47.75.72.244:8080/chain/enable", {
 							"ids": ids,
 							"status": 0
 						}, function(d) {

 							if(d.status == 200) {
 								alert('success')
 								disabledFun(checkedBox)
 							}
 						})

 					}

 				})

 				$('#addBtn').click(function() {
                     
 					layer.open({
 						skin: ['addStyle publicStyle'],
 						title: ['Add BlockChain'],
 						type: 1,
 						shade:0,
 						content: $('#addBlockBhainModal'),
 						area: ['500px', '550px'],
 						success: function(layero, index) {
 							$('#addBlockBhainModal').removeClass('hide')
 						},
 						end:function(){
 							

    							$('#addBlockBhainModal').find('input').val('')
                         
                         
 						}
 					})
 				})

 			}
 		});
 	}
 	blockChainInfoTable({
 		"chainName": "",
 		"status": -1
 	})
 	$('#searchBtn').click(function() {

 		blockChainInfoTable({
 			'chainName': $('#searchchainName').val(),
 			'status': $('#status').val()
 		})

 	})
 	var addform=customValidate($('#addBlockChainform'), {
 		"chainId": {
 			required: true
 		}
 	}, {
 		"chainId": {
 			required: "Please Input chainId"
 		}
 	}, function() {
 		save({
 				"chainId": $('chainId').val(),
 				"chainName": $('#chainName').val(),
 				"url": $('#url').val(),
 				"userName": $('#userName').val(),
 				"password": $('#password').val(),
 				"label": $('#label').val(),
 				"comfirmBlockNo": $('#comfirmBlockNo').val(),
 				"scanStartBlockNo": $('#scanStartBlockNo').val(),
 				"descrip": $('#descrip').val()
 			})
 		
 	})
   var editForm=customValidate($('#editForm'), {
   		"chainId1": {
   			required: true
   		}
   	}, {
   		"chainId1": {
   			required: "Please Input chainId"
   		}
   	}, function() {
// 		alert($('#chainId1').val())
// 		 console.log($('#editForm #ChainId').val())
   		save({
   				"chainId": $('chainId1').val(),
   				"chainName": $('#chainName1').val(),
   				"url": $('#url1').val(),
   				"userName": $('#userName1').val(),
   				"password": $('#password1').val(),
   				"label": $('#label1').val(),
   				"comfirmBlockNo": $('#comfirmBlockNo1').val(),
   				"scanStartBlockNo": $('#scanStartBlockNo1').val(),
   				"descrip": $('#descrip1').val()
   			})
   	})
 	function save(data) {
 		$.ajax({
 			type: "POST",
 			url: "http://47.75.72.244:8080/chain/save",
 			async: true,
 			beforeSend: function(XMLHttpRequest) {
 				XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
 			},
 			data: JSON.stringify(data),
 			success: function(d) {
 				if(d.status == 200) {
 					
 					layer.msg('Successfully!')
 					
 					layer.closeAll(index)
 					blockChainInfoTable({
 						"chainName": "",
 						"status": -1
 					})

 				}
 			}

 		});
 	}
 })