package com.example.super_hotel.security.repo;

import com.example.super_hotel.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRolename(String rolename); // à partir de nom d'utilisateur
}
