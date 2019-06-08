package stock.biz.sina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Converter;
import org.springframework.stereotype.Service;
import stock.biz.convertor.StockMinObjCon;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.redis.RedisKey;
import stock.biz.redis.StringRedisBiz;
import stock.biz.utils.DateUtil;
import stock.dal.mongo.StockMinRepository;
import stock.dal.mongo.pojo.StockMinObj;

import java.util.Date;

@Service
public class GrabDataBiz {
    private final Logger logger = LoggerFactory.getLogger(GrabDataBiz.class);
    private static String ServerURL_FullData = "http://hq.sinajs.cn/list=";
    private static String ServerURL_ShortData = "http://hq.sinajs.cn/list=s_";


    @Autowired
    private StockMinRepository stockMinRepository;
    @Autowired
    StringRedisBiz stringRedisBiz;

    static HttpRequest request = new HttpRequest();

    public void fetchStockFullData(String stockCode) throws Exception {
        //TODO: 日志的记录
        logger.info("get stock data:" + stockCode);

        String url = ServerURL_FullData + stockCode.toLowerCase();
        HttpResponseEntity httpResponseEntity = request.doRequest(url,null,null,"UTF-8");

        if(httpResponseEntity.getResponseCode() == 200)
        {
            String[] datas = httpResponseEntity.getResponseContent().split(",");

            if(datas.length <32 )
            {
                logger.error("failed to get stock data:" + stockCode);
                return;
            }
            String day = datas[30];
            String time = datas[31];
            stringRedisBiz.setHash(RedisKey.getStockKey(stockCode,day ),time ,httpResponseEntity.getResponseContent(),5 );
        }
    }

    public void fetchStockShortData(String stockCode,boolean isFirst) {
        //TODO: 日志的记录
        logger.info("get stock data:" + stockCode);

        String url = ServerURL_ShortData + stockCode.toLowerCase();
        HttpResponseEntity httpResponseEntity = null;
        try {
            httpResponseEntity = request.doRequest(url,null,null,"UTF-8");
        } catch (Exception e) {
            logger.error("error with fetchStockShortData",e);
            return;
        }

        if(httpResponseEntity.getResponseCode() == 200)
        {
            if(isFirst) {
                stringRedisBiz.setHash(
                        RedisKey.getStockKey(stockCode),
                        DateUtil.format(new Date(), "HH:mm:00"),
                        httpResponseEntity.getResponseContent(),
                        5);
            }else {
                stringRedisBiz.setHash(
                        RedisKey.getStockKey(stockCode),
                        DateUtil.format(new Date(), "HH:mm:00"),
                        httpResponseEntity.getResponseContent());
            }
        }
    }

    public void fetchStockShortDataToMongo(String stockCode) {
        logger.info("get stock data:" + stockCode);

        String url = ServerURL_ShortData + stockCode.toLowerCase();
        HttpResponseEntity httpResponseEntity = null;
        try {
            httpResponseEntity = request.doRequest(url,null,null,"UTF-8");
        } catch (Exception e) {
            logger.error("error with fetchStockShortData",e);
            return;
        }

        if(httpResponseEntity.getResponseCode() == 200)
        {
            StockMinObj obj = StockMinObjCon.getStockMinObj(stockCode,httpResponseEntity.getResponseContent() );
            if(obj!=null) {
                stockMinRepository.save(obj);
            }
        }
    }



}
