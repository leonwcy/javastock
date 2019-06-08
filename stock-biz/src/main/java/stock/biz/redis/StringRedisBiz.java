package stock.biz.redis;

import io.netty.util.internal.StringUtil;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import stock.biz.utils.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenye.wu on 2018-01-04.
 */
@Service
public class StringRedisBiz {
    @Autowired
    private StringRedisTemplate template;

    /**
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public void setNX(final String key, String value){
        this.template.opsForValue().setIfAbsent(key, value);
    }

    /****
     *
     * @param key
     * @param value
     * @param timeout  超时时间 ， 单位秒
     * @throws Exception
     */
    public void setEX(final String key, String value,long timeout){
        this.template.opsForValue().set(key,value,timeout, TimeUnit.SECONDS );
    }

    /****
     *
     * @param key
     * @param hashKey
     * @param value
     * @param timeout 超时时间 ， 单位天
     */
    public void setHash(String key , String hashKey , String value,long timeout)
    {
        this.template.opsForHash().put(key,hashKey,value);
        this.template.expire(key,timeout,TimeUnit.DAYS);
    }

    public void setHash(String key , String hashKey , String value)
    {
        this.template.opsForHash().put(key,hashKey,value);
    }

}
