function addGoodsaddress() {
	var postalcode = util.trim(document.getElementById("postalcode").value);
	var recName = util.trim(document.getElementById("recName").value);
	var phone = util.trim(document.getElementById("phone").value);
	var address = util.trim(document.getElementById("address").value);
	var prov = util.trim(document.getElementById("prov").value);
	var city = util.trim(document.getElementById("city").value);
	var dist = util.trim(document.getElementById("dist").value);
	
	if ((prov == "" || prov == language["withdraw.error.tips.7"])) {
		util.layerTips("prov", "请选择收货地址省份");
		return;
	}
	if ((city == "" || city == language["withdraw.error.tips.7"])) {
		util.layerTips("city", "请选择收货地址城市");
		return;
	}
	if (address == "") {
		util.layerTips("address", "请填写详细地址");
		return;
	}
	if (address.length > 300) {
		util.layerTips("address", "详细地址长度有误");
		return;
	}
	
	if (postalcode == null || postalcode == "") {
		util.layerTips("postalcode", "邮政编码不能为空");
		return;
	}
	if (recName == null || recName == "") {
		util.layerTips("recName", "收货人不能为空");
		return;
	}
	if (phone == null || phone == "") {
		util.layerTips("phone", "手机号码不能为空");
		return;
	}
	
	var url = "/shop/json/addAddress.html?random="
			+ Math.round(Math.random() * 100);
	var param = {
		province : prov,
		city : city,
		dist : dist,
		desc : address,
		recName : recName,
		postalcode : postalcode,
		phone : phone
	};
	$.post(url, param, function(result) {
		if (result != null) {
			if (result.code < 0) {
				util.layerTips(result.txt,result.msg);
			} else if (result.code == 0) {
				document.getElementById("addressbtn").disabled = "true";
				util.layerAlert("", result.msg, 1);
			}
		}
	});
};

function deleteAddress(id) {
	util.layerConfirm("您确认要删除该地址吗?",
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/shop/json/deleteAddress.html?random=" + Math.round(100 * Math.random()),
	            param = {
	            	fid : id
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        });
};


$(function() {
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
});