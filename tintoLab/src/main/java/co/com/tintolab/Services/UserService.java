package co.com.tintolab.Services;

import co.com.tintolab.Dto.UserDTO;
import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Transactional
    public List<UserDTO> findAllUsers(){
        List<UserModel> users = userRepository.findAll();

        return users.stream()
                .map(this::convertoDTO).toList();
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
