package stock.learning.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @EventListener
    public void sendEmail(HotelBookingEvent o) throws Exception {
        if(o instanceof HotelBookingEvent) {
            logger.info("sendEmail " + ((HotelBookingEvent) o).getSource().toString());
        }

    }
}
