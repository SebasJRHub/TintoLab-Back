package co.com.tintolab.Controllers;
import co.com.tintolab.Dto.AuthRequest;
import co.com.tintolab.Dto.AuthResponse;
import co.com.tintolab.Dto.ChangePassDTO;
import co.com.tintolab.Dto.RegisterDTO;
import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse jwtDto = authService.login(authRequest);

        return ResponseEntity.ok(jwtDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterDTO registerDTO) {
        AuthResponse authResponse = authService.register(registerDTO);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDTO changePassDTO){
            authService.changePassword(changePassDTO);
            return ResponseEntity.noContent().build();
    }
}
