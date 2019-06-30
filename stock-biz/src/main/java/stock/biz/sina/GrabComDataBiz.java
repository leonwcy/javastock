package stock.biz.sina;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.codec.binary.StringUtils;
import org.htmlparser.Tag;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.ComanyDataEnum;
import stock.biz.http.HttpRequest;
import stock.biz.http.HttpResponseEntity;
import stock.biz.redis.StringRedisBiz;
import stock.biz.utils.DateUtil;
import stock.biz.utils.StringUtil;
import stock.dal.mongo.StockCompanyRepository;
import stock.dal.mongo.StockMinRepository;
import stock.dal.mongo.pojo.StockCompanyObj;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import stock.dal.mongo.pojo.StockCompanyRawObj;
import stock.dal.mongo.pojo.StockCompanyUnitObj;

@Service
public class GrabComDataBiz {
    private final Logger logger = LoggerFactory.getLogger(GrabComDataBiz.class);
    private static String ServerURL_Company_Data = "http://stockpage.10jqka.com.cn/%s/finance/";

    private static List<String> target_ids= Arrays.asList(new String[] {"keyindex", "benefit", "debt","cash"});


    @Autowired
    private StockMinRepository stockMinRepository;
    @Autowired
    private StockCompanyRepository stockCompanyRepository;
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
        //if(!"HK0659".equals(stockCode)) return;

        String url = String.format(ServerURL_Company_Data , stockCode.toUpperCase());
        HttpResponseEntity httpResponseEntity = request.doRequest(url,null,null,"UTF-8");

        if(httpResponseEntity.getResponseCode() == 200)
        {
            parseSaveData(stockCode, httpResponseEntity);
        }
    }

    private void parseSaveData(String stockCode, HttpResponseEntity httpResponseEntity) throws ParserException {
        Parser parser = Parser.createParser(httpResponseEntity.getResponseContent(),"UTF-8");
        NodeList nodeList = parser
                .extractAllNodesThatMatch(new NodeFilter() {
                    //实现该方法,用以过滤标签
                    public boolean accept(Node node) {
                        if (node instanceof Tag){

                            if(target_ids.contains (((Tag) node).getAttribute("id")) && ((Tag) node).getChildren()!=null)   {
                                String value = ((Tag) node).getChildren().toHtml();
                                String key = stockCode+"_" + ((Tag) node).getAttribute("id");
                                StockCompanyObj obj = new StockCompanyObj();
                                obj.setId(key);
                                obj.setKey(((Tag) node).getAttribute("id"));
                                obj.setValue(value);
                                logger.info(value);
                                //TODO: 这里需要将4组数据合起来
                                saveUnitObj(value, stockCode,obj.getKey());
                                obj.setStockCode(stockCode);
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }

    private void saveUnitObj(String rawValue,String stockCode,String bigKey){
        StockCompanyRawObj obj = JSON.parseObject( rawValue,StockCompanyRawObj.class);
        Map<String , StockCompanyUnitObj> objMap = new LinkedHashMap<>();

        for (int i =1;i< obj.getReport().length ; i++) {
            for (int j = 0; j < obj.getReport()[i].length; j++) {
                try {
                    String datetime = obj.getReport()[0][j];
                    StockCompanyUnitObj unitObj = null;
                    if(!objMap.containsKey( datetime)){
                        unitObj = new StockCompanyUnitObj();
                        unitObj.setStockCode(stockCode);
                        unitObj.setDatetime(datetime );
                        unitObj.setKey(bigKey);
                    }else{
                        unitObj = objMap.get(datetime);
                    }

                    //unitObj.setName(obj.getTitle()[i]);
                    setKey(unitObj,obj.getTitle()[i],getNum(obj.getReport()[i][j]));
                    objMap.put(datetime,unitObj);
                } catch (Exception ex) {
                    logger.error("data error:" + obj, ex);
                }
            }
        }
        objMap.values().forEach(d->{
                d.setId(d.toString());
                logger.info("save obj" + JSON.toJSONString(d));
                stockCompanyRepository.saveUnitObj(d);
        });

    }

    /***
     * TODO: 这里要考虑 为0 的数值怎么处理，是不是不要了
     * @param number
     * @return
     */
    private BigDecimal getNum(String number){
        if(StringUtil.isNullorEmpty(number)) return BigDecimal.ZERO;

        BigDecimal rtn;
        try{
            rtn = new BigDecimal(number);
            return rtn;
        }catch (Exception ex)
        {
            return BigDecimal.ZERO;
        }
    }

    private void setKey( StockCompanyUnitObj obj,String content,BigDecimal data_value){
        if(StringUtil.isNullorEmpty(content)) return ;
        content=content.replace("\"","");
        content=content.replace("[","");
        content=content.replace("]","");
        String[] tmp = content.split(",");

        String key="";
        String unit = "";
        if(tmp.length>1){
            key = tmp[0].trim();
            unit = tmp[1].trim();
        }

        if(!StringUtil.isNullorEmpty(key)) {
            List<ComanyDataEnum> values = Arrays.asList(ComanyDataEnum.values());
            for (ComanyDataEnum e : values) {
                if (key.equals(e.getDesc())) {
//                    obj.setKey(e.getKey());
//                    obj.setKey_Unit(unit);
                    data_value = TransUnit( data_value, unit);
                    Field f = null;
                    try {
                        f = StockCompanyUnitObj.class.getDeclaredField(e.getKey());
                        f.set(obj , data_value.toString() );
                    } catch (Exception e1) {
                        logger.error("found new Column",e1);
                        continue;
                    }

                }
            }
        }
    }

    //全部换算成rmb
    private BigDecimal TransUnit(BigDecimal value,String unit){
        if("港元".equals(unit)){
            value = value .multiply(new BigDecimal(0.8790));
            value = value.setScale(4,BigDecimal.ROUND_HALF_DOWN);

        }

        return value;

    }


}
