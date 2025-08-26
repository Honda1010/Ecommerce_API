package com.EjadaFinalProject.WalletMicroServices.Service;

import com.EjadaFinalProject.WalletMicroServices.Authuntication.Model.AuthResponse;
import com.EjadaFinalProject.WalletMicroServices.Authuntication.Services.AuthService;
import com.EjadaFinalProject.WalletMicroServices.Dto.UserLoginDto;
import com.EjadaFinalProject.WalletMicroServices.Dto.UserRegisteDto;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.UserNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthService authService;
    public void RegisterUser(UserRegisteDto user) {
        authService.register(user);
    }
    public AuthResponse LoginUser(UserLoginDto user) {
        return authService.login(user);
    }
    public Users GetUserById(int id) {
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    public  void DeleteUserById(int id){
        Users user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepo.delete(user);
    }

}
