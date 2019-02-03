package p7receipt.format;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.time.LocalDate;


public class ReceiptBaseComponents {
    private static final String series = "LRO";
    private static final Font font10 = FontFactory.getFont(FontFactory.COURIER, 10);
    private static final Font font10B = FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD);

    public PdfPTable createFirstTable(Cart cart, int cartId) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(0);
        table.setWidths(new float[]{55,15,30});

        PdfPCell logo = new PdfPCell(getLogo());
        logo.setBorder(PdfPCell.NO_BORDER);

        table.addCell(logo);
        table.addCell("");
        table.addCell(getTitle(cartId));
        table.addCell(createCell(cart.getProvider().toString()));
        table.addCell(createCellWithColspan("\n" + cart.getCustomerWithCartId(cartId).toString(),font10,2));

        return table;
    }

    public Image getLogo(){
        Image image = null;
        try{
            image = Image.getInstance("loro.jpg");
            image.scaleToFit(160,47);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }
        return image;
    }

    public PdfPCell getTitle(int id){
        Font font11B = FontFactory.getFont(FontFactory.COURIER, 11, Font.BOLD);
        Phrase ph1 = new Phrase("FACTURA FISCALA\n", font11B);
        Phrase ph2 = new Phrase("Seria " + getSeries() +"  Nr. " + id +  "\n" +
                "Data emiterii: " + LocalDate.now() + "\n\n",font10);
        PdfPCell cell = new PdfPCell();
        cell.addElement(ph1);
        cell.addElement(ph2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }


    public PdfPTable createFinalTable(Cart cart, int cartId) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidths(new float[]{14,37,15,10,10});
        table.setWidthPercentage(100);

        table.addCell(getArticleInfo());
        table.addCell(getSignText("Semnatura si stampila furnizorului:"));
        table.addCell(getShippingData());

        double total = cart.getTotalPayment(cartId);
        table.addCell(createCell("Statusul \n platii:\n\n" + cart.getStatus() + "\n",font10B,4,0));
        table.addCell(createCell("Total valoare \n" + String.format("%.2f", cart.getValueForId(cartId)) + "\n\n",font10,2,0));
        table.addCell(createCell("Total T.V.A.\n" + String.format("%.2f", cart.getVatValueForId(cartId)) + "\n \n",font10,2,0));
        table.completeRow();

        table.addCell(createCell("\nRest de plata\n\n" + String.format("%.2f", total)+"\n\n",
                font10B,2,2));
        table.addCell(createCell("Semnatura de primire:",font10,0,3));

        return table;
    }


    public PdfPCell getSignText(String text){
        return createCellWithRowspan(text,5);
    }

    public PdfPCell getArticleInfo(){
        return createCellWithColspan("Conform art. 155 alin. (28) din Legea nr. 571/2003 privind Codul fiscal, factura este valabila fara semnatura si stampila. \n" +
                "Plata numerar cu bon fiscal/ chitanta \n\n",font10,5);
    }

    public PdfPCell getShippingData(){
        PdfPCell cell = new PdfPCell();
        Phrase ph1 = new Phrase("Date privind expeditia",font10B);
        Phrase ph2 = new Phrase("Numele delegatului ......................\n"  + "B.I./C.I. seria nr. eliberat(a) de .................",font10);
        Phrase ph3 = new Phrase("Mijlocul de transport...........",font10);
        Phrase ph4 = new Phrase("Expedierea s-a facut in prezenta noastra la data de...... ora .....",font10);
        Phrase ph5 = new Phrase("Semnaturile.........................\n \n",font10);
        cell.addElement(ph1);
        cell.addElement(ph2);
        cell.addElement(ph3);
        cell.addElement(ph4);
        cell.addElement(ph5);
        cell.setRowspan(5);
        return cell;
    }


    private PdfPCell createCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,font10));
        cell.setLeading(2.0f, 1.0f);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private PdfPCell createCell(String text, Font font, int rowSpan, int colSpan) {
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
        cell.setLeading(2.0f, 1.0f);
        cell.setRowspan(rowSpan);
        cell.setColspan(colSpan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell createCellWithColspan(String text, Font font, int col) {
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
        cell.setLeading(2.0f, 1.0f);
        cell.setColspan(col);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private PdfPCell createCellWithRowspan(String text, int row) {
        PdfPCell cell = new PdfPCell(new Phrase(text,font10));
        cell.setLeading(2.0f, 1.0f);
        cell.setRowspan(row);
        return cell;
    }

    public static String getSeries() {
        return series;
    }
}
