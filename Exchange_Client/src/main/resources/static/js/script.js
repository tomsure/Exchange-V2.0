$(function() {

	$('.navlist ul').on('click', 'li', function(e) {

		$(e.target).css('border-bottom', '4px solid #fca033').css('color', "#fca033")
		$(e.target).children('a').css('color', "#fca033")
		$(e.target).siblings().css('border', '0')
		$(e.target).siblings().children('a').css('color', "gray")

		if(e.target.tagName == 'A') {
			$(e.target).css('border', '0')
			$(e.target).parent().css('border-bottom', '4px solid #fca033').css('color', "#fca033")
			$(e.target).parent().siblings().css('border', '0')
			$(e.target).parent().siblings().children().css('color', "gray")
		}

	})

	$('#identy3SubmitBtn').click(function() {
		location.href = 'user.html'
	})

	$('.navSelect').on('click', function() {
		$('.ulList li span').toggleClass('icon-jiantou-up').toggleClass('icon-jiantou-down')

	})
	//exchange
	$('#buyButton').click(function() { //买
		//需要判断是否绑定银行卡

		$('#passWordModal').modal('show') //已绑定银行卡
		//	    	  $('#tipModal').modal('show')  //未绑定银行卡
		//	    	  $('#tipModal .modal-body').text('Please bind the bank card first')
		//	    	  $('#tipModal button').click(function(){
		//	    	  	 location.href='depositAddressMgmt.html'
		//	    	  })

	})
	$('#sellButton').click(function() { //卖
		//需要判断是否绑定银行卡
		$('#passWordModal').modal('show') //已绑定银行卡
		//	    	   $('#tipModal').modal('show') //未绑定银行卡
		//	    	   $('#tipModal .modal-body').text('Please bind the bank card first')
		//	    	   $('#tipModal button').click(function(){
		//	    	  	 location.href='depositAddressMgmt.html'
		//	    	  })

	})

	//my_assets
	//	  $('#accountList').find('.refreshBtn').click(function(e){
	//	  	  location.href='trade.html'  
	//	  })

	$('#accountList').find('.depositBtn').click(function(e) {

		$('#depositModal').modal('show')
	})
	$('#accountList').find('.withdrawBtn').click(function(e) {
		$('#withdrawModal').modal('show')
	})

	$('.addressBox img').mouseover(function() {
		$('.qrCodeBox img').show()
	})

	$('.addressBox img').mouseout(function() {
		$('.qrCodeBox img').hide()
	})

	//user

	//    laydate
	if($('#startDate')) {
		//		layui.use('laydate', function() {
		//			var laydate = layui.laydate;
		//			laydate.render({
		//				elem: '#startDate',
		//				lang: 'en',
		//			});
		//			laydate.render({
		//				elem: '#endDate',
		//				lang: 'en'
		//			});
		//		})
	}

	//trade
	$('.chartBoxTop li').click(function(e) {
		$(e.target).css({

			'background': '#85181e',
			'color': 'white'
		})
		$(e.target).siblings('li').css({
			'background': 'white',
			'color': 'black'
		})

	})
	$('input').attr('autocomplete', "off").attr('autocomplete', 'new-password')

	//  去除空格
	//  $('input').blur(function(){
	//  	   
	//  	  var result=$.trim($(this).val())
	//  	     $(this).val(result)
	//  })

	$('#selectCountry li').click(function(e) {
		var target = e.target || window.srcElement
		$('#selectedBar').html($(target).text())

	})

})

//if($('#addressArea')){
//	
//	
//	function layer(type,content,yesFn){
//
//       var layer = layui.layer;
//       layer.config({
//          skin: 'layer_public-class'
//            })
//       layer.open({
//       content:content,
//       yes:yesFn,
//      btn:['OK'],
//      title:'Reminder'
//
//});    
//   
//     }
//	
//}