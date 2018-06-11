// function resetStyle() {
//    $('div.has-success input.grayBorder').css('border-color', 'gray')
//    $('span.glyphicon-ok').remove()
//}
function resetStyle() {
	$('div.has-success ').removeClass('has-success')
	$('span.glyphicon-ok').remove()
}
var timestamp = new Date().getTime();
RequestId = timestamp.toString()

var ndate = new Date()
var dateTime = ndate.getFullYear() + '-' + (ndate.getMonth() + 1) + '-' + ndate.getDate()
var nowTime = (new Date(Date.parse(dateTime)).getTime()) / 1000
startTime = new Date(Date.parse($('#startDate').val())).getTime() / 1000 // 起始时间
endTime = new Date(Date.parse($('#endDate').val())).getTime() / 1000 // 结束时间
var day7 = nowTime - 7 * 24 * 60 * 60;
var day15 = nowTime - 15 * 24 * 60 * 60;
var day30 = nowTime - 30 * 24 * 60 * 60;
var selectTime = null
var limitedays = []
var selectDay = function() { // 选择时间
	$('#tableBar span.form-inline').click(function(e) {
		if(e.target.innerText == 'Today') {
			selectTime = nowTime
			limitedays = []
			limitedays.push(selectTime)
		} else if(e.target.innerText == '7day') {
			selectTime = day7
			limitedays = []
			limitedays.push(selectTime)
		} else if(e.target.innerText == '15day') {
			selectTime = day15
			limitedays = []
			limitedays.push(selectTime)
		} else if(e.target.innerText == '30day') {
			selectTime = day30
			limitedays = []
			limitedays.push(selectTime)
		}
	})

}
var M1 = 'M1';
var M5 = 'M5'
var M15 = 'M15';
var M30 = 'M30';
var H1 = 'H1';

var H4 = 'H4';

var H12 = 'H12';
var D1 = 'D1';
var W1 = 'W1';
var MN = 'MN'

function timestampToTime(timestamp) { // 时间戳转换时间
	var date = new Date(timestamp * 1000); // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';

	D = date.getDate() < 10 ? "0" + date.getDate() + ' ' : date.getDate() + ' ';

	h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';

	m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';

	s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
	return Y + M + D + h + m + s;
}

/**
 * 把时间段转成对应的开始时间和结束时间
 */
var SelectTimePeriod = function() {

	$('#tableBar span.form-inline').click(function(e) {
		$(e.target).css({

			'background': '#85181e',
			'color': 'white'
		})
		$(e.target).siblings('span').css({
			'background': '#e8edf0',
			'color': 'black'
		})
		if(e.target.innerText == 'Today') {
			begin = fun_date(0);
		} else if(e.target.innerText == '7days') {
			begin = fun_date(-7);
		} else if(e.target.innerText == '15days') {
			begin = fun_date(-15);
		} else if(e.target.innerText == '30days') {
			begin = fun_date(-30);;
		}
		$("#startDate").val(begin);
		$('#endDate').val(dateTime);
	})

}

/**
 * 获取多少天
 * 
 * @param days
 * @returns
 */
function fun_date(days) {
	var date1 = new Date(),
		time1 = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate(); // time1表示当前时间
	var date2 = new Date(date1);
	date2.setDate(date1.getDate() + days);
	return date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
}

//     表单重置样式    	    
function resetStyle() {
	$('div.has-success input.grayBorder').css('border-color', 'gray')
	$('span.glyphicon-ok').remove()
}

//取消表单input框自动提示
//     
$('input').attr('autocomplete', 'off')

