package com.broctagon.exchangeadmin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.broctagon.exchangeadmin.constant.PendingOrderKeys;
import com.broctagon.exchangeadmin.dao.UserRepository;
import com.broctagon.exchangeadmin.message.listrequest.PengdingOrderListRequest;
import com.broctagon.exchangeadmin.vo.PendingOrderVo;

@Service
@Transactional
public class PendingOrderService {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private UserRepository userRepository;

	public List<PendingOrderVo> list(PengdingOrderListRequest listRequest) {
		List<PendingOrderVo> pendingOrderVos = new ArrayList<>();
		Set<String> keys = redisTemplate.keys("*");
		for(String key : keys) {
			Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
			PendingOrderVo pendingOrderVo = new PendingOrderVo();
			pendingOrderVo.setOrderId(key);
			pendingOrderVo.setUserName(userRepository.findUserNameById(values.get(PendingOrderKeys.USER_ID) == null ? 0 : Integer.parseInt(String.valueOf(values.get(PendingOrderKeys.USER_ID)))));
			pendingOrderVo.setCryptoPair(String.valueOf(values.get(PendingOrderKeys.CRYPTO_PAIR)));
			pendingOrderVo.setType(Integer.parseInt(String.valueOf(values.get(PendingOrderKeys.TYPE) == null ? 0 : values.get(PendingOrderKeys.TYPE))));
			pendingOrderVo.setPrice(new BigDecimal(String.valueOf(values.get(PendingOrderKeys.PRICE) == null ? BigDecimal.ZERO : values.get(PendingOrderKeys.PRICE))));
			pendingOrderVo.setQty(new BigDecimal(String.valueOf(values.get(PendingOrderKeys.QTY) == null ? BigDecimal.ZERO : values.get(PendingOrderKeys.QTY))));
			pendingOrderVo.setAmount(pendingOrderVo.getPrice().multiply(pendingOrderVo.getQty()));
			pendingOrderVo.setTime(String.valueOf(values.get(PendingOrderKeys.TIME)));
			pendingOrderVos.add(pendingOrderVo);
		}
		if(listRequest.getType() != 0) {
			Iterator<PendingOrderVo> pendingOrderVoIterator = pendingOrderVos.iterator();
			while(pendingOrderVoIterator.hasNext()) {
				PendingOrderVo pendingOrderVo = pendingOrderVoIterator.next();
				if (pendingOrderVo.getType() != listRequest.getType()) {
					pendingOrderVoIterator.remove();
				}
			}
		}
		if(listRequest.getOrderId() != null && listRequest.getOrderId() != BigDecimal.ZERO) {
			Iterator<PendingOrderVo> pendingOrderVoIterator = pendingOrderVos.iterator();
			while(pendingOrderVoIterator.hasNext()) {
				PendingOrderVo pendingOrderVo = pendingOrderVoIterator.next();
				if (pendingOrderVo.getOrderId().compareTo(listRequest.getOrderId().toString()) != 0) {
					pendingOrderVoIterator.remove();
				}
			}
		}
		if(!StringUtils.isEmpty(listRequest.getUserName())) {
			Iterator<PendingOrderVo> pendingOrderVoIterator = pendingOrderVos.iterator();
			while(pendingOrderVoIterator.hasNext()) {
				PendingOrderVo pendingOrderVo = pendingOrderVoIterator.next();
				if (!pendingOrderVo.getUserName().equals(listRequest.getUserName())) {
					pendingOrderVoIterator.remove();
				}
			}
		}
		if(!StringUtils.isEmpty(listRequest.getCryptoPair())) {
			Iterator<PendingOrderVo> pendingOrderVoIterator = pendingOrderVos.iterator();
			while(pendingOrderVoIterator.hasNext()) {
				PendingOrderVo pendingOrderVo = pendingOrderVoIterator.next();
				if (!pendingOrderVo.getCryptoPair().equals(listRequest.getCryptoPair())) {
					pendingOrderVoIterator.remove();
				}
			}
		}
		return pendingOrderVos;
	}

//	public BaseResponse cancel(List<Integer> ids) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
