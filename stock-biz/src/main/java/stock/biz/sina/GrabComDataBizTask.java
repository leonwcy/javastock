package stock.biz.sina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrabComDataBizTask implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(GrabComDataBizTask.class);

    public List<String> getStockCodes() {
        return stockCodes;
    }

    public void setStockCodes(List<String> stockCodes) {
        this.stockCodes = stockCodes;
    }

    private List<String> stockCodes;

    @Autowired
    GrabComDataBiz grabComDataBiz;


    public void run() {
        boolean isfirst = true;
        for (String s : stockCodes) {
            //grabDataBiz.fetchStockShortData(s,isfirst);
            try {
                grabComDataBiz.fetchDataByTHS(s);
            } catch (Exception e) {
                logger.error("error execute GrabComDataBizTask fetch stock " + s,e);
            }
            isfirst = false;
        }

    }
}
