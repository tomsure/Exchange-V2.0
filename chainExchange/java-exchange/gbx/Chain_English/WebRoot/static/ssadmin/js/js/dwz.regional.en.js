/**
 * @author ZhangHuihua@msn.com
 */
(function($){
	// jQuery validate
	$.extend($.validator.messages, {
		required: "required",
		remote: "Please fix this field",
		email: "Please enter the correct format of e-mail",
		url: "Please enter the legal web site",
		date: "Please enter the legal date",
		dateISO: "Please enter the legal date (ISO).",
		number: "Please enter the legitimate number",
		digits: "Only inputting integers",
		creditcard: "Please enter a legitimate credit card number",
		equalTo: "Please enter the same value again",
		accept: "Please enter a string with a legitimate suffix name",
		maxlength: $.validator.format("The length is the most {0} Character"),
		minlength: $.validator.format("The least length is {0} Character"),
		rangelength: $.validator.format("Length is between {0} 和 {1} Character"),
		range: $.validator.format("Please enter a intermediate {0} and {1} Character"),
		max: $.validator.format("Please enter a maximum of one {0} Character"),
		min: $.validator.format("Please enter a minimum of one {0} Character"),
		
		alphanumeric: "Letter、number、Underline",
		lettersonly: "It must be the alphabet",
		phone: "Letter、Space、brackets"
	});
	
	// DWZ regional
	$.setRegional("datepicker", {
		dayNames: ['日', '一', '二', '三', '四', '五', '六'],
		monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
	});
	$.setRegional("alertMsg", {
		title:{error:"Error", info:"Info", warn:"Warn", correct:"Success", confirm:"Confirm Tip"},
		butMsg:{ok:"Ok", yes:"Yes", no:"No", cancel:"Cancel"}
	});
})(jQuery);