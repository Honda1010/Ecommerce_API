package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username, String password);
}
