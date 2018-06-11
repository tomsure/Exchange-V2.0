package com.ruizton.main.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruizton.main.model.Fuser;
import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
/**
 * 请求重定向
 * **/
public class UrlRedirectFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response ;
		
		String uri = req.getRequestURI().toLowerCase().trim() ;
		
		String servletName = req.getServerName().toLowerCase() ;
		try {
			if(servletName.indexOf("yunshu333.com")!=-1  ){
				resp.sendRedirect("https://www.yunshu333.cn");
				return ;
			}
		} catch (Exception e) {
		}

		
		//不接受任何jsp请求
		if(uri.endsWith(".jsp")){
			return ;
		}

		if("/".equalsIgnoreCase(uri) || "/index.html".equalsIgnoreCase(uri))
		{
			resp.sendRedirect("/financial/index.html") ;
			return;
		}

		//只拦截.html结尾的请求
		if(!uri.endsWith(".html") || uri.startsWith("/m/")){
			chain.doFilter(request, response) ;
			return ;
		}/*else{
			//moo
			String redirect = Mobilutils.Redirect(req,uri) ;
			if(redirect!=null ){
				resp.sendRedirect(redirect);
				return ;
			}
		}*/

		
		///////////////////////////////////////////////////////////////////////////////
		if(uri.startsWith("/indexbitbs.html")//测试首页
				||uri.startsWith("/appapi.html")//app apiiiiii
				||uri.startsWith("/app/article.html")

				||uri.startsWith("/qqLogin")//qq
				||uri.startsWith("/link/wx/callback")//qq
				||uri.startsWith("/link/qq/call")//qq
				||uri.startsWith("/error/")//error
				||uri.startsWith("/api/")//api
				||uri.startsWith("/data/ticker.html")
				||uri.startsWith("/user/sendfindpasswordmsg")//api
				||uri.startsWith("/user/sendregmsg")//api
				||uri.startsWith("/json/findpassword")//api
				||uri.startsWith("/line/period-btcdefault.html")
				||uri.startsWith("/data/depth-btcdefault.html")
				||uri.startsWith("/data/stock-btcdefault.html")
				||uri.startsWith("/index_chart.html")
			||uri.startsWith("/user/login")//登陆
			||uri.startsWith("/user/logout")//退出
			||uri.startsWith("/user/reg")//注册
			||uri.startsWith("/trademarket.html")//注册
			||uri.startsWith("/app/suc.html")//注册
			||uri.startsWith("/real/")//行情
			||uri.startsWith("/market")//行情
			||uri.startsWith("/block/")
			||uri.startsWith("/kline/")//行情
			||uri.startsWith("/json.html")//行情
			||uri.startsWith("/json/")//行情
			||uri.startsWith("/validate/")//邮件激活,找回密码
			||uri.startsWith("/about/")//文章管理
			||(uri.startsWith("/crowd/index"))
			||uri.startsWith("/service/")//文章管理
			||uri.startsWith("/shop/index.html")//文章管理
			||uri.startsWith("/shop/view.html")//文章管理
			||uri.startsWith("/shop/showdetails")//文章管理
			||uri.startsWith("/dowload/index")//文章管理
			||uri.startsWith("/vote/list")//文章管理
			||uri.startsWith("/business.html")//文章管理
			||uri.startsWith("/vote/details")//文章管理
			||uri.startsWith("/trade/type")//文章管理
			||uri.startsWith("/trade/coin")//文章管理
			||uri.startsWith("/trade/entrustinfo")
			||uri.startsWith("/user/sendMailCode".toLowerCase())//注册邮件
			||uri.startsWith("/user/sendMsg".toLowerCase())//注册短信
			){
			chain.doFilter(request, response) ;
			return ;
		}else{
			
			
			if(uri.startsWith("/ssadmin/")){
				//后台
				if(uri.startsWith("/ssadmin/login_zblt.html")
						||uri.startsWith("/ssadmin/submitlogin_zblt.html")
						|| req.getSession().getAttribute("login_admin")!=null){
					chain.doFilter(request, response) ;
				}else{
					resp.sendRedirect("/") ;
				}
				return ;
			}else{
				
				Object login_user = req.getSession().getAttribute("login_user") ;
				if(login_user==null){
					resp.sendRedirect("/user/login.html?forwardUrl="+req.getRequestURI().trim()) ;
					return ;
				}else{
					if( ((Fuser)login_user).getFpostRealValidate() ){
						//提交身份认证信息了
						chain.doFilter(request, response) ;
						return ;
					}else{
						if(uri.startsWith("/user/realCertification.html".toLowerCase())
							||uri.startsWith("/user/validateidentity.html")
							){
							chain.doFilter(request, response) ;
						}else{
							resp.sendRedirect("/user/realCertification.html") ;
						}
						return ;
					}
				}
				
			}
			
			
		}
		
	}


}
