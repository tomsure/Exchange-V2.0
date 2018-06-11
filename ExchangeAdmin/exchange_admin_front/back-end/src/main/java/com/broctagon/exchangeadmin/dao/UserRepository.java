package com.broctagon.exchangeadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.broctagon.exchangeadmin.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	public String findUserNameById(@Param("id") int id);

	@Modifying
	@Query("UPDATE User SET status = :status WHERE id IN ( :ids )")
	public void updateStatusByIds(@Param("ids") List<Integer> ids, @Param("status") int status);
	
}
