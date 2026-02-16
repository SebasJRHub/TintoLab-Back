package co.com.tintolab.Controllers;

import co.com.tintolab.Dto.UpdateUserDTO;
import co.com.tintolab.Dto.UserDTO;
import co.com.tintolab.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @PutMapping("/users/{username}/inactive")
    public ResponseEntity<UserDTO> inactiveUser( @PathVariable String username){
        UserDTO userUpdate = adminService.inactiveMode(username);
        return ResponseEntity.ok(userUpdate);
    }
    @PutMapping("/users/{username}/active")
    public ResponseEntity<UserDTO> activeUser( @PathVariable String username){
        UserDTO userUpdate = adminService.activeMode(username);
        return ResponseEntity.ok(userUpdate);
    }
    @PutMapping("/users/edit/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @RequestBody UpdateUserDTO updateUserDTO){
        UserDTO userUpdate = adminService.updateUser(username, updateUserDTO);
        return ResponseEntity.ok(userUpdate);
    }
}
