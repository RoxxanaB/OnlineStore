package p7receipt.format;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import p5skeleton.CartItem;

import java.util.List;
import java.util.stream.Stream;

public class ReceiptItems {
    private static final String UM = "buc";
    private int nrCrt;

    public PdfPTable getTableOfItems(Cart cart, int id){
        PdfPTable table = new PdfPTable(8);
        table.setSpacingBefore(20);
        table.setWidthPercentage(100);
        addProductsTableHeader(table);

        try{
            table.setWidths(new float[]{6,48,8,10,17,15,17,14});
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        List<CartItem> orderedProducts =  cart.getCartItemWithId(id);
        for(CartItem item: orderedProducts){
            table.addCell(createCell(++nrCrt + "",Element.ALIGN_CENTER));
            table.addCell(createCell(item.getProductDescription() + "",Element.ALIGN_LEFT));
            Stream.of(item.getUM(), item.getQuantity(), item.getPrice(),
                    item.calculateVatRateForOneProduct(), item.calculateValueForProducts(), item.calculateValueForVAT())
                    .forEach(rowTitle -> table.addCell(createCell(rowTitle + "", Element.ALIGN_CENTER)));
        }
        table.setSpacingAfter(250-(table.size()*25));
        return table;
    }


    private PdfPCell createCell(String text, int element) {
        Font fontRows = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(text,fontRows));
        cell.setLeading(2.0f, 1.0f);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(element);
        return cell;
    }

    private void addProductsTableHeader(PdfPTable table){
        Stream.of("Nr.\ncrt", "Denumirea Produselor sau a serviciilor",
                "U.M.", "Cant.", "Pret Unitar\n (fara TVA) \n-RON-", "Cota \nT.V.A. \n-%-",
                "Valoare \n-RON-", "Valoare \nT.V.A.")
                .forEach(columnTitle ->{
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }
}
