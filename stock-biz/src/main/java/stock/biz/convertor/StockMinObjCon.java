package stock.biz.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stock.biz.sina.GrabDataBiz;
import stock.biz.utils.DateUtil;
import stock.biz.utils.SnowflakeIdWorker;
import stock.dal.mongo.pojo.StockMinObj;

import java.math.BigDecimal;
import java.util.Date;

public class StockMinObjCon {
    static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0,0);

    public static StockMinObj getStockMinObj (String stockCode , String rawData) {
        final Logger logger = LoggerFactory.getLogger(StockMinObjCon.class);
        StockMinObj obj = new StockMinObj();

        String[] datas = rawData.split(",");

        if(datas.length !=6 )
        {
            logger.error("failed to get stock data:" + stockCode);
            return null;
        }
        obj.setStockCode(stockCode);
        obj.setStockPrice(new BigDecimal(datas[1]));
        obj.setChangePrice(new BigDecimal(datas[2]));
        obj.setChangeRate(new BigDecimal(datas[3]));
        obj.setDealHand(new BigDecimal(datas[4]));
        String tmp = datas[5].replace('"',' ').replace(';',' ').trim();
        obj.setDealAmount(new BigDecimal(tmp));
        obj.setDataHumanTime( DateUtil.format(new Date(), "HH:mm:00"));
        obj.setDataHumanDate( DateUtil.toDate(new Date()));
        obj.setDataTime( new Date().getTime());
        obj.setId(idWorker.nextId());
        return obj;
    }
}
