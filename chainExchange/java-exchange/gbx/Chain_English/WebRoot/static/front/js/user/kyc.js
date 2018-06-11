var flag =true;
var security = {
    submitRealCertificationForm: function () {
    	if(!flag) return;
		flag =false;
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
            flag =true;
            return;
        }
        //验证姓名
        if (realname.length > 32 || realname.trim() == "") {
            desc = language["certification.error.tips.2"];
            util.showerrortips('certificationinfo-errortips', desc);
            flag =true;
            return;
        }
        // // 验证证件类型
        // if (identitytype != 0) {
        //     desc = language["certification.error.tips.3"];
        //     util.showerrortips('certificationinfo-errortips', desc);
        //     flag =true;
        //     return;
        // }
        // 验证身份证
        // var isIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        // var re = new RegExp(isIDCard);
        // if (!re.test(identityno)) {
        //     desc = language["certification.error.tips.4"];
        //     util.showerrortips('certificationinfo-errortips', language["comm.error.tips.119"]);
        //     flag =true;
        //     return false;
        // }
        // 隐藏错误消息
        util.hideerrortips('certificationinfo-errortips');
        // 提交信息

        var url = "/user/validateIdentity.html?random=" + Math.round(Math.random() * 100);
        var param = {
            realName: realname,
            identityType: identitytype,
            identityNo: identityno
        };
        jQuery.post(url, param, function (data) {
        	if (data.code == 0) {
				util.showerrortips('certificationinfo-errortips', data.msg);
				window.location.reload(false);
			} else {
				util.showerrortips('certificationinfo-errortips', data.msg);
				flag =true;
			}
        }, "json");
    },
    submitInfo: function () {
        var identityUrlOn = $('#pic1Url').val();
        var identityUrlOff = $('#pic2Url').val();
        var identityHoldUrl = $('#pic3Url').val();

        if (!identityUrlOn) {
            util.showerrortips("error", 'Please upload the front copy');
            return false;
        }

        // if (!identityUrlOff) {
        //     util.showerrortips("error", 'Please Upload Reverse Photo');
        //     return false;
        // }
        //
        // if (!identityHoldUrl) {
        //     util.showerrortips("error", 'Please Upload Handheld Photo');
        //     return false;
        // }

        var parma = {
            identityUrlOn: identityUrlOn,
            identityUrlOff: identityUrlOff,
            identityHoldUrl: identityHoldUrl
        }

        $.post("/user/validateKyc.html", parma, function (data, textStatus, jqXHR) {

            if (data.code == 0) {
                setTimeout("location.reload()", 500)
            }
            util.showerrortips("error", data.msg);
        }, "json");
    }
}

$(function () {

    //实名认证
    $("#bindrealinfo-Btn").on("click", function () {
        security.submitRealCertificationForm(false);
    });

    $('#withdrawCnyAddrBtn').on('click', function () {
        security.submitWithdrawCnyAddress();
    });


    $(".btn-sendmsg").on("click", function () {
        if (this.id == "unbindphone-newsendmessage") {
            var areacode = $("#unbindphone-newphone-areacode").html();
            var phone = $("#unbindphone-newphone").val();
            if (phone == "") {
                util.showerrortips("unbindphone-errortips", language["comm.error.tips.6"]);
                return;
            }
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
        } else if (this.id == "unbindbank-megbtn") {
            var areacode = $("#bindphone-newphone-areacode").html();
            var phone = $("#unbindBank-phone").val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

        } else if (this.id == "bindphone-sendmessage") {
            var areacode = $("#bindphone-newphone-areacode").html();
            var phone = $("#bindphone-newphone").val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

        } else if (this.id == "bindsendmessage") {
            var areacode = '86';
            var phone = $("#dialogPhone").val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

        } else if (this.id == "retrievePassword-sendmessage") {
            var imgcode = $('#retrievePassword-imgpwd').val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone, imgcode);
        } else {
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
        }
    });

    //提交
    $('.to_up .s1').on('click', function () {
        security.submitInfo();
    });

})



//上传图片
function uploadImg1() {
    if (checkFileType('pic1', 'img')) {
        fileUpload("/common/upload.html", "4", "pic1", "pic1Url", null, null, imgbakc1, "resultUrl");
    }
}

function imgbakc1(resultUrl) {
    $(".pic1show").css("background-image", "url(" + resultUrl + ")");
    <!--$('label[for="pic1"]').text('Re - Upload');-->
    $('label[for="pic1"]').html("<div style='margin-top:8%'>Re - Upload</div>");
    $('.pic1name').text($('#pic1').val().split('\\').pop())
        .siblings().text('Selected');
}

function uploadImg2() {
    if (checkFileType('pic2', 'img')) {
        fileUpload("/common/upload.html", "4", "pic2", "pic2Url", null, null, imgbakc2, "resultUrl");
    }
}

function imgbakc2(resultUrl) {
    $(".pic2show").css("background-image", "url(" + resultUrl + ")");
    <!--$('label[for="pic2"]').text('Re - Upload');-->
    $('label[for="pic2"]').html("<div style='margin-top:8%'>Re - Upload</div>");
    $('.pic2name').text($('#pic2').val().split('\\').pop())
       .siblings().text('Selected');
}

function uploadImg3() {
    if (checkFileType('pic3', 'img')) {
        fileUpload("/common/upload.html", "4", "pic3", "pic3Url", null, null, imgbakc3, "resultUrl");
    }
}

function imgbakc3(resultUrl) {
		
    $(".pic3show").css("background-image", "url(" + resultUrl + ")");
		
    <!--$('label[for="pic3"]').text('Re - Upload');-->
    $('label[for="pic3"]').html("<div style='margin-top:8%'>Re - Upload</div>");
    $('.pic3name').text($('#pic3').val().split('\\').pop())
       .siblings().text('Selected');
}
