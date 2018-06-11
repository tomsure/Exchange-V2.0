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

import com.broctagon.exchangeadmin.dao.CryptoPairStatsRepositorty;
import com.broctagon.exchangeadmin.message.listrequest.CryptoPairStatsListRequest;
import com.broctagon.exchangeadmin.vo.CryptoPairStats;
import com.broctagon.exchangeadmin.vo.CryptoPairStatsVo;

@Service
@Transactional
public class CryptoPairStatsService {

	@Autowired
	private CryptoPairStatsRepositorty cryptoPairStatsRepositorty;
	
	@SuppressWarnings("serial")
	public List<CryptoPairStatsVo> list(CryptoPairStatsListRequest listRequest) {
		List<CryptoPairStatsVo> cryptoPairStatsVos = cryptoPairStatsRepositorty.findAll(new Specification<CryptoPairStatsVo>() {
			
			@Override
			public Predicate toPredicate(Root<CryptoPairStatsVo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (listRequest.getCryptoPairId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("cryptoPairId"), listRequest.getCryptoPairId()));
					
				}
				return null;
			}
		});
		return cryptoPairStatsVos;
	}

	public CryptoPairStats details(String cryptoPair) {
		CryptoPairStats cryptoPairStats = new CryptoPairStats();
		cryptoPairStats.setCurrent(cryptoPairStatsRepositorty.findCurrentPriceByCryptoPair(cryptoPair));
		cryptoPairStats.setHigh(cryptoPairStatsRepositorty.findHighPriceByCryptoPair(cryptoPair));
		cryptoPairStats.setLow(cryptoPairStatsRepositorty.findLowPriceByCryptoPair(cryptoPair));
		cryptoPairStats.setOpen(cryptoPairStatsRepositorty.findOpenPriceByCryptoPair(cryptoPair));
		return cryptoPairStats;
	}

}
