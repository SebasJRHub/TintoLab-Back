package co.com.tintolab.Util;

import co.com.tintolab.Models.RoleModel;
import co.com.tintolab.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoleIfNotExists(RoleName.ADMIN);
        createRoleIfNotExists(RoleName.MANAGER);
        createRoleIfNotExists(RoleName.CASHIER);
    }

    private void createRoleIfNotExists(RoleName roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            RoleModel role = RoleModel.builder()
                    .name(roleName)
                    .build();
            roleRepository.save(role);
            System.out.println("✅ Rol creado: " + roleName);
        }
    }
}