package stock.dal.mongo.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class StockCompanyRawObj implements Serializable {



    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[][] getReport() {
        return report;
    }

    public void setReport(String[][] report) {
        this.report = report;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    private String[] title;
    private String[][] report;
    private String year;


}
