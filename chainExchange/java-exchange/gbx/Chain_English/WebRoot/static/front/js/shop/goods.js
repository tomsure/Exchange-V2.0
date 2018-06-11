var  goods= {
	payTypeChange : function(begindate, enddate) {
		var payType = $("#payType").val();
		if(payType ==0){
			document.getElementById("lastPay").innerHTML="0";
			document.getElementById("coinName").innerHTML="";
			document.getElementById("coinPrice").innerHTML="";
		}
		var goodsID = $("#goodsID").val();
		var qty = document.getElementById("amount").value;
		if(qty == null || qty ==0 || qty == ""){
			qty = 0;
		}
		url = "/shop/showDetails.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			payType : payType,
			goodsID : goodsID
		}, function(data) {
			if (data != "") {
				if(data.code <0){
					util.layerAlert("", data.msg, 2);
					document.getElementById("payType").value=0;
					document.getElementById("lastPay").innerHTML="0";
					document.getElementById("coinName").innerHTML="";
					document.getElementById("coinPrice").innerHTML="";
					return;
				}else{
					var goodsPrice = data.goodsPrice;
					var price = data.price;
					var name = data.name;
					var shortName = data.shortName;
					var total = util.moneyformat(qty*price*goodsPrice, 4);
					document.getElementById("lastPay").innerHTML=total +" "+shortName;
					if(shortName=="CNY"){
						document.getElementById("coinName").innerHTML="";
						document.getElementById("coinPrice").innerHTML="";
					}else{
						document.getElementById("coinName").innerHTML=name;
						document.getElementById("coinPrice").innerHTML="￥"+price;
					}
					
				}
			}else{
				util.layerAlert("", "网络异常!", 2);
				return;
			}
		}, "json");
		
	}
};

function buyGoods(id){
	var isLogin = document.getElementById("isLogin").value;
	var amount = document.getElementById("amount").value;
	var payType = $("#payType").val();
	if(isLogin == null || isLogin ==0){
		util.layerAlert("", "请先登陆!", 2);
		return;
	}
	if(id == null || id ==0 || id == ""){
		util.layerAlert("", "商品信息异常!", 2);
		return;
	}
	if(amount == null || amount ==0 || amount == ""){
		util.layerAlert("", "请填写购买数量!", 2);
		return;
	}
	if(payType ==0){
		util.layerAlert("", "请选择付款方式!", 2);
		return;
	}
	window.location.href="/shop/addCart.html?id="+id+"&qty="+amount+"&payType="+payType;
}


$(function() {
	$("#amount").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 0);
	}).on("click", function() {
		this.focus();
		this.select();
	});
	$("#payType").change(function() {
		goods.payTypeChange();
	});
	$("#amount").change(function() {
		goods.payTypeChange();
	});
	document.getElementById("payType").value=0;
	document.getElementById("lastPay").innerHTML="0";
	document.getElementById("coinName").innerHTML="";
	document.getElementById("coinPrice").innerHTML="";
});