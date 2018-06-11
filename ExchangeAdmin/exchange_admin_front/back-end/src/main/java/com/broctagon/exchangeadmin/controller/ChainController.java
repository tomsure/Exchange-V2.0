package com.broctagon.exchangeadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.ChainListRequest;
import com.broctagon.exchangeadmin.model.BasicChain;
import com.broctagon.exchangeadmin.model.Chain;
import com.broctagon.exchangeadmin.service.ChainService;

@CrossOrigin
@RestController
@RequestMapping("/chain")
public class ChainController {

	@Autowired
	private ChainService chainService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<Chain> list(@RequestBody ChainListRequest listRequest) {
		return chainService.list(listRequest);
	}
	
	@PostMapping("/enable")
	@ResponseBody
	public BaseResponse enable(@RequestBody EnableRequest enableRequest) {
		return chainService.enable(enableRequest);
	}
	
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse save(@RequestBody Chain chain) {
		return chainService.save(chain);
	}
	
	@GetMapping("/list")
	@ResponseBody
	public List<BasicChain> list(){
		return chainService.list();
	}
}
