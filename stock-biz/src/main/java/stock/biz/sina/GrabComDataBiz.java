package stock.biz.sina;

import org.htmlparser.Tag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.biz.convertor.StockMinObjCon;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.redis.RedisKey;
import stock.biz.redis.StringRedisBiz;
import stock.biz.utils.DateUtil;
import stock.biz.utils.StringUtil;
import stock.dal.mongo.StockMinRepository;
import stock.dal.mongo.pojo.StockMinObj;

import java.lang.reflect.Array;
import java.util.*;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
@Service
public class GrabComDataBiz {
    private final Logger logger = LoggerFactory.getLogger(GrabComDataBiz.class);
    private static String ServerURL_Company_Data = "http://stockpage.10jqka.com.cn/%s/finance/";

    private static List<String> target_ids= Arrays.asList(new String[] {"keyindex", "benefit", "debt","cash"});


    @Autowired
    private StockMinRepository stockMinRepository;
    @Autowired
    StringRedisBiz stringRedisBiz;

    static HttpRequest request = new HttpRequest();

    /***
     * 根据同花顺 的数据拿到公司的历史数据
     * @param stockCode
     * @throws Exception
     */
    public void fetchDataByTHS(String stockCode) throws Exception {
        logger.info("get stock data:" + stockCode);

        String url = String.format(ServerURL_Company_Data , stockCode.toUpperCase());
        HttpResponseEntity httpResponseEntity = request.doRequest(url,null,null,"UTF-8");

        if(httpResponseEntity.getResponseCode() == 200)
        {
            Parser parser = Parser.createParser(httpResponseEntity.getResponseContent(),"UTF-8");
            NodeList nodeList = parser
                    .extractAllNodesThatMatch(new NodeFilter() {
                        //实现该方法,用以过滤标签
                        public boolean accept(Node node) {
                            if (node instanceof Tag ){

                                if(target_ids.contains (((Tag) node).getAttribute("id")) && ((Tag) node).getChildren()!=null)   {
                                    String value = ((Tag) node).getChildren().toHtml();
                                    String key = stockCode + "_" + ((Tag) node).getAttribute("id");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
        }
    }




}
