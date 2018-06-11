var  goodsPay= {
		submitGoods : function(begindate, enddate) {
		var payType = document.getElementById("payType").value;
		var id = document.getElementById("id").value;
		var qty = document.getElementById("qty").value;
		var remark = document.getElementById("remark").value;
		var tradePwd = document.getElementById("tradePwd").value;
		var addressId = $(".hover").attr("data");
		if(!addressId){
			util.layerAlert("", "收货地址不能为空!", 2);
		}
		if(tradePwd == null || tradePwd == ""){
			util.layerAlert("", "请输入交易密码!", 2);
		}
		url = "/shop/json/buy.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			fid : id,
			buyQty : qty,
			payType : payType,
			addressId : addressId,
			tradePwd : tradePwd,
			remark : remark
		}, function(data) {
			if (data != "") {
				if(data.code <0){
					util.layerAlert("", data.msg, 2);
					return;
				}else{
					util.showerrortips("","购买成功，等待商家发货", {
						okbtn : function() {
							window.location.href = '/shop/myorder.html';
						},
						noshow : true
					});
				}
			}else{
				util.layerAlert("", "网络异常!", 2);
				return;
			}
		}, "json");
		
	}
};

$(function() {
	$(".qrshdz li").click(function() {
		$(this).addClass("hover").siblings().removeClass("hover");
	});
	
	$("#submitGoodsButton").on("click", function() {
		goodsPay.submitGoods();
	});
});