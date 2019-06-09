package stock.dal.sql;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class StockRepository {
    String connectionUrl = "jdbc:sqlserver://leon03\\MSSQLSERVER1:20587;database=ChinaStock";
    String userName = "leon-wcy@hotmail.com";
    String userPwd = "jiajiajia";

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    public String getStockHKCodes(){
        return StockCode.stockCodes;
    }

    public String getStockCodes() {
        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl, userName, userPwd);

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT StockCode FROM [Stock] where isActive = 1";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            StringBuilder sb = new StringBuilder();
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                sb.append(rs.getString(1));
                sb.append(",");
            }

            return sb.toString();
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
        return "";
    }

}
