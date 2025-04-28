package com.myproject.sm.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Role;
import com.myproject.sm.repository.RoleRepository;

@Service
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DatabaseInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");

        long countRole = this.roleRepository.count();

        if (countRole == 0) {
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(new Role("TEACHER", "giáo viên dạy học"));
            roles.add(new Role("ADMIN", "admin thì full quyền"));
            roles.add(new Role("PARENT", "phụ huynh học sinh"));
            this.roleRepository.saveAll(roles);
        }

        if (countRole > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");

    }

}
