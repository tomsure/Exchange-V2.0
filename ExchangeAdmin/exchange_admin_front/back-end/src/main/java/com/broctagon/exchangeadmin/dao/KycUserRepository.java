package com.broctagon.exchangeadmin.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.broctagon.exchangeadmin.model.KycUser;

public interface KycUserRepository extends CrudRepository<KycUser, Long>, JpaSpecificationExecutor<KycUser>{

}
