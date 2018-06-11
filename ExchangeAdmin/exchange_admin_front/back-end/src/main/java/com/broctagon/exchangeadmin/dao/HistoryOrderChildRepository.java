package com.broctagon.exchangeadmin.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.broctagon.exchangeadmin.model.HistoryOrderChild;

public interface HistoryOrderChildRepository extends CrudRepository<HistoryOrderChild, Long>{

	List<HistoryOrderChild> findByOrderIdOrTradeOrderId(BigDecimal orderId, BigDecimal tradeOrderId);

}
