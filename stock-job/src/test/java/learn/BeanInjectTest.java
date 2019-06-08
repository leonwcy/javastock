package learn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import stock.learning.event.EmailService;
import stock.learning.event.HotelBookingEvent;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-bean.xml"
})
@SpringBootTest
public class BeanInjectTest {
    @Autowired
    ApplicationContext ctx;
    @Autowired
    BeanFactory factory;
    @Autowired
    BeanFactoryPostProcessor[] processors;
    @Autowired
    EmailService emailService;

    @Autowired
    ExecutorService[] pools;

    ExecutorService pool;

    @Autowired(required =false)
    @Qualifier(value="stockExecutor")
    @Resource( name = "stockExecutor2")
    ExecutorService stockThreadPool;

    @Autowired
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

    @Autowired
    @Qualifier(value="stockExecutor2")
    public void stockExecutor(ExecutorService pool){
       this.pool = pool;
    }

    @Autowired
    public ExecutorService test(ExecutorService[] pools){
        return pools[0];
    }

    @Resource( name = "stockExecutor2")
    public void test2(ExecutorService pools){
       return;
    }

    @Autowired
    public void test3(@Qualifier(value="stockExecutor2") ExecutorService pools){
        return;
    }

    @Value("${spring.redis.database}")
    private String databaseName;

    @Autowired
    Environment environment;

    private final Logger logger = LoggerFactory.getLogger(BeanInjectTest.class);


    @Test
    public void testEvent() throws InterruptedException {
        logger.info("publish event");
        ctx.publishEvent(new HotelBookingEvent("hotelBooking"));

    }



}
