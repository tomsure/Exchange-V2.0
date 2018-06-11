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
import com.broctagon.exchangeadmin.message.listrequest.CryptoListRequest;
import com.broctagon.exchangeadmin.model.Crypto;
import com.broctagon.exchangeadmin.service.CryptoService;
import com.broctagon.exchangeadmin.vo.CryptoVo;

@CrossOrigin	
@RestController
@RequestMapping("/crypto")
public class CryptoController {

	@Autowired
	private CryptoService cryptoService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<CryptoVo> list(@RequestBody CryptoListRequest listRequest){
		return cryptoService.list(listRequest);
	}
	
	@PostMapping("/enable")
	@ResponseBody
	public BaseResponse enable(@RequestBody EnableRequest enableRequest) {
		return cryptoService.enable(enableRequest);
	}
	
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse save(@RequestBody Crypto crypto) {
		return cryptoService.save(crypto);
	}
}
