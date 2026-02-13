package co.com.tintolab.Services;

import co.com.tintolab.Dto.AuthRequest;
import co.com.tintolab.Dto.AuthResponse;
import co.com.tintolab.Dto.RegisterDTO;
import co.com.tintolab.Dto.UserDTO;
import co.com.tintolab.Models.RoleModel;
import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Repository.RoleRepository;
import co.com.tintolab.Repository.UserRepository;
import co.com.tintolab.Util.RoleName;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    public AuthResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authToken);

        UserModel userModel = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nombre", userModel.getName());
        extraClaims.put("rol", userModel.getRoles().stream().map(roleModel -> roleModel.getName().name())
                .collect(Collectors.toSet()));

        String token = jwtService.generateToken(userModel, extraClaims);


        return AuthResponse.builder().jwt(token).userDTO(convertoDTO(userModel)).build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public AuthResponse register(RegisterDTO registerDTO) {
        Set<RoleModel> roles = registerDTO.getRoles().stream()
                .map(roleName -> {
                    RoleName roleEnum = RoleName.valueOf(roleName.toUpperCase());
                    return roleRepository.findByName(roleEnum)
                            .orElseThrow(() -> new RuntimeException("Error, no existe este rol"));
                })
                .collect(Collectors.toSet());

        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new EntityExistsException("Ya existe usuario");
        }

        UserModel user = UserModel.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .name(registerDTO.getName())
                .lastname(registerDTO.getLastname())
                .email(registerDTO.getEmail())
                .active(true)
                .roles(roles)
                .build();

        String role = user.getRoles().isEmpty()
                ? "USER"
                : user.getRoles().iterator().next().getName().name();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nombre", user.getName());
        extraClaims.put("rol", role);

        String token = jwtService.generateToken(user, extraClaims);
        userRepository.save(user);
        return AuthResponse.builder()
                .jwt(token)
                .userDTO(convertoDTO(user)).build();

    }

    private UserDTO convertoDTO(UserModel user) {
        String role = user.getRoles().isEmpty()
                ? "USER"
                : user.getRoles().iterator().next().getName().name();
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .active(user.isActive())
                .roles(Collections.singleton(role)).build();
    }

}
