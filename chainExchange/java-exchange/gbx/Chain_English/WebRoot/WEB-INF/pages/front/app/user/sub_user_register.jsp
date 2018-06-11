<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../comm/include.inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
	<head>
	<jsp:include page="../comm/header.jsp"></jsp:include>
		<meta charset="utf-8" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<title>注册</title>
		<link rel="stylesheet" href="/static/front/app/css/css.css" />
		<link rel="stylesheet" href="/static/front/app/css/style.css" />
		<script type="text/javascript" src="/static/front/app/js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="/static/front/app/js/swiper-3.4.2.jquery.min.js" ></script>
	</head>
	<body>
		<nav>
			<div class="register-banner">
				<a href="javascript:;" onClick="javascript :history.back(-1)"><img src="/static/front/app/images/back_home.png"></a>
				<p>注册</p>
				<div class="user-logo"><img src="/static/front/app/images/user_logo.png"></div>
			</div>
			<div class="step">
				<ul>
					<li class="active">注册</li>
					<li>设置交易密码</li>
					<li>实名认证</li>
					<li>成功</li>
				</ul>
			</div>
		</nav>
		<section>
			<div class="information">
				<div class="register-tab">
					<ul>
						<li class="active"  data-type="0">手机注册</li>
						<li  data-type="1">邮箱注册</li>
					</ul>
				</div>
				<div class="register_cont" style="display: block;">
					<div class="information-cont">
						<ul>
							<li>
								<select name="" id="register-areaCode">
									<option value="中国大陆(china)">中国大陆(china)</option>
								</select>
							</li>
							<li>
								<span class="prefixion" id="register-phone-areacode">+86</span>
								<input class="phone-number" type="number" value="" placeholder="手机" id="register-phone" />
							</li>
							<li>
								<input type="number" class="phone-code" value="" placeholder="手机验证码" id="register-msgcode"/>
								<span class="gain btn-sendmsg" id="register-sendphone"  data-msgtype="12">获取验证码</span>
							</li>
							<li>
								<input type="text" class="phone-code" value="" placeholder="请输入验证码" id="register-imgcode"/>
								<span class="gain1"><img class="btn-imgcode" src="/servlet/ValidateImageServlet?r=1501754647473" style="width: 100%; height: 100%;"></span>
							</li>
							<li>
								<input type="password" class="password" value="" placeholder="请输入密码" id="register-password"/>
								<em>
									<img src="/static/front/app/images/password_off.png" class="off" style="display: block;" />
									<img src="/static/front/app/images/password_on.png" class="on" />
								</em>
							</li>
						</ul>
					</div>
					<div class="protocol pro-active">阅读并同意<a href="zhucexieyi.html">《云数网用户协议》</a><input type="checkbox" checked="checked" class="pro-checked"></div>
					<div class="next next-active">下一步</div>
					<div class="change-info">已有帐号？ <a href="login.html">直接登录>></a></div>
				</div>
				<div class="register_cont">
					<div class="information-cont">
						<ul>
							<li>
								<input type="text" value="" placeholder="请输入邮箱地址" class="mail" id="register-email"/>
							</li>
							<li>
								<input type="number" class="phone-code" value="" placeholder="请输入邮箱验证码" id="register-email-code"/>
								<span class="gain btn-sendemailcode" id="register-sendemail" data-msgtype="3">获取验证码</span>
							</li>
							<li>
								<input type="text" class="phone-code" value="" placeholder="请输入验证码" id="register-imgcode2"/>
								<span class="gain1"><img class="btn-imgcode" src="/servlet/ValidateImageServlet?r=1501754647473" style="width: 100%; height: 100%;"></span>
							</li>
							<li>
								<input type="password" class="password" value="" placeholder="请输入密码" id="register-password2" />
								<em>
									<img src="/static/front/app/images/password_off.png" class="off" style="display: block;" />
									<img src="/static/front/app/images/password_on.png" class="on" />
								</em>
							</li>
						</ul>
					</div>
					<div class="protocol pro-active">阅读并同意<a href="">《云数网用户协议》</a><input type="checkbox" checked="checked" class="pro-checked"></div>
					<div class="next next-active">下一步</div>
					<div class="change-info">已有帐号？ <a href="">直接登录>></a></div>
				</div>
			</div>
		</section>
		
		
