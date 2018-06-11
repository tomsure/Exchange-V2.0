    
   function resetStyle() {
      $('div.has-success input.grayBorder').css('border-color', 'gray')
      $('span.glyphicon-ok').remove()
  }
   
        var timestamp=new Date().getTime();
          RequestId=timestamp.toString()   	  

    	 var ndate=new Date()  
           var dateTime=ndate.getFullYear() + '-' + (ndate.getMonth() +1)+ '-' +ndate.getDate()
           var nowTime=(new Date(Date.parse(dateTime)).getTime())/1000
           startTime=new Date(Date.parse($('#startDate').val())).getTime()/1000  // 起始时间
           endTime=new Date(Date.parse($('#endDate').val())).getTime()/1000 // 结束时间
           var day7=nowTime - 7*24*60*60;
           var day15=nowTime - 15*24*60*60;
           var day30=nowTime - 30*24*60*60;
           var selectTime=null     
           var limitedays=[]
           var selectDay=function(){ // 选择时间
             $('#tableBar span.form-inline').click(function(e){
               	  if(e.target.innerText=='Today'){
             	  	selectTime=nowTime
                      limitedays=[]
             	  	  limitedays.push(selectTime)
             	  }else if(e.target.innerText=='7day'){
             	  	 selectTime=day7
                      limitedays=[]
             	  	 limitedays.push(selectTime)
             	  }else if(e.target.innerText=='15day'){
             	  		selectTime=day15
                         limitedays=[]
             	  		limitedays.push(selectTime)
             	  }else if(e.target.innerText=='30day'){
             	  		selectTime=day30
                         limitedays=[]
             	  		limitedays.push(selectTime)
             	  } 
              })
             
               
            
      }
            
// selectDay()
// var m1=nowTime-60;
// var m5=nowTime-5*60;
//             
// var m15=nowTime-15*60;
// var m30=nowTime-30*60;
// var h1=nowTime-60*60;
// var h2=nowTime-2*60*60;
// var h4=nowTime-4*60*60;
// var h6=nowTime-6*60*60;
// var h12=nowTime-12*60*60;
// var D1=nowTime-24*60*60;
// var W1=nowTime-7*24*60*60;
 
                var M1='M1';
               var M5='M5'
               var M15='M15';
               var M30='M30';
               var H1='H1';

               var H4='H4';

               var H12='H12';
               var D1='D1';
               var W1='W1';
               var MN='MN'
           	    
           	    
           	    
           	    
           	    
    function timestampToTime(timestamp) {  // 时间戳转换时间
        var date = new Date(timestamp * 1000);// 时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
//      D = date.getDate() + ' ';
         D= date.getDate()<10 ? "0" + date.getDate() + ' ' : date.getDate()+ ' ';
//      h = date.getHours() + ':';
         h=date.getHours()<10 ? '0'+ date.getHours() + ':': date.getHours() + ':';
//      m = date.getMinutes() + ':';
          m=date.getMinutes()<10 ? '0'+ date.getMinutes() + ':': date.getMinutes() + ':';
//      s = date.getSeconds();
          s=date.getSeconds()<10 ? '0'+ date.getSeconds() : date.getSeconds();
        return Y+M+D+h+m+s;
    }
// console.log(timestampToTime(14036925));
     
       /**
		 * 把时间段转成对应的开始时间和结束时间
		 */
     var SelectTimePeriod = function(){
    	 
    	 $('#tableBar span.form-inline').click(function(e){
    	 	     $(e.target).css({
    	 	     	
    	 	     	  'background':'#85181e',
    	 	     	  'color':'white'
    	 	     })
    	 	     $(e.target).siblings('span').css({
    	 	     	 'background':'#e8edf0',
    	 	     	  'color':'black'
    	 	     })
          	  if(e.target.innerText=='Today'){
          		 begin = fun_date(0);
        	  }else if(e.target.innerText=='7day'){
        		  begin = fun_date(-7);
        	  }else if(e.target.innerText=='15day'){
        		  begin = fun_date(-15);
        	  }else if(e.target.innerText=='30day'){
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
     function fun_date(days){
         var date1 = new Date(),
         time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();// time1表示当前时间
         var date2 = new Date(date1);
         date2.setDate(date1.getDate()+days);
         return date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
     }
           	    
//     表单重置样式    	    
      function resetStyle() {
      $('div.has-success input.grayBorder').css('border-color', 'gray')
      $('span.glyphicon-ok').remove()
  }     	    
      
     //取消表单input框自动提示
//     $('input[type="text"],input[type="password"]').attr('autocomplete','off')
           	    	 $('input').attr('autocomplete','off')