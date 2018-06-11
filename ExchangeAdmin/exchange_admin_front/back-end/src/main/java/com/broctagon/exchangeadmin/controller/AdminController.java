package com.broctagon.exchangeadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.AdminListRequest;
import com.broctagon.exchangeadmin.message.user.ForgetPasswordRequest;
import com.broctagon.exchangeadmin.message.user.LoginRequest;
import com.broctagon.exchangeadmin.message.user.LoginResponse;
import com.broctagon.exchangeadmin.message.user.LogoutRequest;
import com.broctagon.exchangeadmin.message.user.ResetPasswordAfterLoginRequest;
import com.broctagon.exchangeadmin.message.user.ResetPasswordRequest;
import com.broctagon.exchangeadmin.model.Admin;
import com.broctagon.exchangeadmin.model.Role;
import com.broctagon.exchangeadmin.service.AdminService;
import com.broctagon.exchangeadmin.vo.AdminVo;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/login")
	@ResponseBody	
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Login Successfully"),
			@ApiResponse(code = 500, message = "Cannot find the email"),
			@ApiResponse(code = 501, message = "Password Error")
	})
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		return adminService.login(loginRequest);
	}
	
	@PostMapping("/forgetPwd")
	@ResponseBody	
	public BaseResponse forgetPwd(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
		return adminService.forgetPwd(forgetPasswordRequest);
	}
	
	@PostMapping("/resetPwd")
	@ResponseBody	
	public BaseResponse resetPwd(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		return adminService.resetPwd(resetPasswordRequest);
	}
	
	@PostMapping("/login/resetPwd")
	@ResponseBody
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Reset Password Successfully"),
			@ApiResponse(code = 502, message = "Old Password Error")
	})
	public BaseResponse resetPwdAfterLogin(@RequestBody ResetPasswordAfterLoginRequest resetPasswordRequest) {
		return adminService.resetPwdAfterLogin(resetPasswordRequest);
	}
	
	@PostMapping("/logout")
	@ResponseBody
	public BaseResponse logout(@RequestBody LogoutRequest logoutRequest) {
		return adminService.logout(logoutRequest);
	}	
	
	@PostMapping("/list")
	@ResponseBody
	public List<AdminVo> list(@RequestBody AdminListRequest listRequest) {
		return adminService.list(listRequest);
	}
	
	@PostMapping("/enable")
	@ResponseBody
	public BaseResponse enable(@RequestBody EnableRequest enableRequest) {
		return adminService.enable(enableRequest);
	}
	
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse save(@RequestBody Admin saveRequest) {
		return adminService.save(saveRequest);
	}
	
	@GetMapping("/delete")
	@ResponseBody
	public BaseResponse delete(@RequestParam("ids") List<Integer> ids) {
		return adminService.delete(ids);
	}
	
	@GetMapping("roles")
	@ResponseBody
	public List<Role> roles(){
		return adminService.roles();
	}
	
}
