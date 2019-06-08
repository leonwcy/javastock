/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stock.job.grabdata;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import stock.biz.StockInfo;
import stock.biz.redis.StringRedisBiz;
import stock.biz.sina.GrabDataBiz;
import stock.biz.sina.GrabDataBizTask;
import stock.biz.utils.DateUtil;
import stock.biz.utils.StringUtil;
import stock.dal.sql.StockRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/****
 * 更新redis 里的数据， 每分钟 抓取 3000个股票的 金额 和 成交量 。
 */
public class FetchStockShortDataJobTest extends QuartzJobBean {
	private String name;
    @Autowired
    GrabDataBiz grabDataBiz;
	@Autowired
    StringRedisBiz stringRedisBiz;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    StockRepository stockRepository;

    private final int STOCK_ARRAY_CAP = 50;



    private final Logger logger = LoggerFactory.getLogger(FetchStockShortDataJobTest.class);

	// Invoked if a Job data map entry with that name
	public void setName(String name) {
		this.name = name;
	}

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        try {
            startJob();
        } catch (Exception e) {
            logger.error("error to execute FetchStockShortDataJob :" ,e);
        }
    }

    private void startJob() throws InterruptedException {
        //String [] stocks = StockInfo.getTotalStocks().split(",");
        String [] stocks = stockRepository.getStockCodes().split(",");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(64,128,1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

        long  start = new Date().getTime();
        List<String> arrs = new ArrayList<String>(STOCK_ARRAY_CAP);

        int batchCount = stocks.length / STOCK_ARRAY_CAP;
        int lastBatch =  stocks.length % STOCK_ARRAY_CAP;
        int b = 0;
        for ( String s :stocks ) {
            if(StringUtil.isNullorEmpty(s)) continue;

            arrs.add(s);
            if(arrs.size() == STOCK_ARRAY_CAP || (b==batchCount && lastBatch ==arrs.size()) )
            {
                b++;
                List<String> arrb = new ArrayList<String>(STOCK_ARRAY_CAP);
                arrb.addAll(arrs) ;
                GrabDataBizTask task = new GrabDataBizTask();
                task.setStockCodes(arrb);

                AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
                factory.autowireBean(task);
                executor.execute(task);
                arrs.clear();
            }
        }

        executor.shutdown();
        while (!executor.isTerminated())
        {
            Thread.sleep(500);
        }
        long end = new Date().getTime();
        long diff = end - start;
        logger.info("FetchStockShortDataJob take time :" + diff/1000 + "s");
    }

}
