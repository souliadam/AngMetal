package com.esprit.gestionAuth.services.Implementation;

import com.esprit.gestionAuth.persistence.entity.Role;
import com.esprit.gestionAuth.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}

