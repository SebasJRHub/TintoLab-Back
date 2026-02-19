package co.com.tintolab.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    Long id;
    String name;
    String description;
    int price;
    BigDecimal offer;
    int stock;
    String image;
}
