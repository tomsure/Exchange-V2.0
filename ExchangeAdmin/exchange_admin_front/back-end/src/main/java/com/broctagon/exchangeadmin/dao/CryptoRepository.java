package com.broctagon.exchangeadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.model.BasicCoin;
import com.broctagon.exchangeadmin.model.Crypto;
import com.broctagon.exchangeadmin.vo.CryptoVo;

public interface CryptoRepository extends CrudRepository<Crypto, Long>, JpaSpecificationExecutor<CryptoVo>{

	@Modifying
	@Query("UPDATE Crypto SET status = :status WHERE id IN ( :ids )")
	void enable(@Param("ids") List<Integer> ids, @Param("status") int status);

	@Query("SELECT new com.broctagon.exchangeadmin.model.BasicCoin(id, coinName) FROM Crypto WHERE baseCoin = 1")
	List<BasicCoin> baseCoin();

	@Query("SELECT new com.broctagon.exchangeadmin.model.BasicCoin(id, coinName) FROM Crypto WHERE status = 1")
	List<BasicCoin> tradeCoin();
}
