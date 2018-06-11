function sendSomething(btn) {

	var countdown = 60;

	function sendCode(btn) {

		var obj = btn;
		settime(obj);

	}

	function settime(obj) { //发送验证码倒计时

		if(countdown == 0) {
			obj.attr('disabled', false);

			$(obj).val("send");
			countdown = 60;
			return;
		} else {
			obj.attr('disabled', true);
			$(obj).val(+countdown + "S");
			countdown--;
		}
		setTimeout(function() {
			settime(obj)
		}, 1000)
	}

	sendCode(btn)
}