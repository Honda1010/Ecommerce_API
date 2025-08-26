package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username, String password);
    Users findByEmailAndPassword(String email, String password);
    Optional <Users> findByEmail(String email);
    Optional <Users> findByUsername(String username);
}
