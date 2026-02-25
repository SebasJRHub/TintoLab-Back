package co.com.tintolab.Services;

import co.com.tintolab.Dto.ReportMonthResponseDTO;
import co.com.tintolab.Dto.ReportMonthSalesResponseDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


import java.io.ByteArrayOutputStream;
import java.util.List;


public class PDFService {
    public byte[] generatePdfReportMonth(ReportMonthResponseDTO report) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);


            document.add(new Paragraph("REPORTE MENSUAL DE VENTAS").setBold().setFontSize(18));
            document.add(new Paragraph("Año: " + report.getYear()));
            document.add(new Paragraph("Mes: " + report.getMonth()));
            document.add(new Paragraph("Cantidad de ventas: " + report.getAmountSales()));
            document.add(new Paragraph("Total vendido: $" + report.getTotalSales()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    public byte[] generatePdfReportMonthDetail(List<ReportMonthSalesResponseDTO> sales, int year, int month) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document((pdf));

            document.add(new Paragraph("Reporte Detallado de ventas").setFontSize(18));
            document.add(new Paragraph("Periodo: " + month + "/" + year));

            Table table = new Table(4);

            table.addHeaderCell("Codigo");
            table.addHeaderCell("Fecha");
            table.addHeaderCell("Total");
            table.addHeaderCell("Metodo de pago");

            for (ReportMonthSalesResponseDTO s : sales) {
                table.addCell(s.getCode());
                table.addCell(String.valueOf(s.getDateHour()));
                table.addCell("$" + String.valueOf(s.getTotal()));
                table.addCell(s.getPayMethod());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF detallado", e);
        }
    }
}
