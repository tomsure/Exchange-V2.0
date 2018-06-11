package com.broctagon.exchangeadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.model.CryptoPair;
import com.broctagon.exchangeadmin.vo.CryptoPairVo;

public interface CryptoPairRepository extends CrudRepository<CryptoPair, Long>, JpaSpecificationExecutor<CryptoPairVo>{

	@Modifying
	@Query("UPDATE CryptoPair SET status = :status WHERE id IN ( :ids )")
	void enable(@Param("ids") List<Integer> ids, @Param("status") int status);

}
