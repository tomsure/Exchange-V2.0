package com.broctagon.exchangeadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.message.listrequest.PengdingOrderListRequest;
import com.broctagon.exchangeadmin.service.PendingOrderService;
import com.broctagon.exchangeadmin.vo.PendingOrderVo;

@CrossOrigin
@RestController
@RequestMapping("/pendingOrder")
public class PendingOrderController {

	@Autowired
	private PendingOrderService pendingOrderService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<PendingOrderVo> list(@RequestBody PengdingOrderListRequest listRequest){
		return pendingOrderService.list(listRequest);
	}
	
//	@GetMapping("/cancel")
//	@ResponseBody
//	public BaseResponse cancel(@RequestParam("ids") List<Integer> ids){
//		return pendingOrderService.cancel(ids);
//	}
}
