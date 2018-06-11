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
import com.broctagon.exchangeadmin.dao.ChainRepository;
import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.ChainListRequest;
import com.broctagon.exchangeadmin.model.BasicChain;
import com.broctagon.exchangeadmin.model.Chain;

@Service
@Transactional
public class ChainService {
	
	@Autowired
	private ChainRepository chainRepository;

	@SuppressWarnings("serial")
	public List<Chain> list(ChainListRequest listRequest) {
		List<Chain> chains = chainRepository.findAll(new Specification<Chain>() {
			
			@Override
			public Predicate toPredicate(Root<Chain> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(listRequest.getStatus() != -1) {
					predicates.add(criteriaBuilder.equal(root.get("status"), listRequest.getStatus()));
				}
				if(!StringUtils.isEmpty(listRequest.getChainName())) {
					predicates.add(criteriaBuilder.equal(root.get("chainName"), listRequest.getChainName()));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return chains;
	}

	public BaseResponse enable(EnableRequest enableRequest) {
		BaseResponse enableResponse = new BaseResponse();
		chainRepository.enable(enableRequest.getIds(), enableRequest.getStatus());
		enableResponse.setStatus(StatusCode.SUCCESS);
		return enableResponse;
	}

	public BaseResponse save(Chain chain) {
		BaseResponse saveResponse = new BaseResponse();
		chainRepository.save(chain);
		saveResponse.setStatus(StatusCode.SUCCESS);
		return saveResponse;
	}

	public List<BasicChain> list() {
		List<BasicChain> basicChains = chainRepository.findIdAndChainName();
		return basicChains == null ? new ArrayList<BasicChain>() : basicChains;
	}
}
