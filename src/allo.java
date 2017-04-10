import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//alloooo
public class allo {
    public static void main(String args[]) {
        Properties pr = new Properties();
        try {
            FileInputStream inp = new FileInputStream("database.prop");
            pr.load(inp);
            inp.close();
        } catch (IOException e) {
            return;
        }

        String databaseURL = pr.getProperty("dbURL");

        String user = pr.getProperty("user");

        String password = pr.getProperty("password");
        String driverName = pr.getProperty("driver");

        Driver d = null;
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            Class.forName(driverName);
            System.out.println("OK !!!!");
            c = DriverManager.getConnection(databaseURL, user, password);
            System.out.println("Connect");
            DatabaseMetaData dbM = c.getMetaData();
            rs = dbM.getTables(null, null, "%", new String[]{"TABLE", "VIEW"});
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME") + "  " + rs.getString("TABLE_TYPE"));
            }
            s = c.createStatement();
            rs = s.executeQuery("select full_name from employee where salary<50000");
            ResultSetMetaData rsM = rs.getMetaData();
            System.out.println("columns=" + rsM.getColumnCount());
            for (int i = 0; i < rsM.getColumnCount(); i++) {
                System.out.print(rsM.getColumnName(i + 1) + " - " + rsM.getColumnTypeName(i + 1));
            }
            System.out.println();
            while (rs.next()) {
                System.out.println(rs.getString("full_name"));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Fireberd JDBC driver not found");
        } catch (SQLException e) {
            System.out.println("SQLException" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (s != null) s.close();
            } catch (SQLException e) {
            }
            try {
                if (c != null) c.close();
            } catch (SQLException e) {
            }
        }


    }
}
 