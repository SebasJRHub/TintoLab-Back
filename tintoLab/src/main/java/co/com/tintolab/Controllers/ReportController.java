package co.com.tintolab.Controllers;


import co.com.tintolab.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/resumeReport/pdf")
    public ResponseEntity<byte[]> generateReportMonth(@RequestParam int year, @RequestParam int month){
        byte[] pdf = reportService.generateReportMonth(year, month);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=ventas-resumen.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping("/detailsReport/pdf")
    public ResponseEntity<byte[]> generateReportMonthDetails(@RequestParam int year, @RequestParam int month){
        byte[] pdf = reportService.generateReportMonthDetail(year,month);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=ventas-detalladas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
