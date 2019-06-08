import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import stock.biz.convertor.StockMinObjCon;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.sina.GrabComDataBiz;
import stock.dal.mongo.StockMinRepository;
import stock.dal.mongo.pojo.StockMinObj;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-bean.xml"
})
@SpringBootTest
public class HttpTest {
    @Autowired
    private StockMinRepository stockMinRepository;
    @Autowired
    private GrabComDataBiz com_data_fetcher;

    static HttpRequest request = new HttpRequest();

    @Test
    public void testHttpRequest() throws Exception {
        String url = "http://stockpage.10jqka.com.cn/HK0135/finance/";
        HttpResponseEntity resp = request.doRequest(url,null,null,null);
    }

    @Test
    public void testFetchCompanyData() throws Exception{
        com_data_fetcher.fetchDataByTHS("HK0135");

    }



}