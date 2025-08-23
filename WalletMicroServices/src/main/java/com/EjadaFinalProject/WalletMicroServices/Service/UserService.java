package com.EjadaFinalProject.WalletMicroServices.Service;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.UserNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public Users RegisterUser(Users user) {
        return userRepo.save(user);
    }
    public Boolean LoginUser(String username, String password) {
        Users foundUser = userRepo.findByUsernameAndPassword(username, password);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
