package com.broctagon.exchangeadmin.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.broctagon.exchangeadmin.vo.CryptoPairStatsDetailsVo;
import com.broctagon.exchangeadmin.vo.CryptoPairStatsVo;


public interface CryptoPairStatsRepositorty extends CrudRepository<CryptoPairStatsDetailsVo, Long>, JpaSpecificationExecutor<CryptoPairStatsVo>{
	
	@Query(nativeQuery = true, value = "SELECT h.id, u.userName, h.type, h.price, h.Amount, h.TradeAmount, h.LastTraderTime FROM token_order h INNER JOIN user u ON u.id = h.userId WHERE h.Symbol = :symbol ORDER BY h.LastTraderTime DESC LIMIT 1")
	CryptoPairStatsDetailsVo findCurrentPriceByCryptoPair(@Param("symbol") String cryptoPair);
	
	@Query(nativeQuery = true, value = "SELECT h.id, u.userName, h.type, h.price, h.Amount, h.TradeAmount, h.LastTraderTime FROM token_order h INNER JOIN user u ON u.id = h.userId WHERE h.Symbol = :symbol ORDER BY h.Price DESC LIMIT 1 ")
	CryptoPairStatsDetailsVo findHighPriceByCryptoPair(@Param("symbol") String cryptoPair);

	@Query(nativeQuery = true, value = "SELECT h.id, u.userName, h.type, h.price, h.Amount, h.TradeAmount, h.LastTraderTime FROM token_order h INNER JOIN user u ON u.id = h.userId WHERE h.Symbol = :symbol ORDER BY h.Price ASC LIMIT 1 ")
	CryptoPairStatsDetailsVo findLowPriceByCryptoPair(@Param("symbol") String cryptoPair);

	@Query(nativeQuery = true, value = "SELECT h.id, u.userName, h.type, h.price, h.Amount, h.TradeAmount, h.LastTraderTime FROM token_order h INNER JOIN user u ON u.id = h.userId WHERE h.Symbol = :symbol ORDER BY h.LastTraderTime ASC LIMIT 1")
	CryptoPairStatsDetailsVo findOpenPriceByCryptoPair(@Param("symbol") String cryptoPair);

}
