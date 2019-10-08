import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import stock.biz.convertor.StockMinObjCon;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.redis.StringRedisBiz;
import stock.biz.sina.GrabDataBiz;
import stock.dal.mongo.StockMinRepository;
import stock.dal.mongo.StockTickDataRepository;
import stock.dal.mongo.pojo.StockMinObj;
import stock.dal.mongo.pojo.TicketDataObj;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-bean.xml"
})
@SpringBootTest
public class MongoTest {
    @Autowired
    private StockMinRepository stockMinRepository;

    @Autowired
    private StockTickDataRepository stockTickDataRepository;

    @Test
    public void testGetStockTickData(){
        TicketDataObj obj = new TicketDataObj();
        obj.setCode("hk02991");
        obj.setDate("2019-01-11");
        List<TicketDataObj> data = stockTickDataRepository.get(obj);
        Assert.notEmpty(data);
    }

    @Test
    public void testMongo() throws Exception {

        StockMinObj obj = new StockMinObj();
        obj.setId(1);
        obj.setStockCode("sh13");
        obj.setStockPrice(BigDecimal.ONE);
        stockMinRepository.save(obj);

    }

    @Test
    public void testGetMongo()
    {
        StockMinObjCon.getStockMinObj("sh12332","var hq_str_s_sh600025=\"华能水电,4.820,-0.150,-3.02,1843903,89838\";");
    }



}
