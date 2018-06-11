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

import com.broctagon.exchangeadmin.constant.StatusCode;
import com.broctagon.exchangeadmin.dao.CryptoPairRepository;
import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.CryptoPairListRequest;
import com.broctagon.exchangeadmin.model.BasicCoin;
import com.broctagon.exchangeadmin.model.CryptoPair;
import com.broctagon.exchangeadmin.vo.CryptoPairVo;

@Service
@Transactional
public class CryptoPairService {

	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private CryptoPairRepository cryptoPairRepository;
	
	@SuppressWarnings("serial")
	public List<CryptoPairVo> list(CryptoPairListRequest listRequest) {
		List<CryptoPairVo> cryptoPairVos = cryptoPairRepository.findAll(new Specification<CryptoPairVo>() {

			@Override
			public Predicate toPredicate(Root<CryptoPairVo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(listRequest.getBaseCoinId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("baseCoinId"), listRequest.getBaseCoinId()));
				}
				if(listRequest.getTradeCoinId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("tradeCoinId"), listRequest.getTradeCoinId()));
				}
				if(listRequest.getStatus() != -1) {
					predicates.add(criteriaBuilder.equal(root.get("status"), listRequest.getStatus()));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return cryptoPairVos == null ? new ArrayList<>() : cryptoPairVos;
	}

	public BaseResponse enable(EnableRequest enableRequest) {
		BaseResponse enableResponse = new BaseResponse();
		cryptoPairRepository.enable(enableRequest.getIds(), enableRequest.getStatus());
		enableResponse.setStatus(StatusCode.SUCCESS);
		return enableResponse;
	}

	public List<BasicCoin> baseCoin() {
		List<BasicCoin> baseCoins = cryptoService.baseCoin();
		return baseCoins == null ? new ArrayList<>() : baseCoins;
	}

	public List<BasicCoin> tradeCoin() {
		List<BasicCoin> tradeCoins = cryptoService.tradeCoin();
		return tradeCoins == null ? new ArrayList<>() : tradeCoins;
	}

	public BaseResponse save(CryptoPair cryptoPair) {
		BaseResponse saveResponse = new BaseResponse();
		cryptoPairRepository.save(cryptoPair);
		saveResponse.setStatus(StatusCode.SUCCESS);
		return saveResponse;
	}

}
