package stock.dal.mongo.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockCompanyUnitObj implements Serializable {
    public String stockCode;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Long getDatetimelong() {
        return datetimelong;
    }

    public void setDatetimelong(Long datetimelong) {
        this.datetimelong = datetimelong;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String key;

    public String getKey_Unit() {
        return key_Unit;
    }

    public void setKey_Unit(String key_Unit) {
        this.key_Unit = key_Unit;
    }

    public String key_Unit;
    public String name;
    public String datetime;
    public Long datetimelong;
    public BigDecimal value;

    public String PE;
    public String XPE;
    public String GME;
    public String GMEG;
    public String YYE;
    public String YYEG;
    public String PYYET;
    public String PB;
    public String PBE;
    public String PBO;

    public String getPE() {
        return PE;
    }

    public void setPE(String PE) {
        this.PE = PE;
    }

    public String getXPE() {
        return XPE;
    }

    public void setXPE(String XPE) {
        this.XPE = XPE;
    }

    public String getGME() {
        return GME;
    }

    public void setGME(String GME) {
        this.GME = GME;
    }

    public String getGMEG() {
        return GMEG;
    }

    public void setGMEG(String GMEG) {
        this.GMEG = GMEG;
    }

    public String getYYE() {
        return YYE;
    }

    public void setYYE(String YYE) {
        this.YYE = YYE;
    }

    public String getYYEG() {
        return YYEG;
    }

    public void setYYEG(String YYEG) {
        this.YYEG = YYEG;
    }

    public String getPYYET() {
        return PYYET;
    }

    public void setPYYET(String PYYET) {
        this.PYYET = PYYET;
    }

    public String getPB() {
        return PB;
    }

    public void setPB(String PB) {
        this.PB = PB;
    }

    public String getPBE() {
        return PBE;
    }

    public void setPBE(String PBE) {
        this.PBE = PBE;
    }

    public String getPBO() {
        return PBO;
    }

    public void setPBO(String PBO) {
        this.PBO = PBO;
    }

    public String getPGJJ() {
        return PGJJ;
    }

    public void setPGJJ(String PGJJ) {
        this.PGJJ = PGJJ;
    }

    public String getPGX() {
        return PGX;
    }

    public void setPGX(String PGX) {
        this.PGX = PGX;
    }

    public String getPXJL() {
        return PXJL;
    }

    public void setPXJL(String PXJL) {
        this.PXJL = PXJL;
    }

    public String getMER() {
        return MER;
    }

    public void setMER(String MER) {
        this.MER = MER;
    }

    public String PGJJ;
    public String PGX;
    public String PXJL;
    public String MER;


    @Id
    public String id;

    @Override
    public String toString() {
        return   stockCode + '_' +
                 key + '_'+
                 datetime;
    }
}
