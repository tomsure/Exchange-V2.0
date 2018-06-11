package com.broctagon.exchangeadmin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.broctagon.exchangeadmin.dao.HistoryOrderChildRepository;
import com.broctagon.exchangeadmin.dao.HistoryOrderRepository;
import com.broctagon.exchangeadmin.message.listrequest.HistoryOrderListRequest;
import com.broctagon.exchangeadmin.model.HistoryOrderChild;
import com.broctagon.exchangeadmin.model.UserHistoryOrder;
import com.broctagon.exchangeadmin.vo.HistoryOrderDetailsVo;
import com.broctagon.exchangeadmin.vo.HistoryOrderVo;

@Service
@Transactional
public class HistoryOrderService {
	
	@Autowired
	private HistoryOrderRepository historyOrderRepository;
	
	@Autowired
	private HistoryOrderChildRepository historyOrderChildRepository;

	public List<HistoryOrderVo> list(HistoryOrderListRequest listRequest) {
		List<HistoryOrderVo> historyOrderVos = new ArrayList<>();
		List<UserHistoryOrder> historyOrders = getHistoryOrderByParam(listRequest);
		for(UserHistoryOrder userHistoryOrder : historyOrders) {
			HistoryOrderVo historyOrderVo = new HistoryOrderVo(userHistoryOrder.getOrderId(), userHistoryOrder.getUser().getUserName(), userHistoryOrder.getCryptoPair(), userHistoryOrder.getType(), userHistoryOrder.getStatus(), userHistoryOrder.getPrice(), userHistoryOrder.getQty(), userHistoryOrder.getAmount(), userHistoryOrder.getTime());
			historyOrderVos.add(historyOrderVo);
		}
		return historyOrderVos;
	}
	
	@SuppressWarnings("serial")
	private List<UserHistoryOrder> getHistoryOrderByParam(HistoryOrderListRequest listRequest) {
		List<UserHistoryOrder> historyOrders = historyOrderRepository.findAll(new Specification<UserHistoryOrder>() {
			@Override
			public Predicate toPredicate(Root<UserHistoryOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(listRequest.getStatus() != -1) {
					predicates.add(criteriaBuilder.equal(root.get("status"), listRequest.getStatus()));
				}
				if(listRequest.getType() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("type"), listRequest.getType()));
				}
				if(listRequest.getOrderId() !=null && listRequest.getOrderId() != BigDecimal.ZERO) {
					predicates.add(criteriaBuilder.equal(root.get("orderId"), listRequest.getOrderId()));
				}
				if(!StringUtils.isEmpty(listRequest.getCrypto())) {
					predicates.add(criteriaBuilder.equal(root.get("cryptoPair"), listRequest.getCrypto()));
				}
				if(!StringUtils.isEmpty(listRequest.getUserName())) {
					predicates.add(criteriaBuilder.equal(root.get("user").get("userName"), listRequest.getUserName()));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return historyOrders;
	}

	public List<HistoryOrderDetailsVo> details(BigDecimal id) {
		List<HistoryOrderDetailsVo> historyOrderDetailsVos = new ArrayList<>();
		List<HistoryOrderChild> historyOrderChildren =  historyOrderChildRepository.findByOrderIdOrTradeOrderId(id, id);
		for(HistoryOrderChild historyOrderChild : historyOrderChildren) {
			HistoryOrderDetailsVo historyOrderDetailsVo = new HistoryOrderDetailsVo(historyOrderChild);
			String userName = historyOrderRepository.findUserNameByPeerOrderId(historyOrderChild.getOrderId());
			historyOrderDetailsVo.setUserName(userName);
			historyOrderDetailsVos.add(historyOrderDetailsVo);
		}
		return historyOrderDetailsVos;
	}

}
