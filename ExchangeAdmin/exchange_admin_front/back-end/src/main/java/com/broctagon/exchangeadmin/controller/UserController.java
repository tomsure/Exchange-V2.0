package com.broctagon.exchangeadmin.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.UserListRequest;
import com.broctagon.exchangeadmin.service.UserService;
import com.broctagon.exchangeadmin.vo.UserVo;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;	
	
	@PostMapping("/list")
	@ResponseBody
	public List<UserVo> list(@RequestBody UserListRequest listRequest){
		return userService.list(listRequest);
	}
	
	@PostMapping("/enable")
	@ResponseBody
	public BaseResponse enable(@RequestBody EnableRequest enableRequest) {
		return userService.enable(enableRequest);
	}

}
