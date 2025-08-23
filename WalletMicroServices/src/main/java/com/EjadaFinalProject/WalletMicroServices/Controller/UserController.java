package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Dto.UserLoginDto;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import com.EjadaFinalProject.WalletMicroServices.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    ResponseEntity<Users> RegistForUser(@Valid @RequestBody Users user) {
        Users savedUser = userService.RegisterUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @PostMapping("/login")
    ResponseEntity<Users> LoginForUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        if (userService.LoginUser(userLoginDto.getUsername(), userLoginDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
