package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Dto.UserLoginDto;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @PostMapping("/register")
    ResponseEntity<Users> RegistForUser(@Valid @RequestBody Users user) {
        Users savedUser = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @PostMapping("/login")
    ResponseEntity<Users> LoginForUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        Users foundUser = userRepo.findByUsernameAndPassword(userLoginDto.getUsername(), userLoginDto.getPassword());
        if (foundUser != null && foundUser.getPassword().equals(userLoginDto.getPassword())) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }




}
