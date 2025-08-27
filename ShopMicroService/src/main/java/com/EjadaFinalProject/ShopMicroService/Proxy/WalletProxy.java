package com.EjadaFinalProject.ShopMicroService.Proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "walletmicroservices")
public interface WalletProxy {
    @PostMapping("/wallets/deposit/{userId}/{amount}")
    void depositToWalletForUser(@PathVariable int userId, @PathVariable double amount);
    @PostMapping("/wallets/withdraw/{userId}/{amount}")
    void withdrawFromWalletForUser(@PathVariable int userId, @PathVariable double amount);
    @GetMapping("/wallets/checkbalance/{userId}/{amount}")
    Boolean checkBalanceForUser(@PathVariable int userId, @PathVariable double amount);
}
