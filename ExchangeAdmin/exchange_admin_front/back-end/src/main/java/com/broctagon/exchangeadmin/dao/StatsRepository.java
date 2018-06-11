package com.broctagon.exchangeadmin.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.model.WithdrawDepositRecord;

public interface StatsRepository extends CrudRepository<WithdrawDepositRecord, Long>{

	@Query(nativeQuery = true, value = "SELECT SUM(Amount) FROM withdrawdeposit_ecord_histroy WHERE CoinID = :id And ApplyTime > :startTime AND ApplyTime < :endTime AND Type = :type")
	BigDecimal getAmountByCrypto(@Param("id") int id, @Param("startTime") long startTime, @Param("endTime") long endTime, @Param("type") long type);

}
