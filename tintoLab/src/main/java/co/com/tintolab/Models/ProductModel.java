package co.com.tintolab.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;
    @Column
    @NotBlank
    private String name;
    @Column(length = 150)
    private String description;
    @Column
    @NotBlank
    private int price;
    @Column
    @NotBlank
    private BigDecimal offer;
    @Column
    @NotBlank
    @Builder.Default
    private int stock = 0;
    @Column
    @NotBlank
    private String image;
}
