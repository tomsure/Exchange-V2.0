$(function(){
	
	
	function symbolStarts(data){
		$.ajax({
		type:"POST",
		url:"http://47.75.72.244:8080/cryptoPairStats/list",
		async:true,
		beforeSend: function (XMLHttpRequest) {  
           XMLHttpRequest.setRequestHeader("Content-Type", "application/json");  
                                              },  
  data:JSON.stringify(data),

             	success:function(d){
             		console.log(d)
             		  $('#tradeSymbolListTable').bootstrapTable('destroy')
            $('#tradeSymbolListTable').bootstrapTable({
            	pagination:true,
            pageSize:3,
            rowStyle: function(row, index) {
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
           	        	checkbox : true,
           	        },
           	   {
           	   	 field: 'Crypto',
                 title: 'Crypto.',
           	   },
           	   {
           	   	 field: 'HighPrice',
                 title: 'High Price',
           	   },
           	   {
           	   	 field: 'lowPrice',
                 title: 'Low Price.',
           	   },
           	    {
           	   	 field: 'qty',
                 title: 'Qty',
           	   },
           	   {
           	   	 field: 'Amount',
                 title: 'Amount',
           	   },
           	   {
           	   	 field: 'Profit',
                 title: 'Profit',
           	   },
           	   {
           	   	 field: '',
                 title: 'Operate',
           	   }
           	   
           	   
           	 ],
           	 data:d
            	
            	
            	
            })
             		 }
		
	});
		
		
	}
	
	
	symbolStarts({"cryptoPairId" :0, "high" : "", "low" : "", "qty" : "", "amount" : "", "peroid": ""})
	$('#searchBtn').click(function(){
		symbolStarts({"cryptoPairId" :Number($('#crypto').val()), "high" :$('#heighPrice').val(), "low":$('#lowPrice').val(), "qty" :$('#qty').val(), "amount" :$('#amount').val(), "peroid":$('#period').val()})
	})
	
	
})
