function submitBalanceForm() {
	var amount = util.trim(document.getElementById("amount").value);
	var balancetype = util.trim(document.getElementById("balancetype").value);
	var tradePwd = util.trim(document.getElementById("tradePwd").value);
	var totpCode = 0;
	var phoneCode = 0;
	
	if (balancetype == null || balancetype == 0) {
		util.showerrortips("balanceerrortips", language["balance.error.tips.1"]);
		return;
	} else {
		util.hideerrortips("balanceerrortips");
	}
	
	var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
	if (!reg.test(amount)) {
		util.showerrortips("balanceerrortips",language["balance.error.tips.2"]);
		return;
	} else {
		util.hideerrortips("balanceerrortips");
	}
	
	if (tradePwd == "") {
		util.showerrortips("balanceerrortips", language["balance.error.tips.3"]);
		return;
	} else {
		util.hideerrortips("balanceerrortips");
	}
	
	if (document.getElementById("balanceTotpCode") != null) {
		totpCode = util.trim(document.getElementById("balanceTotpCode").value);
		if (!/^[0-9]{6}$/.test(totpCode)) {
			util.showerrortips("balanceerrortips",language["comm.error.tips.59"]);
			return;
		} else {
			util.hideerrortips("balanceerrortips");
		}
	}
	if (document.getElementById("balancePhoneCode") != null) {
		phoneCode = util.trim(document.getElementById("balancePhoneCode").value);
		if (!/^[0-9]{6}$/.test(phoneCode)) {
			util.showerrortips("balanceerrortips",
					language["comm.error.tips.60"]);
			return;
		} else {
			util.hideerrortips("balanceerrortips");
		}
	}
	
	var url = "/json/balance/in.html?random="
			+ Math.round(Math.random() * 100);
	var param = {
		tradePwd : tradePwd,
		type : balancetype,
		amount : amount,
		totpCode : totpCode,
		phoneCode : phoneCode
	};
	$.post(url, param, function(result) {
		if (result != null) {
			if (result.code < 0) {
				util.showerrortips("balanceerrortips", result.msg);
			} else if (result.code == 0) {
				document.getElementById("balanceButton").disabled = "true";
				util.showerrortips("", language["balance.error.tips.4"], {
					okbtn : function() {
						$('#alertTips').modal('hide');
						location.reload(true);
					}
				});
			} else {
				util.hideerrortips("balanceerrortips");
			}
		}
	});
};

function calculateRate() {
	var amount = $("#amount").val();
	var feesRate = $("#balanceRate").val();
	if (amount == "") {
		amount = 0;
	}
	var feeamt = util.moneyformat(util.accMul(amount, feesRate), 6);
	var symbol = util.trim(document.getElementById("symbol").value);
	$("#free").html(feeamt + " " + symbol);
};

function balanceTypeChange(){
	var type = $("#balancetype").val();
	if(type ==0){
		$("#balanceRate").val(0);
		$("#free").val(0);
		$("#symbol").val("");
		$("#balanceAmount").html("0");
		calculateRate();
	}else{
		var url = "/json/balance/getType.html?random="
			+ Math.round(Math.random() * 100);
		var param = {
			typeid:type,
		};
		$.post(url, param, function(result){
			if (result != null) {
				if(result.code <0){
					util.showerrortips("balanceerrortips", result.msg);
				}else{
					$("#balanceAmount").html(result.wallet);
					$("#symbol").val(result.symbol);
					$("#balanceRate").val(result.rate);
					calculateRate();
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
	
	$("#balancetype").on("change", function() {
		balanceTypeChange();
	});
	
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
	
	$("#balancetype").val(0);
	$("#amount").val(0);
	$("#balanceRate").val(0);
	$("#free").val(0);
	$("#symbol").val("");
	$("#balanceAmount").html("0");
});