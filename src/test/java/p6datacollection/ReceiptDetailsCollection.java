package p6datacollection;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import p0file.connection.DBConnection;
import p5skeleton.ReceiptDB;
import p5skeleton.ReceiptDetail;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailsCollection {
    private static final String sql_Receipts_Details = "SELECT * FROM LORO_ReceiptDetails A " +
            "JOIN LORO_Categories B ON A.category_id = B.category_id;";
    private static String sql_receiptId = "select max(receipt_id) as max_id from LORO_ReceiptDetails;";

    public static int getMAXReceiptId(){
        int receiptId = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try (Connection conn = DBConnection.getConnection()) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql_receiptId);
                if(rs.next()) {
                    receiptId = rs.getInt("max_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return receiptId;
    }

    public void writeDetailsToPdfFile(String path) {
        Document document = new Document();
        String fileName = checkFile(path) + "\\ReceiptDetails.pdf";

        try{
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            Font font14B = FontFactory.getFont(FontFactory.defaultEncoding, 14, Font.BOLD );
            Font fontTitle = FontFactory.getFont(FontFactory.defaultEncoding, 20, Element.ALIGN_CENTER );
            font14B.setColor(BaseColor.DARK_GRAY);
            fontTitle.setColor(BaseColor.LIGHT_GRAY);

            document.open();
            document.add(new Paragraph("Receipt Details", fontTitle));

            ReceiptCollection receiptCollection = new ReceiptCollection();
            List<ReceiptDB> receiptDBList = receiptCollection.getReceiptsFromDB();

            for(ReceiptDB db: receiptDBList) {
                document.add(new Chunk("Receipt with id " + db.getCartId() + " contains for category: ", font14B));
                document.add(Chunk.NEWLINE);
                for (ReceiptDetail r : getReceiptDetailsFromDB()) {
                    if (r.getCartId() == db.getCartId()) {
                        ZapfDingbatsList details = new ZapfDingbatsList(47, 45);
                        ListItem listItem = new ListItem(r.getCategoryName() + " - " + r.getSubtotal() + " RON ");
                        listItem.setFont(FontFactory.getFont(FontFactory.COURIER, 8));
                        details.add(listItem);
                        document.add(details);
                    }
                }
                document.add(new Paragraph("Having a total of " + receiptCollection.getAmountWithId(db.getCartId())));
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    public File checkFile(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
            System.out.println("Successfully created file: '" + file.getName() + "'");
        }
        return file;
    }

    public static List<ReceiptDetail> getReceiptDetailsFromDB(){
        List<ReceiptDetail> details = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_Receipts_Details))
        {
            while(rs.next())
            {
                details.add(new ReceiptDetail(rs.getInt("receipt_id"), rs.getInt("cart_id"),
                        rs.getInt("category_id"), rs.getString("name"),
                        rs.getDouble("subtotal")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
}
