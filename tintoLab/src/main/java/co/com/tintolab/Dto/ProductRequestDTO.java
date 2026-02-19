package co.com.tintolab.Dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequestDTO {
    String name;
    String description;
    Integer price;
    BigDecimal offer;
    Integer stock;
    String image;
}
