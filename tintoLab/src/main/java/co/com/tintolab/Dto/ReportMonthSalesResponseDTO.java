package co.com.tintolab.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReportMonthSalesResponseDTO {
    String code;
    LocalDateTime dateHour;
    BigDecimal total;
    String payMethod;
}
