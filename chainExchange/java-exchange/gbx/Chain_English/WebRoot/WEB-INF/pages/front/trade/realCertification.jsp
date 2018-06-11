<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getHeader("X-Forwarded-Proto")+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>





 <style type="text/css">
.lead2 {
    font-size: 16px; 
    color: gray; 
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

#leadid {
    font-size: 14px; 
}
</style>



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/kyc.css" type="text/css"></link>
</head>
<body>


<jsp:include page="../comm/header.jsp"></jsp:include>


<div class="container-full">
    <div class="container displayFlex">
<div class="row">
        <jsp:include page="../comm/left_menu.jsp"></jsp:include>


        <div class="col-xs-10 padding-right-clear">
            <div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
                <div class="col-xs-12 rightarea-con">
                    <div class="wrap">
                        <div class="section">
                            <div class="top">
                                <p>Reminder: In order to ensure the security of your account, kindly upload all the relevant documents​.
                                </p>
                            </div>
                            <!-- kyc1验证     -->
                            <div class="kyc_01" style="display: block;">
                                <div class="title">
                                    <span class="s1">KYC1 Authentication</span>
                                    <c:if test="${fuser.fpostRealValidate && fuser.fhasRealValidate}">
                                        <span class="s2">(Authenticated)</span>
                                    </c:if>
                                    <c:if test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                                        <span class="s2">(Not Authenticated)</span>
                                    </c:if>
                                </div>
                                <div class="user_set">    
                                   
                                <div id="leadid" class="lead" >                                                      
                                    <p>You just need to complete the identity authentication for the KYC1.</p>
                                 </div>                                                    
                                    <div class="nav_btn">
                                        <div class="id_card all_card">
                                            <!--p class="p1"><span><em ></em>Identity Verification​</span></p-->
                                            <p class="p3">
                                                <c:if test="${fuser.fpostRealValidate }">
                                                ${fuser.frealName }:${fuser.fidentityNo_s }
                                                </c:if>
                                            </p>

                                            <p class="p2">
                                                <c:if test="${fuser.fpostRealValidate && !fuser.fhasRealValidate}">
                                                   <span class="step1-btn disabled">Verify Now</span>
                                                </c:if>
                                                <c:if test="${fuser.fpostRealValidate && fuser.fhasRealValidate}">
                                                   <span class="step1-btn disabled">Approved</span>
                                                </c:if>
                                                 <c:if test="${!fuser.fpostRealValidate && !fuser.fhasRealValidate}">
                                                    <span class="step1-btn" data-toggle="modal" data-target="#bindrealinfo">Verify Now</span>
                                                </c:if>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- kyc2验证 -->
                            <div class="kyc_02" style="display: ${requestScope.constant['isHideKYC'] ==1?'none;':'block;'}">
                                <div class="title">
                                    <span class="s1">KYC2 Authentication</span>
                                    <span class="s2"></span>
                                </div>
                                <div class="step_all"  style="display: none;">
                                    <div class="left">
                                        <img src="${oss_url}/static/front/images/user/step-1_icon_default.png" alt="">
                                        <p>Photo authentication of related documents</p>
                                    </div>
                                    <%--<div class="right">--%>
                                        <%--<img src="${oss_url}/static/front/images/user/step-1_icon_default.png" alt="">--%>
                                        <%--<p>Step 2 Video authentication</p>--%>
                                    <%--</div>--%>
                                    <p class="zhu">*Please make sure you use your real identity to do this verification.We will protect your personal information.</p>
                                    
                                    
                                </div>
                                <!-- kyc2验证第一步 -->
                            
                                <div class="step_01">
                                    <div class="sub_title">
                                        <span class="s1" style="display:none;">Step</span>
                                        <span class=""  style="text-align:center">Document Verification​</span>
                                    </div>
                                    <p class="mind">Please upload your identity document according to the requirements making sure that the photo is complete and clearly visible, in <span>jpg,bmp,png</span> format, and size limit is <span>1MB</span></p>
                                    <div class="up_img_01 all_up">
                                        <p class="left">The front copy of ID card/Passport (Or you can choose to upload the front copy of your Driver's License if you are United States national or National ID document if you are a resident of European countries)</p>
                                        <br/>
                                        <div class="user_up">
                                            <div class="work_info">
                                                <c:choose>
	                                             <c:when test="${fuser.fhasImgValidate && fuser.fpostImgValidate}">
	                                                <label for="pic1" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
														<div style="margin-top:8%;">Approved</div>
													</label>
	                                             </c:when>
	                                             <c:when test="${!fuser.fhasImgValidate && fuser.fpostImgValidate}">
	                                                <label for="pic1" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
														<div style="margin-top:8%;">Audit</div>
													</label>
	                                             </c:when>
	                                             <c:otherwise>
	                                                 <label for="pic1" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
														<div style="margin-top:8%;">Upload</div>
													 </label>
	                                             </c:otherwise>
	                                             </c:choose>
                                                <input id="pic1" type="file" onchange="uploadImg1()">
                                                <input id="pic1Url" type="hidden">
                                                <span class="s2"> <span>Unselected file</span>  <em class="pic1name"></em></span>
                                                <span class="f2" style="display: none">Sample</span>
                                            </div>
                                            <div class="imgs">
                                                <div class="pic1show">
                                                 <c:if test="${fuser.fpostImgValidate}">
                                                  <img src="${fuser.fIdentityPath }" alt="">
                                                  </c:if>
                                                </div>
                                                <div class="exemple" style="display: none"><img src="${oss_url}/static/front/images/user/zheng_image.png" alt=""></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="up_img_02 all_up">
                                        <p class="left">The back copy of ID card(Or you can choose to upload the back copy of your Driver's License if you are United States national or National ID document if you are a resident of European countries)</p>
                                        <br/>
                                        <div class="user_up">
                                            <div class="work_info">
                                                <c:choose>
                                             <c:when test="${fuser.fhasImgValidate && fuser.fpostImgValidate}">
                                                <label for="pic2" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
													<div style="margin-top:8%;">Approved</div>
												</label>
                                             </c:when>
                                             <c:when test="${!fuser.fhasImgValidate && fuser.fpostImgValidate}">
                                                <label for="pic2" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
													<div style="margin-top:8%;">Audit</div>
												</label>
                                             </c:when>
                                             <c:otherwise>
                                                 <label for="pic2" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
													<div style="margin-top:8%;">Upload</div>
												 </label>
                                             </c:otherwise>
                                             </c:choose>
                                                <input id="pic2" type="file" onchange="uploadImg2()">
                                                <input type="hidden" id="pic2Url">
								                <span class="s2"> <span>Unselected file</span>  <em class="pic2name"></em></span>
                                                <span class="f2" style="display: none">Sample</span>
                                            </div>
                                            <div class="imgs">
                                                <div class="pic2show">
                                                 <c:if test="${fuser.fpostImgValidate}">
                                                  <img src="${fuser.fIdentityPath2 }" alt="">
                                                  </c:if>
                                                </div>
                                                <div class="exemple" style="display: none"><img src="${oss_url}/static/front/images/user/fan_image.png" alt=""></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="up_img_03 all_up">
                                        <p class="left">Handheld photo of Identity Card/Overseas Resident Card(only required for Chinese oversea resident)</p>
                                        <br/>
                                        <div class="user_up">
                                            <div class="work_info">
                                             <c:choose>
                                             <c:when test="${fuser.fhasImgValidate && fuser.fpostImgValidate}">
                                                <label for="pic3" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
													<div style="margin-top:8%;">Approved</div>
												</label>
                                             </c:when>
                                             <c:when test="${!fuser.fhasImgValidate && fuser.fpostImgValidate}">
                                                <label for="pic3" class="s1"  style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
													<div style="margin-top:8%;">Audit</div>
												</label>
                                             </c:when>
                                             <c:otherwise>
                                                 <label for="pic3" class="s1" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png);background-size:100% 100%;">
													<div style="margin-top:8%;">Upload</div>
												</label>
                                             </c:otherwise>
                                             </c:choose>
                                                 <input id="pic3" type="file" onchange="uploadImg3()">
                                                <input type="hidden" id="pic3Url">
								                <span class="s2"> <span>Unselected file</span>  <em class="pic3name"></em></span>
                                                <span class="f2" style="display: none">Sample</span>
                                            </div>
                                            <div class="imgs">
                                                <div class="pic3show">
                                                  <c:if test="${fuser.fpostImgValidate}">
                                                  <img src="${fuser.fIdentityPath3 }" alt="">
                                                  </c:if>
                                                </div>
                                                <div class="exemple" style="display: none"><img src="${oss_url}/static/front/images/user/chi_image.png" alt=""></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="to_up" style="display: ${!fuser.fpostImgValidate==true?'block;':'none;'}">
                                    <p id="error" class="text-danger"></p>
                                        <%--<c:choose>--%>
                                        <%--<c:when test="${fuser.fpostRealValidate && fuser.fhasRealValidate && !fuser.fpostImgValidate}">--%>
                                             <span class="s1 btn-danger">Submit</span>
                                        <%--</c:when>--%>
                                        <%--<c:otherwise>--%>
                                         <%--<span class="s2 btn-danger">Submit</span>--%>
                                        <%--</c:otherwise>     --%>
                                        <%--</c:choose>--%>
                                </div>
                                <!-- kyc2验证第二步 -->
                                <div class="step_02" style="display: none;">
                                    <div class="sub_title">
                                        <span class="s1">Step 2</span>
                                        <span class="">Video authentication</span>
                                    </div>
                                    <div class="up_video">
                                        <div class="left">
                                            <div class="video_btn">
                                                <span class="s1">Please contact customer service for video authentication</span>
                                                <c:if test="${!fuser.fhasVideoValidate }">
                                                <label for="vedio" class="s2" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png)"><a href="http://wpa.qq.com/msgrd?v=3&uin=${requestScope.constant['serviceQQ'] }&menu=yes" style="color: #fff;">Contact</a></label>
                                                </c:if>
                                                <c:if test="${fuser.fhasVideoValidate }">
                                                <label for="vedio" class="s2" style="background-image:url(${oss_url}/static/front/images/user/blue-button.png)">Authenticated</label>
                                                </c:if>
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
    </div>
