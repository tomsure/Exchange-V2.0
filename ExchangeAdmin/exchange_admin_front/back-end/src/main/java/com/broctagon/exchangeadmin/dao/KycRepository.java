package com.broctagon.exchangeadmin.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.broctagon.exchangeadmin.model.Kyc;

@Repository
public interface KycRepository extends CrudRepository<Kyc, Long>{

	public Kyc findByUserId(@Param("id") int userId);

	@Modifying
	@Query("UPDATE User SET isKyc = :status WHERE userId = :userId")
	public void auth(@Param("userId") int userId, @Param("status") int status);

}
