package p8receipts.generator;

import p5skeleton.CartItem;
import p6datacollection.ReceiptDetailsCollection;
import p7receipt.format.Cart;
import p7receipt.format.Receipt;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PdfReceipts{
    public void createPdfReceipts(String date, String path) {
        int receiptId = ReceiptDetailsCollection.getMAXReceiptId();
        Cart cart = new Cart();
        Map<Integer, List<CartItem>> map = cart.getCartItemsById();
        for (Map.Entry<Integer, List<CartItem>> entry : map.entrySet()) {
            int cartId = entry.getKey();
            String datesql = "";
            for(CartItem c: entry.getValue()) {
                datesql = c.getDateAdded().toString();
                break;
            }
            LocalDate localDate = convertDate(date);
                Receipt receipt = new Receipt();
                if(localDate.equals(convertDate(datesql))){
                    if(!receipt.checkTheExistenceOfReceiptInDB(cartId)) {
                        receiptId++;
                        receipt.createReceipt(cart, cartId, path, localDate);
                        receipt.addReceiptToDB(cart, cartId,
                                new File(path + "\\" + localDate + "\\Receipt" + cartId + ".pdf"), receiptId);
                        receipt.addReceiptDetailsToDB(cart,cartId,receiptId);
                    }else{
                        receipt.getReceiptFromDB(cartId,path,localDate);
                    }
                }
        }
    }
        public LocalDate convertDate(String date){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;
        }
}