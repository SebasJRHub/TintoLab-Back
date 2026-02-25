package co.com.tintolab.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleDetailRequestDTO {
    private Long productId;
    private Integer amount;
}
