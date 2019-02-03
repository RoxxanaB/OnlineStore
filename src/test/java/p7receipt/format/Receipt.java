package p7receipt.format;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import p0file.connection.DBConnection;
import p5skeleton.ReceiptDB;
import p6datacollection.ReceiptCollection;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Receipt {
    private static final String sql_Receipt =
            "INSERT INTO LORO_Receipts (receipt_id, cart_id, receipt, amount, payment_type) VALUES(?,?,?,?,?);";
    private static final String sql_Receipt_Details =
            "INSERT INTO LORO_ReceiptDetails(receipt_id, cart_id, category_id, subtotal) VALUES (?,?,?,?);";


    public void getReceiptFromDB(int cartId, String path, LocalDate date){
        Blob receipt = new ReceiptCollection().getReceiptWithCartId(cartId);

        String fileName = checkFile(path, date)+ "\\Receipt" + cartId + ".pdf";
        File file = new File(fileName);

        if(!file.exists()){
            try(FileOutputStream fout = new FileOutputStream(file))
            {
                InputStream in = receipt.getBinaryStream();
                byte[] bytes = new byte[in.available()];
                in.read(bytes);

                DataOutputStream dout = new DataOutputStream(fout);
                dout.write(bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            writeLogFile(cartId,path,date);
        }
    }

    public boolean checkTheExistenceOfReceiptInDB(int cartId){
        boolean result = false;
        List<ReceiptDB> receiptListFromDB = ReceiptCollection.getReceiptsFromDB();
        if(receiptListFromDB == null){
            return result;
        }
        for(ReceiptDB r: receiptListFromDB){
            if(r.getCartId() == cartId){
                result = true;
            }
        }
        return result;
    }

    public void addReceiptDetailsToDB(Cart cart, int cartId, int receiptId){
        Map<Integer, Double> subtotals = cart.getSubtotalForEachCategory(cartId);

        for(Map.Entry<Integer,Double> entry: subtotals.entrySet()) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql_Receipt_Details))
            {
                    pstmt.setInt(1, receiptId);
                    pstmt.setInt(2, cartId);
                    pstmt.setInt(3, entry.getKey());
                    pstmt.setDouble(4, entry.getValue());
                    pstmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public void addReceiptToDB(Cart cart, int cartId, File file, int receiptId){
        int idx = 0;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql_Receipt))
        {
            pstmt.setInt(++idx, receiptId);
            pstmt.setInt(++idx, cartId);
            pstmt.setBinaryStream(++idx, new FileInputStream(file));
            pstmt.setDouble(++idx, cart.getTotalPaymentFromCart(cartId));
            pstmt.setString(++idx, cart.getPaymentType(cartId)+"");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createReceipt(Cart cart, int cartId, String path, LocalDate date){
        Document document = null;
        ReceiptBaseComponents components = new ReceiptBaseComponents();
        ReceiptItems items = new ReceiptItems();

        String fileName = checkFile(path, date)+ "\\Receipt" + cartId + ".pdf";
        File file = new File(fileName);
        if(!file.exists()){
            try {
                document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                document.open();
                document.add(components.createFirstTable(cart, cartId));
                document.add(items.getTableOfItems(cart, cartId));
                document.add(components.createFinalTable(cart, cartId));
                document.close();
            } catch (DocumentException e) {
            e.printStackTrace();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            } finally {
                if(document != null){
                    document.close();
                }
            }
        }else{
            writeLogFile(cartId,path,date);
        }
    }

    public void writeLogFile(int cartId, String path, LocalDate date){
        File file = new File(checkFile(path, date) + "\\Log.txt");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)))
        {
            bw.write("Receipt" + cartId + ".pdf already exists!");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File checkFile(String path, LocalDate date){
        File file = new File(path + "\\" + date);
        if(!file.exists()){
            file.mkdir();
            System.out.println("Successfully created file: '" + file.getName() + "'");
        }
        return file;
    }

}
