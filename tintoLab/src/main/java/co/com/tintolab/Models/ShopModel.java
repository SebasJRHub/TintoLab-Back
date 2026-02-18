package co.com.tintolab.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shop")
@Entity
public class ShopModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_shop;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String address;
    @Column
    @NotBlank
    private String city;
    @Column(length = 20)
    @NotBlank
    private String phone;
    @Column
    @NotBlank
    private boolean active;
}