$(document).ready(function(){
	$(".download-ma").hover(function(){
		$(".ma-show").fadeIn();
		$(".download-ma i img").css('transform','rotate(180deg)');
	},function(){
		$(".ma-show").fadeOut();
		$(".download-ma i img").css('transform','rotate(0)');
	});
	
	$(".menu-list ul li").hover(function(){
		$(this).children('.list_cont').show();
	},function(){
		$(this).children('.list_cont').hide();
	});
	
	
	
	var index = 0;
	var imgnum = $('.banner ul li').length;
	var nmun = '';
	var imgwidth = $('.banner ul li').width();
	var allimgwidth = imgwidth * imgnum;

	var shangtime = new Date().getTime();

	$(".banner ul li").width($(document).width());
	$('.banner ul').css('width', allimgwidth);

	$('.banner').hover(function() {
		clearInterval(p);
		$('.banner .u').fadeIn();
	}, function() {
		pic();
		$('.banner .u').fadeOut();
	});

	function pic() {

		p = setInterval(function() {
			index++;

			if(index >= imgnum) {
				index = 0;
			}

			selectimg(index);

		}, 3000);

	}

	function selectimg(index) {
		$('.banner ul').animate({
			'margin-left': '-' + imgwidth * index + 'px'
		}, 600);
		$('.con a').eq(index).addClass('active').siblings('a').removeClass('active');
		shangtime = new Date().getTime();
	}

	$('.banner .left').click(function() {
		if(new Date().getTime()-shangtime < 600){
			return;
		}
		index -= 1;
		if(index < 0) {
			index = imgnum - 1;
		}
		selectimg(index);
	});

	$('.banner .right').click(function() {
		if(new Date().getTime()-shangtime < 600){
			return;
		}
		index += 1;
		if(index > imgnum - 1) {
			index = 0;
		}
		selectimg(index);

	});

	for(var ni = 0; ni < imgnum; ni++) {
		nmun += "<a href='javascript:;'></a>";
	}
	$('.banner .con').html(nmun);
	$('.banner .con a').eq(0).addClass('active');

	$('.con a').each(function(index) {
		$(this).click(function() {
			$('.banner ul').animate({
				'margin-left': '-' + imgwidth * index + 'px'
			}, 600);
			$('.con a').eq(index).addClass('active').siblings('a').removeClass('active');
		});
	});

	pic();
	
	
	var clickNumber =0; 
	$(".table_list table tr td span.sort").on('click',function(){
		$(".table_list table tr td span.sort").removeClass('active');
		$(this).addClass('active');
		if(clickNumber %2==0){ 
			$(".table_list table tr td span.sort img.show").hide();
			$(".table_list table tr td span.sort img.hide").show();
			$(this).children('.hide').hide();
			$(this).children('.show').show();
			$(this).children('.show').css('transform','rotate(0deg)');
		}else{ 
			$(this).children('.show').css('transform','rotate(180deg)');
		} 
		clickNumber ++;
	});
	
	$(".tab-cont ul li").on('click',function(){
		$(this).addClass('active').siblings().removeClass('active');
		var _index = $(".tab-cont ul li").index(this);
		$(".table_list").eq(_index).show().siblings('.table_list').hide();
	});
	
	
	$(".true").on('click',function(){
		$(this).hide();
		$(".false").show();
	});
	
	$(".false").on('click',function(){
		$(this).hide();
		$(".true").show();
	});
	
	$(".register-tab ul li").on('click',function(){
		
		$(this).addClass('active').siblings().removeClass('active');
		var _index = $(".register-tab ul li").index(this);
		$(".register-btn").eq(_index).show().siblings('.register-btn').hide();
		
	});
	
})
