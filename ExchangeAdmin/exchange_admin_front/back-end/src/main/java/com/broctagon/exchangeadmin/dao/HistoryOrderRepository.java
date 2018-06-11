package com.broctagon.exchangeadmin.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.model.UserHistoryOrder;

public interface HistoryOrderRepository extends CrudRepository<UserHistoryOrder, Long>, JpaSpecificationExecutor<UserHistoryOrder>{
	
	@Query("SELECT u.userName FROM User u INNER JOIN HistoryOrder h ON u.id = h.userId WHERE h.orderId = :orderId")
	String findUserNameByPeerOrderId(@Param("orderId") BigDecimal orderId);

}
