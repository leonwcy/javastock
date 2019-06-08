package stock.biz.sina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.redis.RedisKey;
import stock.biz.redis.StringRedisBiz;
import stock.biz.utils.DateUtil;

import java.util.Date;
import java.util.List;

@Service
public class GrabDataBizTask implements Runnable {

    public List<String> getStockCodes() {
        return stockCodes;
    }

    public void setStockCodes(List<String> stockCodes) {
        this.stockCodes = stockCodes;
    }

    private List<String> stockCodes;

    @Autowired
    GrabDataBiz grabDataBiz;


    public void run() {
        boolean isfirst = true;
        for (String s : stockCodes) {
            //grabDataBiz.fetchStockShortData(s,isfirst);
            grabDataBiz.fetchStockShortDataToMongo(s);
            isfirst = false;
        }

    }
}
