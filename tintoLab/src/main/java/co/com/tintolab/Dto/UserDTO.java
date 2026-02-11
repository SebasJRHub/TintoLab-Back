package co.com.tintolab.Dto;

import co.com.tintolab.Models.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    Long id;
    String name;
    Set<String> roles;
}
