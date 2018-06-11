String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		for ( var i = 0; i < arguments.length; i++) {
			if (arguments[i] != undefined) {
				var reg = new RegExp("({[" + i + "]})", "g");
				result = result.replace(reg, arguments[i]);
			}
		}
	}
	return result;
};

function centerModals() {
	$('.modal').each(function(i) {
		var $clone = $(this).clone().css('display', 'block').appendTo('body');
		var modalHeight = $clone.find('.modal-content').height();
		var width = $clone.find('.modal-content').width();
		var top = Math.round(($clone.height() - modalHeight) / 2);
		top = top > 0 ? top : 0;
		$clone.remove();
		$(this).find('.modal-content').css("margin-top", top);
		$(this).find('.modal-mark').css("height", modalHeight + 20).css("width", width + 20);
	});
}

$('.modal').on('show.bs.modal', centerModals);
$(window).on('resize', centerModals);

util.lrFixFooter("#allFooter");

$(function() {
	var speed = 8000;
	var count = 0;
	var newstoplist = jQuery("#newsList p");
	var sumCount = jQuery("#newsList p").length;
	function Marquee() {
		jQuery("#newsList p").hide();
		if(count>sumCount){
			count=0;
		}
		jQuery("#newsList p:eq("+count+")").fadeToggle(2000);
		++count;
	}
	Marquee();
	var MyMar = setInterval(Marquee, speed);
	newstoplist.onmouseover = function() {
		clearInterval(MyMar);
	};
	newstoplist.onmouseout = function() {
		MyMar = setInterval(Marquee, speed);
	};
	

		$(document)
			.ready(
					function() {
						$(".side ul li").hover(function() {
							$(this).find(".sidebox").stop().animate({
								"width" : "200px"
							}, 200).css({
								"opacity" : "1",
								"filter" : "Alpha(opacity=100)",
								"background" : "#e74e19"
							})
						}, function() {
							$(this).find(".sidebox").stop().animate({
								"width" : "84px"
							}, 200).css({
								"opacity" : "0.8",
								"filter" : "Alpha(opacity=80)",
								"background" : "#5e5e5e"
							})
						});
					});
	function goTop() {
		$('html,body').animate({
			'scrollTop' : 0
		}, 500)
	}
});

$(function(){
	$(".leftmenu-folding").on("click",function(){
		var that=$(this);
		$("."+that.data().folding).slideToggle("fast"); 
	});
});

/** *******QQ登录****************************** */	
function openqq(url){
	if(url==null||url==""){
		url=window.location.href;
	}
	window.open('/qqLogin?url='+url,'new','height='+550+',,innerHeight='+550+',width='+800+',innerWidth='+800+',top='+200+',left='+200+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}

function showTips(text){
	util.showerrortips("", text, {
		okbtn : function() {
			$('#alertTips').modal('hide');
		}
	});
}

$(function(){
	if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1){
	    var inputers = document.getElementsByTagName("input");  
	    for(var i=0;i<inputers.length;i++){  
	        if((inputers[i].type !== "submit") && (inputers[i].type !== "password")){  
	            inputers[i].disabled= true;  
	        }  
	    }  
	    setTimeout(function(){  
	        for(var i=0;i<inputers.length;i++){  
	            if(inputers[i].type !== "submit"){  
	                inputers[i].disabled= false;  
	            }  
	        }  
	    },100);
	}
	});

/*
 * jQuery placeholder, fix for IE6,7,8,9
 * @author JENA
 * @since 20131115.1504
 * @website ishere.cn
 */
var JPlaceHolder = {
    //检测
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //修复
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.wrap($('<div></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left, top:'8px', height:h, lienHeight:h, paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if(!self.val()){
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//执行
$(function(){
    JPlaceHolder.init();    
});