<input id="register-intro" class="form-control login-ipt" type="hidden" value="${intro }">
<input id="regType" type="hidden" value="0">
	<input id="intro_user" type="hidden" value="${intro }">
<script type="text/javascript" src="/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="/static/front/js/layer/layer.js"></script>
	<script type="text/javascript" src="/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="/static/front/js/comm/comm.js"></script>

<script>
	$(function(){
		
		$(".information-cont .off").on('click',function(){
			$(this).hide();
			$(this).next().show();
			$(this).parent().siblings('input.password').attr('type','text');
		});
		
		$(".information-cont .on").on('click',function(){
			$(this).hide();
			$(this).prev().show();
			$(this).parent().siblings('input.password').attr('type','password');
		});
		
		$(".register-tab ul li").on('click',function(){
		var type = $(this).attr('data-type') ;
            document.getElementById("regType").value = type;
			$(this).addClass('active').siblings().removeClass('active');
			var index =  $(".register-tab ul li").index(this);
            $(".register_cont").eq(index).show().siblings('.register_cont').hide();
            
		});
		
		$(".next").on('click',function(){
			reg.regSubmit();
		});
		
	});
</script>

<script type="text/javascript">
var reg = {
	checkRegUserName : function() {
		var regType = document.getElementById("regType").value;
		var regUserName = "";
		var desc = '';
		if (regType == 0) {
			// 验证手机号
			regUserName = util.trim(document.getElementById("register-phone").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.8"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.9"];
				} else if (!util.checkMobile(regUserName)) {
					desc = language["comm.error.tips.10"];
				}
			}
		} else {
			// 验证邮箱
			regUserName = util.trim(document.getElementById("register-email").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.11"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.12"];
				} else if (!util.checkEmail(regUserName)) {
					desc = language["comm.error.tips.13"];
				} else if (new RegExp("[,]", "g").test(regUserName)) {
					desc = language["comm.error.tips.14"];
				} else if (regUserName.length > 100) {
					desc = language["comm.error.tips.15"];
				}
			}
		}
		if (desc != "") {
			layer.msg( desc);
			return;
		} else {
			util.hideerrortips("register-errortips");
		}
		var url = "/user/reg/chcekregname.html?name=" + encodeURI(regUserName) + "&type=" + regType + "&random=" + Math.round(Math.random() * 100);
		$.get(url, null, function(data) {
			if (data.code == -1) {
				layer.msg( data.msg);
				return;
			} else {
				util.hideerrortips("register-errortips");
			}
		}, "json");
	},
	checkPassword : function() {
	
		var pwd = '' ;
		var regType = document.getElementById("regType").value;
		if(regType == 0 ){
			pwd = util.trim(document.getElementById("register-password").value);
		}else{
			pwd = util.trim(document.getElementById("register-password2").value);
		}
		var desc = util.isPassword(pwd);
		if (desc != "") {
			layer.msg( desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		}
		return true;
	},
	checkRePassword : function() {
		/* var pwd = util.trim(document.getElementById("register-password").value);
		var rePwd = util.trim(document.getElementById("register-confirmpassword").value);
		var desc = util.isPassword(pwd);
		if (desc != "") {
			layer.msg( desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		} */
		return true;
	},

	checkRegUserNameNoJquery : function() {
		var regType = document.getElementById("regType").value;
		var regUserName = "";
		var desc = '';
		if (regType == 0) {
			// 验证手机号
			regUserName = util.trim(document.getElementById("register-phone").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.8"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.9"];
				} else if (!util.checkMobile(regUserName)) {
					desc = language["comm.error.tips.10"];
				}
			}
		} else {
			// 验证邮箱
			regUserName = util.trim(document.getElementById("register-email").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.11"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.12"];
				} else if (!util.checkEmail(regUserName)) {
					desc = language["comm.error.tips.13"];
				} else if (new RegExp("[,]", "g").test(regUserName)) {
					desc = language["comm.error.tips.14"];
				} else if (regUserName.length > 100) {
					desc = language["comm.error.tips.15"];
				}
			}
		}
		if (desc != "") {
			layer.msg( desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
			return true;
		}
	},
	regSubmit : function() {
		/* if (!document.getElementById("agree").checked) {
			layer.msg( language["comm.error.tips.23"]);
			return;
		} */
		var regType = document.getElementById("regType").value;
		var areaCode = document.getElementById("register-phone-areacode").innerHTML;
		var flag = this.checkRegUserNameNoJquery() && this.checkPassword() && this.checkRePassword();
		if (flag == true) {
			var regUserName = "";
			if (regType == 0) {
				regUserName = util.trim(document.getElementById("register-phone").value);
			} else {
				regUserName = util.trim(document.getElementById("register-email").value);
			}
			validateCode = document.getElementById("register-imgcode").value;
			var pwd = '' ;
			if(regType == 0 ){
				pwd = util.trim(document.getElementById("register-password").value);
			}else{
				pwd = util.trim(document.getElementById("register-password2").value);
			}
			var regPhoneCode = 0;
			if (regType == 0) {
				regPhoneCode = document.getElementById("register-msgcode").value;
				if (regPhoneCode == "") {
					layer.msg( language["comm.error.tips.60"]);
					return;
				}
			}
			var regEmailCode = 0;
			if (regType == 1) {
				regEmailCode = document.getElementById("register-email-code").value;
				if (regEmailCode == "") {
					layer.msg( language["comm.error.tips.138"]);
					return;
				}
			}
			var intro_user = document.getElementById("register-intro").value;
			var url = "/user/reg/index.html?random=" + Math.round(Math.random() * 100);
			var param = {
				regName : regUserName,
				password : pwd,
				regType : regType,
				vcode : validateCode,
				phoneCode : regPhoneCode,
				ecode : regEmailCode,
				areaCode : areaCode,
				intro_user : intro_user
			};
			jQuery.post(url, param, function(data) {
				if (data.code < 0) {
					// 注册失败
					layer.msg( data.msg);
					if (data.code == -20) {
//						document.getElementById("register-imgcode").value = "";
						$(".btn-imgcode").click();
					}
				} else {
					layer.msg( '注册成功');
					window.setTimeout("window.location='/m/tradePwd.html'", 2000);
				}
			}, "json");
		}
	},
	areaCodeChange : function(ele, setEle) {
		var code = $(ele).val();
		$("#" + setEle).html("+" + code);
	},
};
$(function() {
	$(".btn-sendmsg").on("click", function() {
		var areacode = $("#register-phone-areacode").html();
		var phone = $("#register-phone").val();
		var vcode = $("#register-imgcode").val() ;
		if(vcode ==''){
			layer.msg( '请输入图片验证码');
			return;
		}
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone,vcode);
	});
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	$("#register-phone").on("blur", function() {
		reg.checkRegUserName();
	});
	$("#register-email").on("blur", function() {
		reg.checkRegUserName();
	});
	$("#register-submit").on("click", function() {
		reg.regSubmit();
	});
	$("#register-areaCode").on("change", function() {
		reg.areaCodeChange(this, "register-phone-areacode");
	});
	$(".btn-sendemailcode").on("click", function() {
		var address = $("#register-email").val();
		if (address == "") {
			layer.msg( language["comm.error.tips.7"]);
			return;
		}
		if($("#register-imgcode2").val() ==''){
			layer.msg( '请输入图片验证码');
			return;
		}
		email.sendcode($(this).data().msgtype, $(this).data().tipsid, this.id, address,$("#register-imgcode2").val());
	});
});
</script>


	</body>
</html>