function accMul(arg1, arg2) //精准乘法计算
{
	var m = 0,
		s1 = arg1.toString(),
		s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch(e) {}
	try {
		m += s2.split(".")[1].length
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

// toFixed兼容方法
Number.prototype.toFixed = function(n) {
	if(n > 20 || n < 0) {
		throw new RangeError('toFixed() digits argument must be between 0 and 20');
	}
	var number = this;
	if(isNaN(number) || number >= Math.pow(10, 21)) {
		return number.toString();
	}
	if(typeof(n) == 'undefined' || n == 0) {
		return(Math.round(number)).toString();
	}

	var result = number.toString();
	arr = result.split('.');

	// 整数的情况
	if(arr.length < 2) {
		result += '.';
		for(var i = 0; i < n; i += 1) {
			result += '0';
		}
		return result;
	}

	var integer = arr[0];
	var decimal = arr[1];
	if(decimal.length == n) {
		return result;
	}
	if(decimal.length < n) {
		for(var i = 0; i < n - decimal.length; i += 1) {
			result += '0';
		}
		return result;
	}
	result = integer + '.' + decimal.substr(0, n);
	var last = decimal.substr(n, 1);

	// 四舍五入，转换为整数再处理，避免浮点数精度错误
	if(parseInt(last, 10) >= 5) {
		var x = Math.pow(10, n);
		result = (Math.round((parseFloat(result) * x)) + 1) / x;
		result = result.toFixed(n);
	}

	return result;
};

//折叠菜单

$(function() {
	$('#collapsefour').collapse('show')
});
$(function() {
	$('#collapseTwo').collapse('show')
});
$(function() {
	$('#collapseThree').collapse('show')
});
$(function() {
	$('#collapseOne').collapse('show')
});

function computedFloat(floatEle){    //处理小数点位数过长，最长保留8位
    	floatEle.on('keyup',function(event){
    		var $amountInput = $(this);
        event = window.event || event;
    if (event.keyCode == 37 | event.keyCode == 39) {
        return;
    }
  $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d\d\d).*$/, '$1$2.$3'));
    })
    	floatEle.on ('blur', function () {
    var $amountInput = $(this);
    //最后一位是小数点的话，移除
    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
    
});
    }


var floatTool = function() {

			/*
			 * 判断obj是否为一个整数
			 */
			function isInteger(obj) {
				return Math.floor(obj) === obj
			}

			function toInteger(floatNum) {
				var ret = {
					times: 1,
					num: 0
				}
				if(isInteger(floatNum)) {
					ret.num = floatNum
					return ret
				}
				var strfi = floatNum + ''
				var dotPos = strfi.indexOf('.')
				var len = strfi.substr(dotPos + 1).length
				var times = Math.pow(10, len)
				var intNum = parseInt(floatNum * times + 0.5, 10)
				ret.times = times
				ret.num = intNum
				return ret
			}

			
			function operation(a, b, op) {
				var o1 = toInteger(a)
				var o2 = toInteger(b)
				var n1 = o1.num
				var n2 = o2.num
				var t1 = o1.times
				var t2 = o2.times
				var max = t1 > t2 ? t1 : t2
				var result = null
				switch(op) {
					case 'add':
						if(t1 === t2) { // 两个小数位数相同
							result = n1 + n2
						} else if(t1 > t2) { // o1 小数位 大于 o2
							result = n1 + n2 * (t1 / t2)
						} else { // o1 小数位 小于 o2
							result = n1 * (t2 / t1) + n2
						}
						return result / max
					case 'subtract':
						if(t1 === t2) {
							result = n1 - n2
						} else if(t1 > t2) {
							result = n1 - n2 * (t1 / t2)
						} else {
							result = n1 * (t2 / t1) - n2
						}
						return result / max
					case 'multiply':
						result = (n1 * n2) / (t1 * t2)
						return result
					case 'divide':
						return result = function() {
							var r1 = n1 / n2
							var r2 = t2 / t1
							return operation(r1, r2, 'multiply')
						}()
				}
			}

			// 加减乘除的四个接口
			function add(a, b) {
				return operation(a, b, 'add')
			}

			function subtract(a, b) {
				return operation(a, b, 'subtract')
			}

			function multiply(a, b) {
				return operation(a, b, 'multiply')
			}

			function divide(a, b) {
				return operation(a, b, 'divide')
			}

			// exports
			return {
				add: add,
				subtract: subtract,
				multiply: multiply,
				divide: divide
			}
		}();
		
//  用法 实例 乘法： floatTool.multiply(0.000021, 0.333212)

function scientificToNumber(num) {  //科学计算转字符串
            var str = num.toString();
            var reg = /^(\d+)(e)([\-]?\d+)$/;
            var arr, len,
                zero = '';

            /*6e7或6e+7 都会自动转换数值*/
            if (!reg.test(str)) {
                return num;
            } else {
                /*6e-7 需要手动转换*/
                arr = reg.exec(str);
                len = Math.abs(arr[3]) - 1;
                for (var i = 0; i < len; i++) {
                    zero += '0';
                }

                return '0.' + zero + arr[1];
            }
        }
