$(function () {
    function pendingOrder(data){
    	$.ajax({
        type: "POST",
        url: "http://47.75.72.244:8080/pendingOrder/list",
        async: true,
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
        },
        data: JSON.stringify(data),

        success: function (d) {
             $('#pendingOrderTable').bootstrapTable('destroy')
            $('#pendingOrderTable').bootstrapTable({
                pagination: true,
                pageSize: 3,
                rowStyle: function (row, index) {
                    var style = {};
                    style = {
                        css: {
                            'text-align': 'center'
                        }
                    };
                    return style;
                },
                columns: [
                    {
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
                        field: '',
                        title: 'Operate',
                    }

                ],
                data: d
            })

           
        },

        error: function (err) {
            console.log(err)
        }
    });
    }
   pendingOrder({"orderId": 0, "userName": "", "crpto": "", "type": 0})
      $('#searchBtn').click(function(){
      	 pendingOrder({"orderId":$('#orderId').val(), "userName":$('#userName').val(), "crpto":$('#crpto').val(), "type":$('type').val()})
      })
})
