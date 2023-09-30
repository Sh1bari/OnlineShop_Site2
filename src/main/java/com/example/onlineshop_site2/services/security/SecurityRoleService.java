package com.example.onlineshop_site2.services.security;

import com.example.onlineshop_site2.models.entities.Role;
import com.example.onlineshop_site2.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityRoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
