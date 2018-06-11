package com.broctagon.exchangeadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.model.Admin;
import com.broctagon.exchangeadmin.vo.AdminVo;

public interface AdminRepository extends CrudRepository<Admin, Long>, JpaSpecificationExecutor<AdminVo> {

	@Modifying
	@Query("UPDATE Admin SET status = :status WHERE id IN ( :ids )")
	void enable(@Param("ids") List<Integer> ids, @Param("status") int status);

	@SuppressWarnings("unchecked")
	Admin save(Admin saveRequest);

	@Modifying
	@Query("DELETE FROM Admin WHERE id IN ( :ids )")
	void delete(@Param("ids") List<Integer> ids);

	Admin findByUserName(@Param("userName") String email);

	@Query("SELECT id FROM Admin WHERE userName = :userName ")
	int findIdByUserName(@Param("userName") String email);
	
	@Modifying
	@Query("UPDATE Admin SET password = :password WHERE id = :id ")
	public void updateLoginPasswordById(@Param("id") int id, @Param("password") String password);

	@Query("SELECT password FROM Admin WHERE id = :id ")
	public String findLoginPasswordById(@Param("id") int id);

	
}
