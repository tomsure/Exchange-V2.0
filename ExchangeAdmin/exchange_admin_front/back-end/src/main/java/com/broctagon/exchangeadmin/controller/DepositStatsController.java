package com.broctagon.exchangeadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.constant.Tag;
import com.broctagon.exchangeadmin.message.listrequest.StatsListRequest;
import com.broctagon.exchangeadmin.service.DepositStatsService;
import com.broctagon.exchangeadmin.vo.StatsVo;

@CrossOrigin
@RestController
@RequestMapping("/depositStats")
public class DepositStatsController {
	
	@Autowired
	private DepositStatsService depositStatsService;
	
	@PostMapping("/list")
	public List<StatsVo> list(@RequestBody StatsListRequest listRequest){
		return depositStatsService.list(listRequest, Tag.DEPOSIT);
	}

}
