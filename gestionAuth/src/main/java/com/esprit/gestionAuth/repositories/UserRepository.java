package com.esprit.gestionAuth.repositories;

import com.esprit.gestionAuth.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, String> {
    public User findByUserEmail(String UserEmail);

    public boolean existsByUserEmail(String UserEmail);

    @Query("select u.userPassword from User u where u.userEmail=?1")
    public String getPasswordByUserEmail(String UserEmail);

    User findByVerificationToken(String token);
    User findByUserName(String username);

}
