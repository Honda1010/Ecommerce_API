package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Authuntication.Model.AuthResponse;
import com.EjadaFinalProject.WalletMicroServices.Dto.UserLoginDto;
import com.EjadaFinalProject.WalletMicroServices.Dto.UserRegisteDto;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import com.EjadaFinalProject.WalletMicroServices.Service.UserService;
import jakarta.validation.Valid;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    ResponseEntity<String> RegistForUser(@Valid @RequestBody UserRegisteDto user) {
        userService.RegisterUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
    @PostMapping("/login")
    ResponseEntity<AuthResponse> LoginForUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        AuthResponse authResponse = userService.LoginUser(userLoginDto);
        return ResponseEntity.ok(authResponse);
    }
    @DeleteMapping("/delete/{userid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> DeleteUser(@PathVariable int userid) {
        userService.DeleteUserById(userid);
        return ResponseEntity.ok("User deleted successfully");
    }
    @GetMapping("/{userid}")
    public ResponseEntity<Users> GetUserById(@PathVariable int userid) {
        Users user = userService.GetUserById(userid);
        return ResponseEntity.ok(user);
    }
}
