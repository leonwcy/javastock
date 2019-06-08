import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.redis.StringRedisBiz;
import stock.biz.sina.GrabDataBiz;
import stock.dal.mongo.StockMinRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-bean.xml"
})
@SpringBootTest
public class RedisTest {
    @Autowired
    StringRedisBiz stringRedisBiz;
    @Autowired
    ApplicationContext ctx;
    @Autowired
    GrabDataBiz grabDataBiz;
    @Autowired
    private StockMinRepository stockMinRepository;
//    @Autowired
//    StockRepository stockRepository;

    @Test
    public void testSetRedis() throws Exception {
        stringRedisBiz.setHash("testT","hash1","hash1",5);
        stringRedisBiz.setHash("testT","hash2","hash1",5);
        stringRedisBiz.setHash("testT","hash3","hash1",5);
    }

    @Test
    public void testHttpRequest() throws Exception {
        HttpRequest request = new HttpRequest();
        HttpResponseEntity httpResponseEntity = request.doRequest("https://www.baidu.com/",null,null,"UTF-8");
        Assert.notNull( httpResponseEntity.getResponseContent());
    }

    @Test
    public void getSinaData() throws Exception {
        //String s = stockRepository.getStockCodes();


        grabDataBiz.fetchStockFullData("SH600004");

    }

    @Test
    public void getSinaShortData() throws Exception {
        grabDataBiz.fetchStockShortData("SH600004",true);

    }

    @Test
    public void getSinaShortDataMongo() throws Exception {
        grabDataBiz.fetchStockShortDataToMongo("SH600004");

    }

}
