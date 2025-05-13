package com.myproject.sm.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Campus;
import com.myproject.sm.domain.Permission;
import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.User;
import com.myproject.sm.repository.CampusRepository;
import com.myproject.sm.repository.PermissionRepository;
import com.myproject.sm.repository.RoleRepository;
import com.myproject.sm.repository.SubjectRepository;
import com.myproject.sm.repository.UserRepository;

@Service
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CampusRepository campusRepository;
    private final SubjectRepository subjectRepository;

    public DatabaseInitializer(RoleRepository roleRepository, PermissionRepository permissionRepository,
            UserRepository userRepository, PasswordEncoder passwordEncoder, CampusRepository campusRepository,
            SubjectRepository subjectRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.campusRepository = campusRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");

        long countRole = this.roleRepository.count();
        long countPermission = this.permissionRepository.count();
        long countUser = this.userRepository.count();
        long countCampus = this.campusRepository.count();
        long countSubject = this.subjectRepository.count();

        if (countCampus == 0) {
            List<Campus> campus = new ArrayList<>();
            campus.add(new Campus("Eco Home", "số 1, ngõ 1, phường Tân Xuân"));
            campus.add(new Campus("Xuân La", "Số 2, ngõ 2, phường Xuân La"));
            campus.add(new Campus("Xuân Đỉnh", "số 1, ngõ 1, phường Xuân Đỉnh"));
            campus.add(new Campus("Ngoại Giao Đoàn", "khu Ngoại Giao Đoàn, Xuân Đỉnh"));

            this.campusRepository.saveAll(campus);
        }

        if (countPermission == 0) {
            ArrayList<Permission> permissions = new ArrayList<>();
            // student
            permissions.add(new Permission("Create a student", "/students", "POST", "STUDENT"));
            permissions.add(new Permission("Update a student", "/students", "PUT", "STUDENT"));
            permissions.add(new Permission("Delete a student", "/students/{id}", "DELETE", "STUDENT"));
            permissions.add(new Permission("Get a student by id", "/students/{id}", "GET", "STUDENT"));
            permissions.add(new Permission("Get students with pagination", "/students", "GET", "STUDENT"));

            // parent
            permissions.add(new Permission("Create a parent", "/parents", "POST", "PARENT"));
            permissions.add(new Permission("Update a parent", "/parents", "PUT", "PARENT"));
            permissions.add(new Permission("Delete a parent", "/parents/{id}", "DELETE", "PARENT"));
            permissions.add(new Permission("Get a parent by id", "/parents/{id}", "GET", "PARENT"));
            permissions.add(new Permission("Get parents with pagination", "/parents", "GET", "PARENT"));

            // teacher
            permissions.add(new Permission("Create a teacher", "/teachers", "POST", "TEACHER"));
            permissions.add(new Permission("Update a teacher", "/teachers", "PUT", "TEACHER"));
            permissions.add(new Permission("Delete a teacher", "/teachers/{id}", "DELETE", "TEACHER"));
            permissions.add(new Permission("Get a teacher by id", "/teachers/{id}", "GET", "TEACHER"));
            permissions.add(new Permission("Get teachers with pagination", "/teachers", "GET", "TEACHER"));

            // role
            permissions.add(new Permission("Create a role", "/roles", "POST", "ROLE"));
            permissions.add(new Permission("Update a role", "/roles", "PUT", "ROLE"));
            permissions.add(new Permission("Delete a role", "/roles/{id}", "DELETE", "ROLE"));
            permissions.add(new Permission("Get a role by id", "/roles/{id}", "GET", "ROLE"));
            permissions.add(new Permission("Get roles with pagination", "/roles", "GET", "ROLE"));

            // campus
            permissions.add(new Permission("Create a campus", "/campus", "POST", "CAMPUS"));
            permissions.add(new Permission("Update a campus", "/campus", "PUT", "CAMPUS"));
            permissions.add(new Permission("Delete a campus", "/campus/{id}", "DELETE", "CAMPUS"));
            permissions.add(new Permission("Get a campus by id", "/campus/{id}", "GET", "CAMPUS"));
            permissions.add(new Permission("Get campus with pagination", "/campus", "GET", "CAMPUS"));

            // facility
            permissions.add(new Permission("Create a facility", "/facilities", "POST", "FACILITY"));
            permissions.add(new Permission("Update a facility", "/facilities", "PUT", "FACILITY"));
            permissions.add(new Permission("Delete a facility", "/facilities/{id}", "DELETE", "FACILITY"));
            permissions.add(new Permission("Get a facility by id", "/facilities/{id}", "GET", "FACILITY"));
            permissions.add(new Permission("Get facilities with pagination", "/facilities", "GET", "FACILITY"));

            // permission
            permissions.add(new Permission("Create a permission", "/permissions", "POST", "PERMISSION"));
            permissions.add(new Permission("Update a permission", "/permissions", "PUT", "PERMISSION"));
            permissions.add(new Permission("Delete a permission", "/permissions/{id}", "DELETE", "PERMISSION"));
            permissions.add(new Permission("Get a permission by id", "/permissions/{id}", "GET", "PERMISSION"));
            permissions.add(new Permission("Get permissions with pagination", "/permissions", "GET", "PERMISSION"));

            // subject
            permissions.add(new Permission("Create a subject", "/subjects", "POST", "SUBJECT"));
            permissions.add(new Permission("Update a subject", "/subjects", "PUT", "SUBJECT"));
            permissions.add(new Permission("Delete a subject", "/subjects/{id}", "DELETE", "SUBJECT"));
            permissions.add(new Permission("Get a subject by id", "/subjects/{id}", "GET", "SUBJECT"));
            permissions.add(new Permission("Get subjects with pagination", "/subjects", "GET", "SUBJECT"));

            // class
            permissions.add(new Permission("Create a class", "/classes", "POST", "CLASS"));
            permissions.add(new Permission("Update a class", "/classes", "PUT", "CLASS"));
            permissions.add(new Permission("Delete a class", "/classes/{id}", "DELETE", "CLASS"));
            permissions.add(new Permission("Get a class by id", "/classes/{id}", "GET", "CLASS"));
            permissions.add(new Permission("Get classes with pagination", "/classes", "GET", "CLASS"));

            // schedule
            permissions.add(new Permission("Create a schedule", "/schedule", "POST", "SCHEDULE"));
            permissions.add(new Permission("Update a schedule", "/schedule", "PUT", "SCHEDULE"));
            permissions.add(new Permission("Delete a schedule", "/schedule/{id}", "DELETE", "SCHEDULE"));
            permissions.add(new Permission("Get schedule ", "/schedule", "GET", "SCHEDULE"));

            // user
            permissions.add(new Permission("Create a user", "/users", "POST", "USER"));
            permissions.add(new Permission("Update a user", "/users", "PUT", "USER"));
            permissions.add(new Permission("Delete a user", "/users/{id}", "DELETE", "USER"));
            permissions.add(new Permission("Get a user by id", "/users/{id}", "GET", "USER"));
            permissions.add(new Permission("Get users with pagination", "/users", "GET", "USER"));

            this.permissionRepository.saveAll(permissions);
        }

        if (countRole == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();
            adminRole.setName("SUPER_ADMIN");
            adminRole.setDescription("Admin full quyền");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);

            ArrayList<Role> roles = new ArrayList<>();
            roles.add(new Role("TEACHER", "Giáo viên dạy học"));
            roles.add(new Role("PARENT", "Phụ huynh học sinh"));
            this.roleRepository.saveAll(roles);
        }

        if (countUser == 0) {
            User adminUser = new User();
            adminUser.setName("I'm super admin");
            adminUser.setUsername("admin@gmail.com");
            adminUser.setPassword(this.passwordEncoder.encode("123456"));

            Optional<Role> roleOptional = this.roleRepository.findByName("SUPER_ADMIN");
            Role adminRole = roleOptional.isPresent() ? roleOptional.get() : null;
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            this.userRepository.save(adminUser);
        }

        if (countRole > 0 && countPermission > 0 && countUser > 0 && countCampus > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");

    }

}
