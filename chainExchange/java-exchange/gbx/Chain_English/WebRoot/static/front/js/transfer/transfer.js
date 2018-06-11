function submitTransferForm() {
	var sendAddress = util.trim(document.getElementById("sendAddress").value);
	var amount = util.trim(document.getElementById("amount").value);
	var transfertype = util.trim(document.getElementById("transfertype").value);
	var tradePwd = util.trim(document.getElementById("tradePwd").value);
	var totpCode = 0;
	var phoneCode = 0;
	
	if (transfertype == null || transfertype == 0) {
		util.showerrortips("transfererrortips", language["transfer.error.tips.1"]);
		return;
	} else {
		util.hideerrortips("transfererrortips");
	}
	
	var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
	if (!reg.test(amount)) {
		util.showerrortips("transfererrortips", language["transfer.error.tips.2"]);
		return;
	} else {
		util.hideerrortips("transfererrortips");
	}
	
	if (!reg.test(sendAddress)) {
		util.showerrortips("transfererrortips", language["transfer.error.tips.3"]);
		return;
	} else {
		util.hideerrortips("transfererrortips");
	}
	
	if (parseFloat(amount) < 0.01) {
		util.showerrortips("transfererrortips", language["transfer.error.tips.4"]);
		return;
	} else {
		util.hideerrortips("transfererrortips");
	}
	
	if (tradePwd == "") {
		util.showerrortips("transfererrortips", language["transfer.error.tips.5"]);
		return;
	} else {
		util.hideerrortips("transfererrortips");
	}
	
	if (document.getElementById("transferTotpCode") != null) {
		totpCode = util.trim(document.getElementById("transferTotpCode").value);
		if (!/^[0-9]{6}$/.test(totpCode)) {
			util.showerrortips("transfererrortips",language["comm.error.tips.59"]);
			return;
		} else {
			util.hideerrortips("transfererrortips");
		}
	}
	if (document.getElementById("transferPhoneCode") != null) {
		phoneCode = util.trim(document.getElementById("transferPhoneCode").value);
		if (!/^[0-9]{6}$/.test(phoneCode)) {
			util.showerrortips("transfererrortips",
					language["comm.error.tips.60"]);
			return;
		} else {
			util.hideerrortips("transfererrortips");
		}
	}
	
	var url = "/account/json/btcTransport.html?random="
			+ Math.round(Math.random() * 100);
	var param = {
		address : sendAddress,
		fid : transfertype,
		passwd : tradePwd,
		amount : amount,
		totpCode : totpCode,
		phoneCode : phoneCode
	};
	$.post(url, param, function(result) {
		if (result != null) {
			if (result.code < 0) {
				util.showerrortips("transfererrortips", result.msg);
			} else if (result.code == 0) {
				document.getElementById("transferButton").disabled = "true";
				$('#alertTips').modal('hide');
				util.layerAlert("", language["transfer.error.tips.6"], 1);
			} else {
				util.hideerrortips("transfererrortips");
			}
		}
	});
};

function cancelTransport(id) {
	util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/account/cancelTransport.html?random=" + Math.round(100 * Math.random()),
	            param = {
	            	fid : id
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        });
};

function calculateRate() {
	var amount = $("#amount").val();
	var feesRate = $("#transferRate").val();
	if (amount == "") {
		amount = 0;
	}
	var feeamt = util.moneyformat(util.accMul(amount, feesRate), 4);
	var symbol = util.trim(document.getElementById("symbol").value);
	$("#free").html(feeamt);
};

function transferTypeChange(){
	var type = $("#transfertype").val();
	if(type ==0){
		$("#balanceAmount").html("0");
		$("#symbol").val("");
	}else{
		var url = "/json/transfer/getType.html?random="
			+ Math.round(Math.random() * 100);
		var param = {
			typeid:type,
		};
		$.post(url, param, function(result){
			if (result != null) {
				if(result.code <0){
					util.showerrortips("transfererrortips", result.msg);
				}else{
					$("#balanceAmount").html(result.amt);
					$("#symbol").val(result.coin);
				}
			}
		});
	}
};

$(function() {
	$(".btn-sendmsg").on("click",
			function() {msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid,this.id);});
	
	$("#amount").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 4);
	}).on("keyup", function() {
		calculateRate();
	});
	
	$("#sendAddress").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 0);
	});
	
	$("#transfertype").on("change", function() {
		transferTypeChange();
	});
	
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
	
	$("#transfertype").val(0);
	$("#balanceAmount").html("0");
	$("#amount").val(0);
	$("#free").html("0");
	$("#symbol").val("");
});