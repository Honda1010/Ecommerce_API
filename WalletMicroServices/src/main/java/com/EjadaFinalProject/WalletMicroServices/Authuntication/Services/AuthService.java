package com.EjadaFinalProject.WalletMicroServices.Authuntication.Services;

import com.EjadaFinalProject.WalletMicroServices.Authuntication.Model.AuthResponse;
import com.EjadaFinalProject.WalletMicroServices.Dto.UserLoginDto;
import com.EjadaFinalProject.WalletMicroServices.Dto.UserRegisteDto;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.UserExistException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.UserNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.UserRole;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authunticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public AuthResponse register(UserRegisteDto Request){
        String username = Request.getUsername();
        if (userRepo.findByUsername(username).isPresent()) {
            throw new UserExistException("Username is already taken");
        }
        Users user=new Users(Request.getUsername(),Request.getEmail(),Request.getPhoneNumber(),passwordEncoder.encode(Request.getPassword()), UserRole.USER);
        userRepo.save(user);
        var jwtToken = jwtService.GenerateToken(user,user.getRole().name());
        return AuthResponse.builder()
                .Token(jwtToken)
                .build();
    }

    public AuthResponse login(UserLoginDto Request){
        Authentication authentication = authunticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(Request.getUsername(), Request.getPassword())
        );

        Users user = userRepo.findByUsername(Request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String jwtToken = jwtService.GenerateToken(user,user.getRole().name());

        return AuthResponse.builder()
                .Token(jwtToken)
                .build();


    }
}
