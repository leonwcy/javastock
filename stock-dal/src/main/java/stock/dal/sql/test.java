package stock.dal.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {

    public static void main(String[] args)
    {
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://localhost\\MSSQLSERVER1:20587;database=ChinaStock";
        String userName = "leon-wcy@hotmail.com";
        String userPwd = "jiajiajia";

        try

        {

            Class.forName(driverName);

            Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);

            String SQL = "SELECT StockCode FROM [Stock] where isActive = 1";
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            System.out.println("连接数据库成功");

        } catch (Exception e)

        {
            System.out.print("连接失败");
        }

    }

}
