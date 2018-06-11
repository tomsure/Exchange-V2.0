package com.broctagon.exchangeadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.message.listrequest.CryptoPairStatsListRequest;
import com.broctagon.exchangeadmin.service.CryptoPairStatsService;
import com.broctagon.exchangeadmin.vo.CryptoPairStats;
import com.broctagon.exchangeadmin.vo.CryptoPairStatsVo;

@CrossOrigin
@RestController
@RequestMapping("/cryptoPairStats")
public class CryptoPairStatsController {

	@Autowired
	private CryptoPairStatsService cryptoPairStatsService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<CryptoPairStatsVo> list(CryptoPairStatsListRequest listRequest){
		return cryptoPairStatsService.list(listRequest);
	}
	
	@GetMapping("/details")
	@ResponseBody
	public CryptoPairStats details(@RequestParam("cryptoPair") String cryptoPair) {
		return cryptoPairStatsService.details(cryptoPair);
	}
	
}
