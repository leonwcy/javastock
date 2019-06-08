package stock.biz.redis;

import io.netty.util.internal.StringUtil;
import stock.biz.utils.DateUtil;

import java.util.Date;

/**
 * Created by chenye.wu on 2018-01-06.
 */
public class RedisKey {

    private final String STOCK_REDIS_KEY_PRE = "";


    public static String getStockKey(String stockCode,String date)
    {
        if(StringUtil.isNullOrEmpty(stockCode ))
        {
            throw new IllegalArgumentException("stockCode");
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(stockCode) .append(":").append(date).toString();
    }

    /***
     * 获取当前时间的stockkey
     * @param stockCode
     * @return
     */
    public static String getStockKey(String stockCode)
    {
        if(StringUtil.isNullOrEmpty(stockCode ))
        {
            throw new IllegalArgumentException("stockCode");
        }
        return getStockKey(stockCode, DateUtil.toDate(new Date()));
    }

    /****
     * 获取该时间的key
     * @param stockCode
     * @param date
     * @return
     */
    public static String getStockKey(String stockCode,Date date)
    {
        if(StringUtil.isNullOrEmpty(stockCode ))
        {
            throw new IllegalArgumentException("stockCode");
        }
        return getStockKey(stockCode, DateUtil.toDate(date));
    }

}
