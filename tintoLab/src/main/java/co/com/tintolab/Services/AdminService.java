package co.com.tintolab.Services;

import co.com.tintolab.Dto.UpdateUserDTO;
import co.com.tintolab.Dto.UserDTO;
import co.com.tintolab.Models.RoleModel;
import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Repository.RoleRepository;
import co.com.tintolab.Repository.UserRepository;
import co.com.tintolab.Util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;



    public UserDTO activeMode(String username){
        return setActiveStatus(username, true);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO inactiveMode(String username){
        return setActiveStatus(username, false);
    }

    public UserDTO setActiveStatus(String username ,boolean active){
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found(active mode)"));

        user.setActive(active);
        userRepository.save(user);

        return mapToDTO(user);
    }
    private UserDTO mapToDTO(UserModel user){
        return UserDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(roleModel -> roleModel.getName().name())
                        .collect(Collectors.toSet()))
                .build();
    }

    //Editar un usuario
    public UserDTO updateUser(String username, UpdateUserDTO updateUserDTO){
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Can´t you edit this user because not exist"));

        if(updateUserDTO.getEmail() != null){
            user.setEmail(updateUserDTO.getEmail());
        }
        if(updateUserDTO.getName() != null){
            user.setName(updateUserDTO.getName());
        }
        if(updateUserDTO.getLastname() != null){
            user.setLastname(updateUserDTO.getLastname());
        }
        if(!updateUserDTO.getRoles().isEmpty() && updateUserDTO.getRoles() != null){
            Set<RoleModel> roles = updateUserDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(RoleName.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
        }


        return mapToDTO(user);
    }
}
