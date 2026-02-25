package co.com.tintolab.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    @NotBlank
    private String name;
    @Column(length = 150)
    @NotBlank
    private String description;
    @Builder.Default
    private boolean active = true;
}
