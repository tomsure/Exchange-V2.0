package com.broctagon.exchangeadmin.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.broctagon.exchangeadmin.model.UserKyc;

public interface UserKycRepository extends CrudRepository<UserKyc, Long>, JpaSpecificationExecutor<UserKyc>{

}
