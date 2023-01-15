import java.io.FileOutputStream;
import java.util.Date;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {

    static int bill_no = 100001;

    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    static double disc;

    public static void generate(Wholesalers wholesaler, Products product) {
        try {

            Document document = new Document();
            String file_name = "/Users/archanadevi/IdeaProjects/PDFs/Invoices/" + Integer.toString(bill_no) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            addMetaData(document);
            addContent(document, wholesaler, product);
            System.out.println("Your bill no. is " + bill_no);
            bill_no++;
            System.out.println("Bill invoice can be found at " + file_name);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generate(Retailers retailer, Products product) {
        try {

            Document document = new Document();
            String file_name = "/Users/archanadevi/IdeaProjects/PDFs/Invoices/" + Integer.toString(bill_no) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            addMetaData(document);
            addContent(document, retailer, product);
            System.out.println("Your bill no. is " + bill_no);
            bill_no++;
            System.out.println("Bill invoice can be found at " + file_name);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Bill number : " + bill_no);
        document.addAuthor("Quinbay Corp.");
        document.addCreator("Archana Devi G");
    }

    private static void addContent(Document document, Wholesalers wholesaler, Products product) throws DocumentException {

        Chapter catPart = new Chapter(new Paragraph("INVOICE"),1);

        Paragraph p = new Paragraph();
        p.add(new Paragraph("Invoice date: " + new Date(), smallBold));
        catPart.addAll(p);

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        catPart.addAll(paragraph);

        Paragraph wholesaler_det = new Paragraph("Bill to: " + wholesaler.name);

        catPart.addAll(wholesaler_det);

        Products p1=product;

        if(p1.stock < 10) disc = 0;
        else if(p1.stock < 50) disc=0.1;
        else if(p1.stock < 100) disc=0.2;
        else disc=0.3;

        createTable(catPart, product);

        Paragraph gap = new Paragraph();
        addEmptyLine(gap, 3);
        catPart.addAll(gap);

        Paragraph thanks = new Paragraph("------------------------------------------------Thank you for purchasing!------------------------------------------------");
        thanks.setAlignment(Element.ALIGN_RIGHT);
        catPart.addAll(thanks);

        document.add(catPart);
    }

    private static void addContent(Document document, Retailers retailer, Products product) throws DocumentException {

        Chapter catPart = new Chapter(new Paragraph("INVOICE"),1);

        Paragraph p = new Paragraph();
        p.add(new Paragraph("Invoice date: " + new Date(), smallBold));
        catPart.addAll(p);

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        catPart.addAll(paragraph);

        Paragraph retailer_det = new Paragraph("Bill to: " + retailer.name);

        catPart.addAll(retailer_det);

        Products p1=product;

        if(p1.stock < 10) disc = 0;
        else if(p1.stock < 50) disc=0.05;
        else if(p1.stock < 100) disc=0.1;
        else disc=0.15;

        createTable(catPart, product);

        Paragraph gap = new Paragraph();
        addEmptyLine(gap, 3);
        catPart.addAll(gap);

        Paragraph thanks = new Paragraph("------------------------------------------------Thank you for purchasing!------------------------------------------------");
        thanks.setAlignment(Element.ALIGN_RIGHT);
        catPart.addAll(thanks);

        document.add(catPart);
    }

    private static void createTable(Section subCatPart, Products product) throws BadElementException {

        double sub_total = 0;
        double total = 0;

        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("Product ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        Products p1 = product;

        table.addCell(Integer.toString(p1.id));
        table.addCell(p1.name);
        table.addCell(Integer.toString(p1.stock));
        table.addCell(Float.toString(p1.price));
        table.addCell(Float.toString(p1.price * p1.stock));
        sub_total += p1.price * p1.stock;

        double gst = p1.gst*sub_total/100;

        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Sub Total");
        table.addCell(Double.toString(sub_total));

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Discount @ " + disc*1000 + "%");
        table.addCell(Double.toString(sub_total*disc));

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("GST @ " + p1.gst + "%");
        String gst_val = String.format("%.02f", gst);
        table.addCell(gst_val);

        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("TOTAL");
        total = sub_total + gst - sub_total*disc;
        String total_val = String.format("%.02f", total);
        table.addCell(total_val);

        subCatPart.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}