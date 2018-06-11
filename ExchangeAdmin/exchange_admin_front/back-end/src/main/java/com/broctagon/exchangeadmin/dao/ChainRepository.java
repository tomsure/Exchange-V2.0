package com.broctagon.exchangeadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.model.BasicChain;
import com.broctagon.exchangeadmin.model.Chain;

public interface ChainRepository extends CrudRepository<Chain, Long>, JpaSpecificationExecutor<Chain>{

	@Modifying
	@Query("UPDATE Chain SET status = :status WHERE id IN ( :ids )")
	void enable(@Param("ids") List<Integer> ids, @Param("status") int status);

	@Query("SELECT new com.broctagon.exchangeadmin.model.BasicChain(chainId, chainName) FROM BasicChain")
	List<BasicChain> findIdAndChainName();	

}
