package p9reports;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import p0file.connection.DBConnection;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Report {
    public static void main(String[] args) {
        Spark.get("/sales", (req, res) -> {
            return salesReport();
        });
    }
    private static final String sqlBestCustomersInTheLastMonth =
            "Select C.customer_id, C.first_name, C.last_name, CAST(SUM(quantity*price+(quantity*price*0.19)) as decimal(10,2)) as TotalAmount from LORO_Customers C\n" +
                    "INNER JOIN LORO_Carts CS on C.customer_id=CS.customer_id\n" +
                    "WHERE EXTRACT(YEAR_MONTH FROM date_added) = EXTRACT(YEAR_MONTH FROM CURDATE() - INTERVAL 1 MONTH)\n" +
                    "group by customer_id\n" +
                    "order by TotalAmount DESC\n" +
                    "LIMIT 10;";

    private static final String sqlTotalSalesPerCategoriesLastMonth=
            "select a.category_id, a.name, CAST(SUM(c.quantity*c.price+(c.quantity*c.price*0.19)) as decimal(10,2)) as TotalAmount\n" +
                    "from LORO_Categories a INNER JOIN LORO_Products b on a.category_id=b.category_id\n" +
                    "INNER join LORO_Carts c on b.product_id=c.product_id\n" +
                    "WHERE EXTRACT(YEAR_MONTH FROM date_added) = EXTRACT(YEAR_MONTH FROM CURDATE() - INTERVAL 1 MONTH)\n" +
                    "group by a.category_id;";

    private static final String sqlTop3BestSoldCategories2018=
            "select a.category_id, a.name, CAST(SUM(c.quantity*c.price+(c.quantity*c.price*0.19)) as decimal(10,2)) as TotalAmount\n" +
                    "from LORO_Categories a INNER JOIN LORO_Products b on a.category_id=b.category_id\n" +
                    "INNER join LORO_Carts c on b.product_id=c.product_id\n" +
                    "WHERE EXTRACT(YEAR FROM date_added) = '2018'\n" +
                    "group by a.category_id\n" +
                    "order by TotalAmount DESC\n" +
                    "Limit 3;";

    private static String sqlTotalSalesOfToday=
            "select date_added, CAST(SUM(quantity*price+(quantity*price*0.19)) as decimal(10,2)) as TotalAmount\n" +
                    "from LORO_Carts\n" +
                    "WHERE DATE_FORMAT(date_added, '%Y-%m-%d') = CURDATE()\n" +
                    "group by date_added;";

    public static List<Value> totalSalesOfToday() {
        List<Value> valuesForColumns1=new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sqlTotalSalesOfToday))
        {
            while (rs.next()){
                Date date=rs.getDate("date_added") ;
                String amount=rs.getString("TotalAmount");

                valuesForColumns1.add(new Value(date,amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valuesForColumns1;
    }


    public static List<Value> totalSalesPerCategoriesLastMonth () {
        List<Value> valuesForColumns=new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sqlTotalSalesPerCategoriesLastMonth))
        {
            while (rs.next()){
                String name=rs.getString("name");
                String amount=rs.getString("TotalAmount");

                valuesForColumns.add(new Value(name,amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valuesForColumns;
    }


    public static List<Value> top3BestSoldCategories2018 () {
        List<Value> valuesForColumns=new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sqlTop3BestSoldCategories2018))
        {
            while (rs.next()){
                String name=rs.getString("name");
                String amount=rs.getString("TotalAmount");

                valuesForColumns.add(new Value(name,amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valuesForColumns;
    }

    public static List<Value> bestCustomersInTheLastMonth () {
        List<Value> valuesForColumns=new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sqlBestCustomersInTheLastMonth))
        {
            while (rs.next()){
                String first_name=rs.getString("first_name");
                String last_name=rs.getString("last_name");
                String amount=rs.getString("TotalAmount");
                valuesForColumns.add(new Value(first_name + " " + last_name,amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valuesForColumns;

    }

    public static String salesReport () throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("salesReport.mustache");
        StringWriter writer = new StringWriter();

        //✓ total sales of today
        Map<String, Object> values1 = new HashMap<>();
        values1.put("reportName", "Sales Report");
        values1.put("title1", "Total sales of today:");
        values1.put("date", "Date");
        values1.put("amount3", "Total sales");

        values1.put("values1",totalSalesOfToday());


        //✓ total sales per each category in the last month
        Map<String, Object> values2 = new HashMap<>();
        values2.put("title2", "Total sales per each category in the last month:");
        values2.put("categories", "Category:");
        values2.put("amount", "Total sales");

        values2.put("values2",totalSalesPerCategoriesLastMonth());

        //✓ a top of best sold categories (top 3 2018)
        Map<String, Object> values3 = new HashMap<>();
        values3.put("title3", "Top 3 best sold categories in 2018:");
        values3.put("categories2", "Category:");
        values3.put("amount2", "Total sales");

        values3.put("values3",top3BestSoldCategories2018());

        //top first 10 customers from last month:
        Map<String, Object> values4 = new HashMap<>();
        values4.put("title4", "Top 10 customers from last month:");
        values4.put("column1", "Customer name");
        values4.put("column2", "Amount");

        values4.put("values4",bestCustomersInTheLastMonth ());

        m.execute(writer, values1).flush();
        m.execute(writer, values2).flush();
        m.execute(writer, values3).flush();
        m.execute(writer, values4).flush();

        return writer.toString();
    }
}