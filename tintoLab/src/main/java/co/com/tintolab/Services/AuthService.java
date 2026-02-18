package co.com.tintolab.Services;

import co.com.tintolab.Dto.*;
import co.com.tintolab.Models.RoleModel;
import co.com.tintolab.Models.ShopModel;
import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Repository.RoleRepository;
import co.com.tintolab.Repository.ShopRepository;
import co.com.tintolab.Repository.UserRepository;
import co.com.tintolab.Util.RoleName;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    private ShopRepository shopRepository;


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

    public AuthResponse register(RegisterDTO registerDTO) {
        Set<RoleModel> roles = registerDTO.getRoles().stream()
                .map(roleName -> {
                    RoleName roleEnum = RoleName.valueOf(roleName.toUpperCase());
                    return roleRepository.findByName(roleEnum)
                            .orElseThrow(() -> new RuntimeException("Error, no existe este rol"));
                })
                .collect(Collectors.toSet());

        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new EntityExistsException("This user is already registered");
        }
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new EntityExistsException("This email is already registered");
        }
        ShopModel shop = null;
        if (registerDTO.getShopId() != null) {
            shop = shopRepository.findById(registerDTO.getShopId())
                    .orElseThrow(() -> new RuntimeException("La tienda no existe"));
        }
        UserModel user = UserModel.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .name(registerDTO.getName())
                .lastname(registerDTO.getLastname())
                .email(registerDTO.getEmail())
                .active(true)
                .roles(roles)
                .shop(shop)
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
    public ResponseEntity<?> changePassword(ChangePassDTO change){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("This user does not exist"));

        if(!passwordEncoder.matches(change.getOldPassword(), user.getPassword())){
            throw new RuntimeException("This password is incorrected");
        }

        user.setPassword(passwordEncoder.encode(change.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

    private UserDTO convertoDTO(UserModel user) {
        Set<String> roles = user.getRoles() == null
                ? Collections.emptySet()
                : user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        Long shopId = user.getShop() != null
                ? user.getShop().getId_shop()
                : null;
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .active(user.isActive())
                .roles(roles)
                .shopId(shopId)
                .build();
    }

}
