package com.kiiik.web.job;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kiiik.web.rsa.service.RsaService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月12日
 */
@Component
public class ScheduledService {
	Log log = LogFactory.getLog(ScheduledService.class);
	@Autowired
	RsaService rsaService;
	@Scheduled(cron = "0 0 23 * * *")
    public void scheduled(){
		try {
			rsaService.genRSASer();
		} catch (Exception e) {
			e.printStackTrace();
		}
        log.info("=====>>>>>使用cron "+System.currentTimeMillis());
    }
	
    /*@Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate "+System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay "+System.currentTimeMillis());
    }*/
}
