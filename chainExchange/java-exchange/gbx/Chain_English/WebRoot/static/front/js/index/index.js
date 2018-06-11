var login={
		indexLoginOnblur:function () {
		    var uName = document.getElementById("indexLoginName").value;
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		        util.showerrortips("indexLoginTips",language["comm.error.tips.1"]);
		    } else {
		    	util.hideerrortips("indexLoginTips");
		    }
		},
		loginIndexSubmit:function () {
		    util.hideerrortips("indexLoginTips");
		    var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		    var uName = document.getElementById("indexLoginName").value;
		    var pWord = document.getElementById("indexLoginPwd").value;
		    var longLogin = 0;
		    if (util.checkEmail(uName)) {
		        longLogin = 1;
		    }
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.1"]);
		        return
		    }
		    if (pWord == "") {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.2"]);
		        return;
		    } else if (pWord.length < 6) {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.3"]);
		        return;
		    }
		    var param = {
		        loginName: uName,
		        password: pWord,
		        type: longLogin
		    };
		    jQuery.post(url, param, function (data) {
		        if (data.code == 0) {
		            if (document.getElementById("forwardUrl") != null && document.getElementById("forwardUrl").value != "") {
		                var forward = document.getElementById("forwardUrl").value;
		                forward = decodeURI(forward);
		                window.location.href = forward;
		            } else {
		                var whref = document.location.href;
		                if (whref.indexOf("#") != -1) {
		                    whref = whref.substring(0, whref.indexOf("#"));
		                }
		                window.location.href = whref;
		            }
		        } else if (data.code <0) {
		        	util.showerrortips("indexLoginTips", data.msg);
		            document.getElementById("indexLoginPwd").value = "";
		        }
		    },"json");
		},
		refreshMarket:function(){
			var url="/real/indexmarket.html?random=" + Math.round(Math.random() * 100);
			$.post(url,{},function(data){
				$.each(data,function(key,value){
					$("#"+key+"_total").html(Number(value.total));
					$("#"+key+"_amt").html(Number(value.amt));
					$("#"+key+"_max").html(Number(value.max));
					if(Number(value.rose)>=0){
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('$'+value.price);
					}else{
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html('$'+value.price);
					}
					
					if(Number(value.rose7)>=0){
						$("#"+key+"_rose7").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose7+'%');
					}else{
						$("#"+key+"_rose7").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose7+'%');
					}
					
				/*$.plot($("#"+key+"_plot"), [{shadowSize:0, data:value.data}],{ grid: { borderWidth: 0}, xaxis: { mode: "time", ticks: false}, yaxis : {tickDecimals: 0, ticks: false},colors:['#e55600']});*/
				});
			});
			window.setTimeout(function() {
				login.refreshMarket();
			}, 5000);
		},	
		loginerror:function(){
			var errormsg =$("#errormsg").val();
			if(errormsg!="" && errormsg!="/"){
				util.showerrortips("", errormsg);
			}
		},
	    switchCoin: function() {
	        $(".currency").on("click",
	        function() {
	            var a = $(this);
	            var type = a.data().type;
	            var max = 3;
	            for(var i=1;i<=max;i++){
	            	if(i == type){
	            		 $("."+i+"_qu").removeClass("hide");
	            		 $("#"+i+"_qu_t").addClass("selectTag").removeClass("currency");
	            	}else{
	            		 $("."+i+"_qu").addClass("hide");
	            		 $("#"+i+"_qu_t").addClass("currency").removeClass("selectTag");
	            	}
	            	
	            }
	        })
	    }
};

var parseUpDown = function (el) {
	 var num = parseFloat($.text([el]).replace('￥', ''), 10);
	    //console.log(num);
	    return num;
}

var parseNum = function (el) {
    var num = parseFloat($.text([el]).replace(/,/g, ''), 10);
    //console.log(num);
    return num;
}

var parsePercent = function (el) {
    var num = parseFloat($.text([el]).replace('%', ''), 10) * ($(el).hasClass('minus') ? -1 : 1);
    //console.log(num);
    return num;
}

var setting = {
       'sbtn1': parseUpDown,
       'sbtn2': parseNum,
       'sbtn3': parseNum,
       'sbtn4': parseNum,
       'sbtn5': parsePercent,
       'sbtn6': parsePercent
   };

$(function(){
	login.loginerror();
	$("#indexLoginPwd").on("focus",function(){
		login.indexLoginOnblur();
		util.callbackEnter(login.loginIndexSubmit);
	});
	$("#loginbtn").on("click",function(){
		login.loginIndexSubmit();
	});
	login.switchCoin();
	var v = $('#alert').val();
    if(v == 1){
    	var modal = $("#msgdetail");
    	modal.modal('show');
    }
	
	login.refreshMarket();
	$(".homepage-lump .col-sm-2").hover(function(){
		$(this).addClass("current")
		},function(){
			$(this).removeClass("current")
			});
	
    $("table[name='main-table']").each(function () {
        $(this).find('.sort').each(function (index, el) {
            var $th = $(this);
            var inverse = false;
            var thIndex = $th.parent().index();
            index++;

            $th.click(function () {
                $th.closest("table").find('td').filter(function () {
                    return $(this).index() === thIndex;
                }).sortElements(function (a, b) {
                    return setting['sbtn' + index](a) > setting['sbtn' + index](b) ?
                      inverse ? -1 : 1
                      : inverse ? 1 : -1;
                }, function () {
                    return this.parentNode;
                });
                inverse = !inverse;
            });
        });
    });

    $(".flexslider").flexslider({
        directionNav: true,
        pauseOnAction: false
    });

});