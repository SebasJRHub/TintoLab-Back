package co.com.tintolab.Models;

import co.com.tintolab.Util.RoleName;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "roles")
@Entity
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Enumerated(value = EnumType.STRING)
    private RoleName name;


}
