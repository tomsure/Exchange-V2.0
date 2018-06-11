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
import com.broctagon.exchangeadmin.dao.CryptoRepository;
import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.CryptoListRequest;
import com.broctagon.exchangeadmin.model.BasicCoin;
import com.broctagon.exchangeadmin.model.Crypto;
import com.broctagon.exchangeadmin.vo.CryptoVo;

@Service
@Transactional
public class CryptoService {
	
	@Autowired
	private CryptoRepository cryptoRepository;

	@SuppressWarnings("serial")
	public List<CryptoVo> list(CryptoListRequest listRequest) {
		List<CryptoVo> cryptoVos = cryptoRepository.findAll(new Specification<CryptoVo>() {
			
			@Override
			public Predicate toPredicate(Root<CryptoVo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(listRequest.getChainId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("id"), listRequest.getChainId()));
				}
				if(!StringUtils.isEmpty(listRequest.getCoinName())) {
					predicates.add(criteriaBuilder.like(root.get("coinName"), listRequest.getCoinName() + "%"));
				}
				if(listRequest.getStatus() != -1) {
					predicates.add(criteriaBuilder.equal(root.get("status"), listRequest.getStatus()));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return cryptoVos;
	}

	public BaseResponse enable(EnableRequest enableRequest) {
		BaseResponse enableResponse = new BaseResponse();
		cryptoRepository.enable(enableRequest.getIds(), enableRequest.getStatus());
		enableResponse.setStatus(StatusCode.SUCCESS);
		return enableResponse;
	}

	public BaseResponse save(Crypto crypto) {
		BaseResponse saveResponse = new BaseResponse();
		cryptoRepository.save(crypto);
		saveResponse.setStatus(StatusCode.SUCCESS);
		return saveResponse;
	}

	public List<BasicCoin> baseCoin() {
		List<BasicCoin> basicCoins = cryptoRepository.baseCoin();
		return basicCoins == null ? new ArrayList<>() : basicCoins;
	}

	public List<BasicCoin> tradeCoin() {
		List<BasicCoin> tradeCoins = cryptoRepository.tradeCoin();
		return tradeCoins == null ? new ArrayList<>() : tradeCoins;
	}

}
