package com.EjadaFinalProject.ShopMicroService.Wrappers;

import com.EjadaFinalProject.ShopMicroService.Proxy.WalletProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletWrapper {
    @Autowired
    WalletProxy walletProxy;
    @CircuitBreaker(name = "wallet", fallbackMethod = "fallbackCheckBalanceForUser")
    public Boolean checkBalanceForUser(int userId, double amount){
        return walletProxy.checkBalanceForUser(userId,amount);
    }
    @CircuitBreaker(name = "wallet", fallbackMethod = "fallbackDepositToWalletForUser")
    public String depositToWalletForUser(int userId, double amount){
        walletProxy.depositToWalletForUser(userId,amount);
        return "Success";
    }
    @CircuitBreaker(name = "wallet", fallbackMethod = "fallbackWithdrawFromWalletForUser")
    public String withdrawFromWalletForUser(int userId, double amount) {
        walletProxy.withdrawFromWalletForUser(userId, amount);
        return "Success";
    }
    //fullback methods
    public Boolean fallbackCheckBalanceForUser(int userId, double amount, Throwable t) {
        return false;
    }
    public String fallbackDepositToWalletForUser(int userId, double amount, Throwable t) {
        return null;
    }
    public String fallbackWithdrawFromWalletForUser(int userId, double amount, Throwable t) {
        return null;
    }


}
