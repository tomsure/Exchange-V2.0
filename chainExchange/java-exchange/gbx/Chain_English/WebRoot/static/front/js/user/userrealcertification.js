var certification = {
	submitRealCertificationForm : function() {
		var realname = document.getElementById("bindrealinfo-realname").value;
		var address = document.getElementById("bindrealinfo-address").value;
		var identitytype = document.getElementById("bindrealinfo-identitytype").value;
		var identityno = document.getElementById("bindrealinfo-identityno").value;
		var ckinfo = document.getElementById("bindrealinfo-ckinfo").checked;
		var desc = '';
		// 验证是否同意
		if (!ckinfo) {
			desc = language["certification.error.tips.1"];
			util.showerrortips('certificationinfo-errortips', desc);
			return;
		}
		//验证姓名
		if (realname.length > 6 || realname.trim() == "") {
			desc = language["certification.error.tips.2"];
			util.showerrortips('certificationinfo-errortips', desc);
			return;
		}
		// 验证证件类型
		if (identitytype != 0) {
			desc = language["certification.error.tips.3"];
			util.showerrortips('certificationinfo-errortips', desc);
			return;
		}
		// 验证身份证
		var isIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		var re = new RegExp(isIDCard);
		if (!re.test(identityno)) {
			desc = language["certification.error.tips.4"];
			util.showerrortips('certificationinfo-errortips', language["comm.error.tips.119"]);
			return false;
		}
		// 隐藏错误消息
		util.hideerrortips('certificationinfo-errortips');
		// 提交信息
		
		var url = "/user/validateIdentity.html?random=" + Math.round(Math.random() * 100);
		var param = {
			realName : realname,
			identityType : identitytype,
			identityNo : identityno
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				util.showerrortips('certificationinfo-errortips', data.msg);
				location.reload();
			} else {
				util.showerrortips('certificationinfo-errortips', data.msg);
			}
		}, "json");

	},
};

$(function() {
	$("#bindrealinfo-Btn").on("click", function() {
		certification.submitRealCertificationForm(false);
	});
});
