package com.broctagon.exchangeadmin.controller;

import java.math.BigDecimal;
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

import com.broctagon.exchangeadmin.message.listrequest.HistoryOrderListRequest;
import com.broctagon.exchangeadmin.service.HistoryOrderService;
import com.broctagon.exchangeadmin.vo.HistoryOrderDetailsVo;
import com.broctagon.exchangeadmin.vo.HistoryOrderVo;

@CrossOrigin
@RestController
@RequestMapping("/historyOrder")
public class HistoryOrderController {

	@Autowired
	private HistoryOrderService historyOrderService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<HistoryOrderVo> list(@RequestBody HistoryOrderListRequest listRequest){
		return historyOrderService.list(listRequest);
	}
	
	@GetMapping("/details")
	@ResponseBody
	public List<HistoryOrderDetailsVo> details(@RequestParam("id") BigDecimal id){
		return historyOrderService.details(id);
	}
}
