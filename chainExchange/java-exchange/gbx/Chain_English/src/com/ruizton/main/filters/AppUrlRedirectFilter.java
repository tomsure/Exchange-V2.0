package com.ruizton.main.filters;
//moo
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
/**
 * 请求重定向
 * **/
public class AppUrlRedirectFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response ;
		
		String uri = req.getRequestURI().toLowerCase().trim() ;
		

		//不接受任何jsp请求
		if(uri.endsWith(".jsp")){
			return ;
		}
		
		//只拦截.html结尾的请求
		if(!uri.endsWith(".html")){
			chain.doFilter(request, response) ;
			return ;
		}else{
			//moo
			String redirect = Mobilutils.Redirect(req,uri) ;
			if(redirect!=null ){
				resp.sendRedirect(redirect);
				return ;
			}
		}
		
		
		///////////////////////////////////////////////////////////////////////////////
		if(	uri.startsWith("/m/index.html")//首页
				||uri.startsWith("/m/top5paihang")//app apiiiiii
				||uri.startsWith("/m/json2")//app apiiiiii
				||uri.startsWith("/m/appapi.html")//app apiiiiii
				||uri.startsWith("/m/app/article.html")
				||uri.startsWith("/m/reg-notice")
				||uri.startsWith("/m/validate/reset")
				
				||uri.startsWith("/m/qqLogin")//qq
				||uri.startsWith("/m/link/wx/callback")//qq
				||uri.startsWith("/m/link/qq/call")//qq
				||uri.startsWith("/m/error/")//error
				||uri.startsWith("/m/api/")//api
				||uri.startsWith("/m/data/ticker.html")
				||uri.startsWith("/m/user/sendfindpasswordmsg")//api
				||uri.startsWith("/m/user/sendregmsg")//api
				||uri.startsWith("/m/json/findpassword")//api
				||uri.startsWith("/m/line/period-btcdefault.html")
				||uri.startsWith("/m/data/depth-btcdefault.html")
				||uri.startsWith("/m/data/stock-btcdefault.html")
				||uri.startsWith("/m/index_chart.html")
			||uri.startsWith("/m/user/login")//登陆
			||uri.startsWith("/m/user/logout")//退出
			||uri.startsWith("/m/user/reg")//注册
			||uri.startsWith("/m/trademarket.html")//注册
			||uri.startsWith("/m/app/suc.html")//注册
			||uri.startsWith("/m/real/")//行情
			||uri.startsWith("/m/market")//行情
			||uri.startsWith("/m/block/")
			||uri.startsWith("/m/kline/")//行情
			||uri.startsWith("/m/json.html")//行情
			||uri.startsWith("/m/json/")//行情
			||uri.startsWith("/m/validate/")//邮件激活,找回密码
			||uri.startsWith("/m/about/")//文章管理
			||(uri.startsWith("/m/crowd/index"))
			||uri.startsWith("/m/service/")//文章管理
			||uri.startsWith("/m/shop/index.html")//文章管理
			||uri.startsWith("/m/shop/view.html")//文章管理
			||uri.startsWith("/m/shop/showdetails")//文章管理
			||uri.startsWith("/m/dowload/index")//文章管理
			||uri.startsWith("/m/vote/list")//文章管理
			||uri.startsWith("/m/business.html")//文章管理
			||uri.startsWith("/m/vote/details")//文章管理
			||uri.startsWith("/m/trade/type")//文章管理
			||uri.startsWith("/m/trade/coin")//文章管理
			||uri.startsWith("/m/trade/entrustinfo")
			||uri.startsWith("/m/user/sendMailCode".toLowerCase())//注册邮件
			||uri.startsWith("/m/user/sendMsg".toLowerCase())//注册短信
			){
			chain.doFilter(request, response) ;
			return ;
		}else{
			Object login_user = req.getSession().getAttribute("login_user") ;
			if(login_user==null){
				//back-end:"/ssadmin/login_zblt.html"
				resp.sendRedirect("/m/user/login.html") ;
				return ;
			}
			chain.doFilter(request, response) ;
			return ;
		}
		
	}


}
