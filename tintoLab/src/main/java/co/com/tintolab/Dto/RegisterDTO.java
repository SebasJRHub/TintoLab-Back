package co.com.tintolab.Dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String username;
    private String password;
    private String name;
    private String lastname;
    @Email
    private String email;
    private boolean active;
    private Set<String> roles;
}
