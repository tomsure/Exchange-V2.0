package com.broctagon.exchangeadmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.broctagon.exchangeadmin.constant.StatusCode;
import com.broctagon.exchangeadmin.dao.UserKycRepository;
import com.broctagon.exchangeadmin.dao.UserRepository;
import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.UserListRequest;
import com.broctagon.exchangeadmin.model.UserKyc;
import com.broctagon.exchangeadmin.vo.UserVo;

@Service	
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserKycRepository userKycRepository;

	public List<UserVo> list(UserListRequest listRequest) {
		List<UserVo> userVos = new ArrayList<>();
		List<UserKyc> userKycs = getUsersByParam(listRequest);
		for(UserKyc userKyc : userKycs) {
			UserVo userVo = new UserVo(userKyc.getId(), userKyc.getUserName(), userKyc.getMobile(), userKyc.getIsKyc(), userKyc.getIsKycLocked(), userKyc.getLastLoginTime(), userKyc.getStatus());
			if(userKyc.getKyc() != null) {
				userVo.setIdType(userKyc.getKyc().getIdType());
				userVo.setIdNo(userKyc.getKyc().getIdNo());
			}
			userVos.add(userVo);
		}
		return userVos;
	}

	@SuppressWarnings("serial")
	private List<UserKyc> getUsersByParam(UserListRequest listRequest) {
		List<UserKyc> userKycs = userKycRepository.findAll(new Specification<UserKyc>() {
			
			@Override
			public Predicate toPredicate(Root<UserKyc> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(!StringUtils.isEmpty(listRequest.getUsername())) {
					predicates.add(criteriaBuilder.equal(root.get("userName"), listRequest.getUsername()));
				}
				if(!StringUtils.isEmpty(listRequest.getMobile())) {
					predicates.add(criteriaBuilder.equal(root.get("mobile"), listRequest.getMobile()));
				}
				if(!StringUtils.isEmpty(listRequest.getIdNo())) {
					predicates.add(criteriaBuilder.equal(root.get("kyc").get("idNo"), listRequest.getIdNo()));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return userKycs;
	}

	public BaseResponse enable(EnableRequest enableRequest) {
		BaseResponse enableResponse = new BaseResponse();
		userRepository.updateStatusByIds(enableRequest.getIds(), enableRequest.getStatus());
		enableResponse.setStatus(StatusCode.SUCCESS);
		return enableResponse;
	}

}
