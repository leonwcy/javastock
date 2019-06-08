package stock.dal.mongo.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockMinObj implements Serializable {
    @Id
    private long id;
    private String stockCode;
    private BigDecimal stockPrice;
    private BigDecimal changeRate;
    private BigDecimal changePrice;
    private BigDecimal dealHand;
    private BigDecimal dealAmount;

    private long dataTime;
    private String dataHumanTime;
    private String dataHumanDate;


    public String getDataHumanDate() {
        return dataHumanDate;
    }

    public void setDataHumanDate(String dataHumanDate) {
        this.dataHumanDate = dataHumanDate;
    }




    public long getDataTime() {
        return dataTime;
    }

    public void setDataTime(long dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataHumanTime() {
        return dataHumanTime;
    }

    public void setDataHumanTime(String dataHumanTime) {
        this.dataHumanTime = dataHumanTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(BigDecimal changeRate) {
        this.changeRate = changeRate;
    }

    public BigDecimal getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(BigDecimal changePrice) {
        this.changePrice = changePrice;
    }

    public BigDecimal getDealHand() {
        return dealHand;
    }

    public void setDealHand(BigDecimal dealHand) {
        this.dealHand = dealHand;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    @Override
    public String toString() {
        return "StockMinObj{" +
                "id=" + id +
                ", stockCode='" + stockCode + '\'' +
                ", stockPrice=" + stockPrice +
                ", changeRate=" + changeRate +
                ", changePrice=" + changePrice +
                ", dealHand=" + dealHand +
                ", dealAmount=" + dealAmount +
                ", dataTime=" + dataTime +
                ", dataHumanTime='" + dataHumanTime + '\'' +
                ", dataHumanDate='" + dataHumanDate + '\'' +
                '}';
    }
}
