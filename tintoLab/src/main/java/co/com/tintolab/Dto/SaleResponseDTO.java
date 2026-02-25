package co.com.tintolab.Dto;

import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Util.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleResponseDTO {
    Long saleId;
    String code;
    LocalDateTime date_hour;
    BigDecimal total;
    PayMethod payMethod;
    Long userId;
    Long shopId;
}
