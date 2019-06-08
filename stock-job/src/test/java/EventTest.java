import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import stock.biz.convertor.StockMinObjCon;
import stock.dal.mongo.StockMinRepository;
import stock.dal.mongo.pojo.StockMinObj;
import stock.job.grabdata.FetchStockShortDataJob;
import stock.learning.event.EmailService;
import stock.learning.event.HotelBookingEvent;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-bean.xml"
})
@SpringBootTest
public class EventTest {
    @Autowired
    ApplicationContext ctx;
    @Autowired
    EmailService emailService;
    //@Autowired
    @Qualifier(value="stockExecutor")
    //@Resource(name = "stockExecutor")
    ExecutorService stockThreadPool;


    private final Logger logger = LoggerFactory.getLogger(EventTest.class);


    @Test
    public void testEvent() throws InterruptedException {
        logger.info("publish event");
        ctx.publishEvent(new HotelBookingEvent("hotelBooking"));

    }



}
