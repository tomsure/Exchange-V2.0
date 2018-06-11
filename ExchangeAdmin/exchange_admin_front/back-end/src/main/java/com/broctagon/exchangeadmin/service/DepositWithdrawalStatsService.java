package com.broctagon.exchangeadmin.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.broctagon.exchangeadmin.dao.StatsRepository;
import com.broctagon.exchangeadmin.message.listrequest.StatsListRequest;
import com.broctagon.exchangeadmin.model.BasicCoin;
import com.broctagon.exchangeadmin.util.DateUtil;
import com.broctagon.exchangeadmin.vo.StatsVo;

@Service
public class DepositWithdrawalStatsService {

	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private StatsRepository statsRepository;
	
	public List<StatsVo> list(StatsListRequest listRequest, long type) {
		List<StatsVo> statsVos = new ArrayList<>();
		List<BasicCoin> baseCoins = cryptoService.baseCoin();
		Instant now = getLastDay(listRequest.getEndTime());
		long days = getDaysFromParam(listRequest.getStartTime(), listRequest.getEndTime());
		for(long i = days;  i > 0; i--) {
			StatsVo statsVo = new StatsVo();
			Map<String, BigDecimal> map = new ConcurrentHashMap<>();
			for(BasicCoin baseCoin : baseCoins) {
				if(!baseCoin.getName().equals(listRequest.getCrypto())) {
					LocalDateTime dateTime = now.minus(Duration.ofDays(i)).atZone(ZoneId.of("UTC")).toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0);
					BigDecimal amount = statsRepository.getAmountByCrypto(baseCoin.getId(), DateUtil.getUTC(dateTime), DateUtil.getUTC(dateTime.plusDays(1)), type);
					if(amount != null) {
						map.put(baseCoin.getName(), amount);
					}
				}
			}
			statsVo.setTime(DateUtil.date(now.minus(Duration.ofDays(i))));
			statsVo.setStats(map);
			statsVos.add(statsVo);
		}
		return statsVos;
	}

	private Instant getLastDay(String endTime) {
		if(StringUtils.isEmpty(endTime)) {
			return Instant.now();
		}else {
			return DateUtil.toLocalDateTime(endTime).toInstant(ZoneOffset.UTC);
		}
	}

	private long getDaysFromParam(String startTime, String endTime) {
		long days = 7;
		if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			LocalDate startTimeLocalDate = DateUtil.toLocalDate(startTime);
			LocalDate endTimeLocalDate = DateUtil.toLocalDate(endTime);
			days = endTimeLocalDate.toEpochDay() - startTimeLocalDate.toEpochDay();
		}
		return days;
	}
	

}
