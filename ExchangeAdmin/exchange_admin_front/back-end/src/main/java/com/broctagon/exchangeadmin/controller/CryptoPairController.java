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
import com.broctagon.exchangeadmin.message.listrequest.CryptoPairListRequest;
import com.broctagon.exchangeadmin.model.BasicCoin;
import com.broctagon.exchangeadmin.model.CryptoPair;
import com.broctagon.exchangeadmin.service.CryptoPairService;
import com.broctagon.exchangeadmin.vo.CryptoPairVo;

@CrossOrigin
@RestController
@RequestMapping("/cryptoPair")
public class CryptoPairController {

	@Autowired
	private CryptoPairService cryptoPairService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<CryptoPairVo> list(@RequestBody CryptoPairListRequest listRequest){
		return cryptoPairService.list(listRequest);
	}
	
	@PostMapping("/enable")
	@ResponseBody
	public BaseResponse enable(@RequestBody EnableRequest enableRequest) {
		return cryptoPairService.enable(enableRequest);
	}
	
	@GetMapping("/baseCoin")
	@ResponseBody
	public List<BasicCoin> baseCoin(){
		return cryptoPairService.baseCoin();
	}
	
	@GetMapping("/tradeCoin")
	@ResponseBody
	public List<BasicCoin> tradeCoin(){
		return cryptoPairService.tradeCoin();
	}
	
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse save(@RequestBody CryptoPair cryptoPair) {
		return cryptoPairService.save(cryptoPair);
	}
	
}
