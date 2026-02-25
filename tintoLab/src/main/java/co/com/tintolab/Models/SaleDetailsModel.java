package co.com.tintolab.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "saleDetails")
public class SaleDetailsModel {

    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private SaleModel sale;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductModel product;
    @NotBlank
    @Column
    private Integer amount;
    @NotBlank
    @Column
    @DecimalMin(value = "0.00")
    private BigDecimal unit_price;
    @NotBlank
    @Column
    private BigDecimal subtotal;

}
