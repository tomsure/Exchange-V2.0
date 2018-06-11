<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
</head>
<body>
	


<%@include file="../comm/header.jsp" %>

 

	<div class="container-full">
		<div class="container displayFlex">
		<div class="row">

<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">
						<div class="user-top-icon">
							<div class="col-xs-2 text-center">
								<i class="top-icon"></i>
							</div>
							<div class="col-xs-6 padding-left-clear">
								<div class="h5">
									
										<span class="top-title1">${fuser.frealName}</span>
									
									<span class="top-vip vip${level }">VIP${level }</span>
								</div>
								<div>
									<span class="top-title2">UID:${fuser.fid }</span><span class="top-title3">${loginName}</span>
								</div>
								<div>
									<span class="top-title2">Invitation Code:${fn:substring(fuser.fid*110033, 0, 6)}${fuser.fid}</span>
								</div>
							</div>
							<div class="col-xs-4" style="margin-top: -40px;">
								<h5 class="top-title4">
									You have set up <span class="top-title6"> ${bindType } </span> security protection methods, remainning 
									<span class="top-title6">${5-bindType } </span> security protection methods to be set.
								</h5>
								<h5 class="top-title5">This platform protects your account and fund security in real time.</h5>
							</div>
						</div>
						<div class="col-xs-12 padding-clear">
							<div class="con-items">
								
									
										<div class="title">
											<span>Email</span>
											<c:if test="${isBindEmail == false }">
														<a class="text-primary text-link" href="#" data-toggle="modal" data-target="#bindemail">Bind>></a>
													</c:if>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="email ${isBindEmail == true?'':'settting' }"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-${isBindEmail == false?'no':'ok' }">${isBindEmail == false?'Unverified':'Verified' }</p>
												<c:if test="${isBindEmail == true}">
												<!--<p>The Email you bind is：</p>-->
												<p>${email}</p>
												</c:if>
											</div>
										</div>
									
									
									
								
							</div>
							<div class="con-items center">


										<div class="title">
											<span>Mobile Number</span>
									        <c:if test="${isBindTelephone == false }">
												<a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#bindphone">Bind&gt;&gt;</a>
											</c:if>
											<c:if test="${isBindTelephone == true}">
											<a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#unbindphone">Modify&gt;&gt;</a>
											</c:if>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="phone ${isBindTelephone == true?'':'settting' }"></i></span>
											</div>

											<div class="content-rt">
												<p class="icon-${isBindTelephone == false?'no':'ok' }">${isBindTelephone == false?'Unverified':'Verified' }</p>
												<c:if test="${isBindTelephone == true}">
												<!--<p>The mobile you bind is：</p>-->
												<p>${telNumber}</p>
												</c:if>
											</div>

										</div>

							</div>
							<div class="con-items">


										<div class="title">
											<span>Google Authentication</span>
									        <c:choose>
											<c:when test="${isBindGoogle }">
											<a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#unbindgoogle">View&gt;&gt;</a>
											</c:when>
											<c:otherwise>
											<a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#bindgoogle">Bind&gt;&gt;</a>
											</c:otherwise>
											</c:choose>

										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="google ${isBindGoogle == true?'':'settting' }"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-${isBindGoogle == false?'no':'ok' }">${isBindGoogle == false?'Unverified':'Verified' }</p>
												<p>Enter Google auth code, modify password and security settings.</p>
											</div>
										</div>



							</div>
							<div class="con-items">
								
									
										<div class="title">
											<span>Login Password</span>
											<a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#unbindloginpass">Modify&gt;&gt;</a>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="login"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-ok">Already Set</p>
											</div>
										</div>
									
									
								
							</div>
							<div class="con-items center">
								
									
										<div class="title">
											<span>Transaction Password</span>
											<c:if test="${isTradePassword == false }">
												<a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#bindtradepass">Set Up>></a>
											</c:if>
											<c:if test="${isTradePassword == true }">
											    <a class="text-link" href="javascript:void(0)" data-toggle="modal" data-target="#unbindtradepass">Modify&gt;&gt;</a>
											</c:if>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="trade ${isTradePassword == true?'':'settting' }"  style="margin-top: 1px;"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-${isTradePassword == false?'no':'ok' }">${isTradePassword == false?'Not Set':'Already Set' }</p>
											</div>
										</div>
									
									
								
							</div>
							<div class="con-items">
								
									
										<div class="title">
											<span>KYC Authentication</span>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="realname"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-ok">Authenticated</p>
											</div>
										</div>
									
									
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<!-- 修改绑定手机 -->
	<c:if test="${isBindTelephone ==true}">
			<div class="modal modal-custom fade" id="unbindphone" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title">Modify Mobile Number</span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group text-center">
								<span class="modal-info-tips">
									You are now charging the mobile number for account
									<span class="text-danger">${loginName}</span>
								</span>
							</div>
							<div class="form-group ">
								<label for="unbindphone-phone" class="col-xs-3 control-label">Original Phone </label>
								<div class="col-xs-6">
									<span id="unbindphone-phone" class="form-control border-fff" type="text">${telNumber}</span>
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindphone-msgcode" class="col-xs-3 control-label">SMS Code</label>
								<div class="col-xs-6">
									<input id="unbindphone-msgcode" class="form-control" type="text">
									<button id="unbindphone-sendmessage" data-msgtype="3" data-tipsid="unbindphone-errortips" class="btn btn-sendmsg">Send</button>
								</div>
							</div>
							<div class="form-group " style="display: none">
								<label for="unbindphone-areaCode" class="col-xs-3 control-label">Address</label>
								<div class="col-xs-6">
									<select class="form-control" id="unbindphone-areaCode">
										<option value="+86">China</option>
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindphone-newphone" class="col-xs-3 control-label">Change Phone </label>
								<div class="col-xs-6">
									<span id="unbindphone-newphone-areacode" class="btn btn-areacode" style="display: none">+86</span>
									<input id="unbindphone-newphone" class="form-control" type="text">
								</div>
							</div>
						
						<c:if test="${isBindTelephone }">		
							<div class="form-group ">
								<label for="unbindphone-newmsgcode" class="col-xs-3 control-label">SMS Code</label>
								<div class="col-xs-6">
									<input id="unbindphone-newmsgcode" class="form-control" type="text">
									<button id="unbindphone-newsendmessage" data-msgtype="2" data-tipsid="unbindphone-errortips" class="btn btn-sendmsg">Send</button>
								</div>
							</div>
						</c:if>	
						
						<c:if test="${isBindGoogle ==true}">	
								<div class="form-group">
									<label for="unbindphone-googlecode" class="col-xs-3 control-label">Google Verification Code</label>
									<div class="col-xs-6">
										<input id="unbindphone-googlecode" class="form-control" type="text">
									</div>
								</div>
						</c:if>		
							
							<div class="form-group ">
								<label for="unbindphone-imgcode" class="col-xs-3 control-label">Code</label>
								<div class="col-xs-6">
									<input id="unbindphone-imgcode" class="form-control" type="text">
									<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
								</div>
							</div>
							<div class="form-group">
								<label for="unbindphone-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindphone-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="unbindemail-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="unbindphone-Btn" class="btn btn-danger btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</c:if>	
	
	
		
		
			<!-- 谷歌查看 -->
			<c:if test="${isBindGoogle ==true}">
			<div class="modal modal-custom fade" id="unbindgoogle" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title">Google Authenticator</span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group unbindgoogle-hide-box" style="display:none">
								<div class="col-xs-12 clearfix">
									<div id="unbindgoogle-code" class="form-control border-fff ">
										<div class="form-qrcode" id="unqrcode"></div>
									</div>
								</div>
							</div>
							<div class="form-group unbindgoogle-hide-box" style="display:none">
								<label for="unbindgoogle-key" class="col-xs-3 control-label">Key</label>
								<div class="col-xs-6">
									<span id="unbindgoogle-key" class="form-control border-fff"></span>
								</div>
							</div>
							<div class="form-group unbindgoogle-hide-box" style="display:none">
								<label for="unbindgoogle-device" class="col-xs-3 control-label">Device Name</label>
								<div class="col-xs-6">
									<span id="unbindgoogle-device" class="form-control border-fff">${device_name }</span>
								</div>
							</div>
							<div class="form-group unbindgoogle-show-box">
								<label for="unbindgoogle-topcode" class="col-xs-3 control-label">Google Verification Code</label>
								<div class="col-xs-6">
									<input id="unbindgoogle-topcode" class="form-control" type="text">
								</div>
							</div>
							<div class="form-group unbindgoogle-show-box">
								<label for="unbindgoogle-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindgoogle-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group unbindgoogle-show-box">
								<label for="unbindgoogle-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="unbindgoogle-Btn" class="btn btn-danger btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</c:if>
	
	
		<!-- 邮箱绑定 -->
		<div class="modal modal-custom fade" id="bindemail" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span class="modal-title">Bind Email</span>
					</div>
					<div class="modal-body form-horizontal">
						<div class="form-group ">
							<label for="bindemail-email" class="col-xs-3 control-label">Email</label>
							<div class="col-xs-6">
								<input id="bindemail-email" class="form-control" type="text">
							</div>
						</div>
						<div class="form-group">
							<label for="bindemail-errortips" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<span id="bindemail-errortips" class="text-danger"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<button id="bindemail-Btn" class="btn btn-danger btn-block">Submit</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	
		
			<!-- 绑定手机 -->
			<div class="modal modal-custom fade" id="bindphone" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title">Bind Mobile Number</span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group text-center">
								<span class="modal-info-tips">
									You are now charging the email for 
									<span class="text-danger">${loginName}</span>
								</span>
							</div>
							<div class="form-group " style="display: none">
								<label for="bindphone-areaCode" class="col-xs-3 control-label">Address</label>
								<div class="col-xs-6">
									<select class="form-control" id="bindphone-areaCode">
										<option value="+86">China</option>
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label for="bindphone-newphone" class="col-xs-3 control-label">Phone</label>
								<div class="col-xs-6">
									<span id="bindphone-newphone-areacode" class="btn btn-areacode" style="display: none">+86</span>
									<input id="bindphone-newphone" class="form-control" type="text">
								</div>
							</div>
							<div class="form-group ">
								<label for="bindphone-msgcode" class="col-xs-3 control-label">SMS Authentication Code</label>
								<div class="col-xs-6">
									<input id="bindphone-msgcode" class="form-control" type="text">
									<button id="bindphone-sendmessage" data-msgtype="2" data-tipsid="bindphone-errortips" class="btn btn-sendmsg">Send</button>
								</div>
							</div>
							
							<c:if test="${isBindGoogle ==true}">
							<div class="form-group">
									<label for="bindphone-googlecode" class="col-xs-3 control-label">Google verification code</label>
									<div class="col-xs-6">
										<input id="bindphone-googlecode" class="form-control" type="text">
									</div>
								</div>
							</c:if>	
							
							<div class="form-group ">
								<label for="bindphone-imgcode" class="col-xs-3 control-label">Code</label>
								<div class="col-xs-6">
									<input id="bindphone-imgcode" class="form-control" type="text">
									<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
								</div>
							</div>
							<div class="form-group">
								<label for="bindphone-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="bindphone-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="bindphone-Btn" class="btn btn-danger btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		
		
	
	
		
			<!-- 谷歌绑定 -->
			<div class="modal modal-custom fade" id="bindgoogle" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title">Binding Google Authenticator</span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<div class="col-xs-12 clearfix">
									<div id="bindgoogle-code" class="form-control border-fff  form-qrcodebox">
										<div class="col-xs-12 clearfix form-qrcode-quotes form-qrcode-quotess"></div>
										<div class="form-qrcodebox-cld">
											<div class="form-qrcode-coded">
												<div class="form-qrcode-title text-center">
													<span>Download Google Authenticator</span>
												</div>
												<div class="form-qrcode">
													<img class="form-qrcode-img" src="${requestScope.constant['googleImages'] }"/>
												</div>
											</div>
											<div class="form-qrcode-tips">
												<span>
													Scan to download and install the Google Authenticator on your mobile phone.
													<%--<span class="text-danger">Scan code download</span>--%>
												</span>
											</div>
										</div>
										<div class="form-qrcodebox-cld">
											<div class="form-qrcode-codec">
												<div class="form-qrcode-title text-center">
													<span>Binding Google Authenticator</span>
												</div>
												<div class="form-qrcode" id="qrcode">
												</div>
											</div>
											<div class="form-qrcode-tips">
												<span>
													Scan or enter the key below to generate a dynamic verification code.
													<%--<span class="text-danger">Dynamic verification code</span>--%>
													<%--Fill in the input box below.--%>
												</span>
											</div>
										</div>
										<div class="col-xs-12 clearfix form-qrcode-quotes form-qrcode-quotese"></div>
									</div>
								</div>
							</div>
							<div class="form-group ">
								<label for="bindgoogle-key" class="col-xs-3 control-label">Key</label>
								<div class="col-xs-7">
									<span id="bindgoogle-key" class="form-control border-fff"></span>
									<input id="bindgoogle-key-hide" type="hidden">
								</div>
							</div>
							<div class="form-group ">
								<label for="bindgoogle-device" class="col-xs-3 control-label">Device</label>
								<div class="col-xs-7">
									<span id="bindgoogle-device" class="form-control border-fff">${device_name }</span>
								</div>
							</div>
							<div class="form-group ">
								<label for="bindgoogle-topcode" class="col-xs-3 control-label">Google Verification Code</label>
								<div class="col-xs-7">
									<input id="bindgoogle-topcode" class="form-control" type="text">
								</div>
							</div>
							<div class="form-group">
								<label for="bindgoogle-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-7">
									<span id="bindgoogle-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindgoogle-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-7">
									<button id="bindgoogle-Btn" class="btn btn-danger btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		
		
	
	<!-- 登录密码修改 -->
	<div class="modal modal-custom fade" id="unbindloginpass" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title">Modify Login Password</span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="unbindloginpass-oldpass" class="col-xs-3 control-label">Old Password</label>
						<div class="col-xs-6">
							<input id="unbindloginpass-oldpass" class="form-control" type="password">
						</div>
					</div>
					<div class="form-group ">
						<label for="unbindloginpass-newpass" class="col-xs-3 control-label">New Password</label>
						<div class="col-xs-6">
							<input id="unbindloginpass-newpass" class="form-control" type="password">
						</div>
					</div>
					<div class="form-group ">
						<label for="unbindloginpass-confirmpass" class="col-xs-3 control-label">Confirm Password</label>
						<div class="col-xs-6">
							<input id="unbindloginpass-confirmpass" class="form-control" type="password">
						</div>
					</div>
				
				<c:if test="${isBindTelephone }">	
					<div class="form-group">
						<label for="unbindloginpass-msgcode" class="col-xs-3 control-label">SMS Authentication Code</label>
						<div class="col-xs-6">
							<input id="unbindloginpass-msgcode" class="form-control" type="text">
							<button id="unbindloginpass-sendmessage" data-msgtype="6" data-tipsid="unbindloginpass-errortips" class="btn btn-sendmsg">Send</button>
						</div>
					</div>
				</c:if>	
				
				<c:if test="${isBindGoogle }">
					<div class="form-group">
						<label for="unbindloginpass-googlecode" class="col-xs-3 control-label">Google Verification Code</label>
						<div class="col-xs-6">
							<input id="unbindloginpass-googlecode" class="form-control" type="text">
						</div>
					</div>
				</c:if>	
					
					<div class="form-group">
						<label for="unbindloginpass-errortips" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<span id="unbindloginpass-errortips" class="text-danger "></span>
						</div>
					</div>
					<div class="form-group">
						<label for="unbindloginpass-Btn" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<button id="unbindloginpass-Btn" class="btn btn-danger btn-block">Submit</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		
			<!-- 交易密码设置 -->
			<div class="modal modal-custom fade" id="bindtradepass" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title">Set Transaction Password</span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<label for="bindtradepass-newpass" class="col-xs-3 control-label">New Transaction Password</label>
								<div class="col-xs-6">
									<input id="bindtradepass-newpass" class="form-control" type="password">
								</div>
							</div>
							<div class="form-group ">
								<label for="bindtradepass-confirmpass" class="col-xs-3 control-label">Confirm Password</label>
								<div class="col-xs-6">
									<input id="bindtradepass-confirmpass" class="form-control" type="password">
								</div>
							</div>
							
							<c:if test="${isBindTelephone }">
							<div class="form-group">
									<label for="bindtradepass-msgcode" class="col-xs-3 control-label">SMS Authentication Code</label>
									<div class="col-xs-6">
										<input id="bindtradepass-msgcode" class="form-control" type="text">
										<button id="bindtradepass-sendmessage" data-msgtype="7" data-tipsid="bindtradepass-errortips" class="btn btn-sendmsg">Send</button>
									</div>
								</div>
							</c:if>
							
							<c:if test="${isBindGoogle }">
							<div class="form-group">
									<label for="bindtradepass-googlecode" class="col-xs-3 control-label">Google Verification Code</label>
									<div class="col-xs-6">
										<input id="bindtradepass-googlecode" class="form-control" type="text">
									</div>
								</div>	
							</c:if>
							
							<div class="form-group">
								<label for="bindtradepass-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="bindtradepass-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindtradepass-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="bindtradepass-Btn" class="btn btn-danger btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			
			<!-- 交易密码修改 -->
			<c:if test="${isTradePassword ==true}">
			<div class="modal modal-custom fade" id="unbindtradepass" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title">Modify Transaction Password</span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<label for="unbindtradepass-oldpass" class="col-xs-3 control-label">Old Password</label>
								<div class="col-xs-6">
									<input id="unbindtradepass-oldpass" class="form-control" type="password">
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindtradepass-newpass" class="col-xs-3 control-label">New Password</label>
								<div class="col-xs-6">
									<input id="unbindtradepass-newpass" class="form-control" type="password">
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindtradepass-confirmpass" class="col-xs-3 control-label">Confirm Password</label>
								<div class="col-xs-6">
									<input id="unbindtradepass-confirmpass" class="form-control" type="password">
								</div>
							</div>
							
							<c:if test="${isBindTelephone }">
								<div class="form-group">
									<label for="unbindtradepass-msgcode" class="col-xs-3 control-label">SMS Authentication Code</label>
									<div class="col-xs-6">
										<input id="unbindtradepass-msgcode" class="form-control" type="text">
										<button id="unbindtradepass-sendmessage" data-msgtype="7" data-tipsid="unbindtradepass-errortips" class="btn btn-sendmsg">Send</button>
									</div>
								</div>
							</c:if>
							
							<c:if test="${isBindGoogle }">
								<div class="form-group">
									<label for="unbindtradepass-googlecode" class="col-xs-3 control-label">Google Verification Code</label>
									<div class="col-xs-6">
										<input id="unbindtradepass-googlecode" class="form-control" type="text">
									</div>
								</div>
							</c:if>	
							
							<div class="form-group">
								<label for="unbindtradepass-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindtradepass-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="unbindtradepass-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="unbindtradepass-Btn" class="btn btn-danger btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	
	<input type="hidden" id="changePhoneCode" value="${fuser.ftelephone }" />
	<input type="hidden" id="isEmptyPhone" value="${isBindTelephone ==true?1:0 }" />
	<input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
	<input id="messageType" type="hidden" value="0" />
	




<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/user.security.js"></script>
</body>
</html>
