package co.com.tintolab.Models;

import co.com.tintolab.Util.PayMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sale")
public class SaleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date_hour;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Enumerated(value = EnumType.STRING)
    @NotBlank
    private PayMethod payMethod;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShopModel shop;
    @OneToMany(mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SaleDetailsModel> details;


}
