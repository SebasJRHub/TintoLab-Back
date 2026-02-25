package co.com.tintolab.Dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReportMonthResponseDTO {
    BigDecimal totalSales;
    Long amountSales;
    Integer year;
    Integer month;
}
