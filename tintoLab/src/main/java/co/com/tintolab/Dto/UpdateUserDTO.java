package co.com.tintolab.Dto;

import co.com.tintolab.Models.ShopModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    String name;
    String lastname;
    String email;
    Set<String> roles;
    Long shop_id;
}
