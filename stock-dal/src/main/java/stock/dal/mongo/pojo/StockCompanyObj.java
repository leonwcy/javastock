package stock.dal.mongo.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class StockCompanyObj implements Serializable {
    @Id
    private String id;
    private String stockCode;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
