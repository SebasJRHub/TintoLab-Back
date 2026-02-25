package co.com.tintolab.Services;

import co.com.tintolab.Dto.ReportMonthResponseDTO;
import co.com.tintolab.Dto.ReportMonthSalesResponseDTO;
import co.com.tintolab.Repository.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReportService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private PDFService pdfService;


    public byte[] generateReportMonth(int year, int month){
        ReportMonthResponseDTO report = saleRepository.getReportMounth(year,month);
        return pdfService.generatePdfReportMonth(report);
    }
    public byte[] generateReportMonthDetail(int year, int month){
        List<ReportMonthSalesResponseDTO> sales = saleRepository.getSalesMonthDetails(year,month);
        return pdfService.generatePdfReportMonthDetail(sales,year,month);
    }
}
