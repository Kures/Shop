import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Orders {

    private String ss;
    private Properties pr;
    private String databaseURL;
    private String user;
    private String password;
    private String driverName;

    private Driver d = null;
    private Connection c = null;
    private Statement s = null;
    private ResultSet rs = null;

//    private ArrayList<String> cID = new ArrayList<>();
//    private ArrayList<String> cID_WH = new ArrayList<>();
//    private ArrayList<String> cID_AG = new ArrayList<>();
//    private ArrayList<String> cID_DR = new ArrayList<>();
//    private ArrayList<String> cID_CAR = new ArrayList<>();

    static public String[][] data;

    public static void main(String args[]) {
        Orders orders = new Orders();
        orders.connectBD();

        JFrame form1 = new JFrame("ЗАКАЗЫ");
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form1.setSize(750, 350);
        form1.setLocationRelativeTo(null);

        String[] columnNames = {
                "Номер заказа",
                "Склад",
                "Предприятие",
                "Водитель",
                "Машина"
        };

        orders.selectAll("ORDERS_INFO");

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton addDriverCar = new JButton("Добавить водителя и машину");

        form1.getContentPane().add(scrollPane);
        form1.setPreferredSize(new Dimension(750, 350));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 1, 5, 0));
        form1.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(addDriverCar);

        form1.pack();
        form1.setLocationRelativeTo(null);
        form1.setVisible(true);
    }

    public void selectAll(String from) {
        ResultSet rs = null;

        try {
            Class.forName(driverName);
            c = DriverManager.getConnection(databaseURL, user, password);
            DatabaseMetaData dbM = c.getMetaData();
            rs = dbM.getTables(null, null, "%", new String[]{"TABLE", "VIEW"});
            while (rs.next()) {
            }
            s = c.createStatement();
            rs = s.executeQuery("select * from " + from);
            ResultSetMetaData rsM = rs.getMetaData();

            int i = 0;
            data = new String[5][100];
            while (rs.next()) {
                data[i][0] = rs.getString("ID");
                data[i][1] = rs.getString("ID_WH");
                data[i][2] = rs.getString("ID_AG");
                data[i][3] = rs.getString("ID_DR");
                data[i][4] = rs.getString("ID_CAR");
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

    public String selectBD(String select, String from) {
        try {
            c = DriverManager.getConnection(databaseURL, user, password);
            s = c.createStatement();
            rs = s.executeQuery("select " + select + " from  " + from);

            ss = "";
            while (rs.next()) {
                ss += rs.getString(select) + "\n";
            }

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
        return ss;
    }

    public boolean connectBD() {
        pr = new Properties();
        try {
            FileInputStream inp = new FileInputStream("database.prop");
            pr.load(inp);
            inp.close();
        } catch (IOException e) {
            return false;
        }

        databaseURL = pr.getProperty("dbURL");
        user = pr.getProperty("user");
        password = pr.getProperty("password");
        driverName = pr.getProperty("driver");

        try {
            Class.forName(driverName);
            c = DriverManager.getConnection(databaseURL, user, password);

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
        return true;
    }
}
