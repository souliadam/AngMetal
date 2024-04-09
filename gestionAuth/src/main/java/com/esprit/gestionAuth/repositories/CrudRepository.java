package com.esprit.gestionAuth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<T,ID> extends JpaRepository<T, ID> {
}