</div>
</div>

<!-- 实名认证 -->
	<div class="modal modal-custom fade" id="bindrealinfo" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">Identity Authentication</span>
                    <!-- <div class="KYC1-types text-center">
                        <span class="KYC1-type active">01身份认证</span>
                    </div> -->
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="bindrealinfo-realname" class="col-xs-3 control-label">Name</label>
						<div class="col-xs-6">
							<input id="bindrealinfo-realname" class="form-control" placeholder="Please enter your name" type="text" autocomplete="off">
							<span id="bindrealinfo-realname-errortips " class="text-danger certificationinfocolor">
                            *Please enter a valid identity    
                            </span>
						</div>
					</div>
                    <!--
                    <div class="form-group ">
                        <label for="bindrealinfo-areaCode" class="col-xs-3 control-label">Region/Country</label>
                        <div class="col-xs-6">
                            <select class="form-control" id="bindrealinfo-address">
                                <option value="86" selected>中国大陆(China)</option>
                            </select>
                        </div>
                    </div>
                    -->
					<div class="form-group ">
						<label for="bindrealinfo-areaCode" class="col-xs-3 control-label">ID Type</label>
						<div class="col-xs-6">
							<select class="form-control" id="bindrealinfo-identitytype">
								<option value="0">Passport</option>
                                <option value="1">National ID Card</option>
                                <option value="2">Drive License</option>
							</select>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-imgcode" class="col-xs-3 control-label">ID Number</label>
						<div class="col-xs-6">
							<input  id="bindrealinfo-identityno" class="form-control" type="text" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-ckinfo" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<div class="checkbox disabled">
								<label id="bindrealinfo-ckinfolb">
									<input type="checkbox" value="" checked id="bindrealinfo-ckinfo">
									I guarantee that the information submitted is true.
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="bindrealinfo-errortips" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<span id="certificationinfo-errortips" class="text-danger"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<button id="bindrealinfo-Btn" class="btn btn-danger btn-block">Submit</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


<jsp:include page="../comm/footer.jsp"></jsp:include>

    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/fileCheck.js"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/city.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/jquery.cityselect.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/kyc.js"></script>
</body>
</html>
