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
import com.broctagon.exchangeadmin.dao.KycRepository;
import com.broctagon.exchangeadmin.dao.KycUserRepository;
import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.listrequest.KycListRequest;
import com.broctagon.exchangeadmin.model.Kyc;
import com.broctagon.exchangeadmin.model.KycUser;
import com.broctagon.exchangeadmin.vo.KycVo;

@Service
@Transactional
public class KycService {
	
	@Autowired
	private KycRepository kycRepository;
	
	@Autowired
	private KycUserRepository userKycRepository;

	public List<KycVo> list(KycListRequest listRequest) {
		List<KycVo> kycVos = new ArrayList<>();
		List<KycUser> userKycs = getKycsByParam(listRequest);
		for(KycUser userKyc : userKycs) {
			KycVo kycVo = new KycVo(userKyc.getId(), userKyc.getUser().getUserName(), userKyc.getIdType(), userKyc.getIdNo());
			kycVos.add(kycVo);
		}
		return kycVos;
	}

	@SuppressWarnings("serial")
	private List<KycUser> getKycsByParam(KycListRequest listRequest) {
		List<KycUser> userKycs = userKycRepository.findAll(new Specification<KycUser>() {
			
			@Override
			public Predicate toPredicate(Root<KycUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(!StringUtils.isEmpty(listRequest.getIdNo())) {
					predicates.add(criteriaBuilder.equal(root.get("idno"), listRequest.getIdNo()));
				}
				if(!StringUtils.isEmpty(listRequest.getUserName())) {
					predicates.add(criteriaBuilder.equal(root.get("user").get("userName"), listRequest.getIdNo()));
				}
				if(listRequest.getUserId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("user").get("id"), listRequest.getUserId()));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return userKycs;
	}

	public Kyc details(int userId) {
		Kyc kyc = kycRepository.findByUserId(userId);
		return kyc == null ? new Kyc() : kyc;
	}

	public BaseResponse auth(int userId, int status) {
		BaseResponse authResponse = new BaseResponse();
		kycRepository.auth(userId, status);
		authResponse.setStatus(StatusCode.SUCCESS);
		return authResponse;
	}


}
