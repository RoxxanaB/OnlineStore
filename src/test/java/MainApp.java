import p4import.ImportCarts;
import p4import.ImportCustomers;
import p4import.ImportPayments;
import p6datacollection.ReceiptDetailsCollection;
import p7receipt.format.Cart;
import p7receipt.format.Receipt;
import p8receipts.generator.PdfReceipts;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        String path = "D:\\OnlineStore\\Proiect";
        try {
            Scanner scan = new Scanner(System.in);
            int n = 0;
            System.out.println("Alegeti una din optiunile de mai jos: ");
            System.out.println("0 - EXIT!");
            System.out.println("1 - Introducere");
            System.out.println("2 - Incarcare fisier inexistent.");
            System.out.println("3 - Incarcare 'Carts.csv'.");
            System.out.println("4 - Incarcare 'Customers.csv'.");
            System.out.println("5 - Incarcare 'Payments.csv'.");
            System.out.println("6 - Vizualizare o factura.");
            System.out.println("7 - Generare facturi pentru o anumita data.");
            System.out.println("8 - Vizualizare rapoarte.");
            System.out.println("9 - Final :)");

            do {
                System.out.print("\n");
                System.out.print("Introduceti o optiune: ");
                n = scan.nextInt();
                if (n == 1) {
                    openFile(path + "\\img\\intro1.jpg");
                }
                if (n == 2) {
                    String fileName = path + "\\src\\Final Loadings\\BlaBla.csv";
                    System.out.println("Se incarca '" + fileName + "'....");
                    new ImportCarts().loadCartsFromCSV(fileName);
                }
                if (n == 3) {
                    new ImportCarts().loadCartsFromCSV(path + "\\src\\Final Loadings\\Carts.csv");
                    System.out.println("Fisierul 'Carts.csv' a fost incarcat.");
                }
                if (n == 4) {
                    new ImportCustomers().loadCustomersFromCSV(path + "\\src\\Final Loadings\\Customers.csv");
                    System.out.println("Fisierul 'Customers.csv' a fost incarcat.");
                }
                if (n == 5) {
                    new ImportPayments().loadPaymentsFromCSV(path + "\\src\\Final Loadings\\Payments.csv");
                    System.out.println("Fisierul 'Payments.csv' a fost incarcat.");
                }
                if (n == 6) {
                    Receipt receipt = new Receipt();
                    Cart cart = new Cart();
                    LocalDate myDate = cart.getDateFromDB(1200);
                    receipt.createReceipt(cart, 1200, path + "\\src",myDate);
                    openFile(path + "\\src\\" + myDate + "\\Receipt1200.pdf");
                }
                if (n == 7) {
                    int nrOptiune;
                    System.out.println("Optiuni: ");
                    System.out.println("71 - 2018-12-02");
                    System.out.println("72 - 2018-11-16");
                    System.out.println("73 - 2017-03-17");
                    System.out.println("70 - EXIT");
                    do {
                        System.out.print("Selectati o data: ");
                        nrOptiune = scan.nextInt();
                        if (nrOptiune == 71) {
                            new PdfReceipts().createPdfReceipts("2018-12-02", path + "\\src");
                            System.out.println("Facturile au fost generate in: " + path + "\\src\\2018-12-02");
                        }
                        if (nrOptiune == 72) {
                            new PdfReceipts().createPdfReceipts("2018-11-16", path + "\\src");
                            System.out.println("Facturile au fost generate in: " + path + "\\src\\2018-11-16");
                        }
                        if (nrOptiune == 73) {
                            new PdfReceipts().createPdfReceipts("2017-03-17", path + "\\src");
                            System.out.println("Facturile au fost generate in: " + path + "\\src\\2017-03-17");
                        }
                        if(nrOptiune != 70 && nrOptiune != 71 && nrOptiune != 72 && nrOptiune != 73){
                            System.out.println("Numar gresit!");
                        }
                    } while (nrOptiune != 70);
                }
                if (n == 8) {
                    System.out.println("Pentru raport web mergeti la: 'http://localhost:4567/sales'");
                    System.out.print("Pentru raport PDF inserati 81: ");
                    int pdf = scan.nextInt();
                    if (pdf == 81) {
                        new ReceiptDetailsCollection().writeDetailsToPdfFile(path + "\\src");
                        openFile(path + "\\src\\ReceiptDetails.pdf");
                    }
                }
                if (n == 9) {
                    openFile(path + "\\img\\final.jpg");
                }
            } while (n != 0);
        }catch(Throwable e){
            openFile(path + "\\img\\ups.jpg");
        }
    }

    public static void openFile(String fileName){
        try {
            if ((new File(fileName)).exists()) {
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + fileName);
                p.waitFor();
                System.out.println("Operatia s-a incheiat cu succes.");
            } else {
                System.out.println("File does not exist");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

