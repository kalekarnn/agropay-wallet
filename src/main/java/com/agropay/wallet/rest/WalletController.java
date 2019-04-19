package com.agropay.wallet.rest;

import com.agropay.wallet.ext.CreateWalletRequest;
import com.agropay.wallet.ext.CreateWalletResponse;
import com.agropay.wallet.ext.TransactionRequest;
import com.agropay.wallet.ext.TransactionResponse;
import com.agropay.wallet.service.TransactionService;
import com.agropay.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    TransactionService transactionService;

    @PostMapping
    CreateWalletResponse createWalletResponse(@RequestBody CreateWalletRequest createWalletRequest) {
        return walletService.createWallet(createWalletRequest);
    }

    @PostMapping("/{walletId}/transactions")
    public TransactionResponse doTransaction(@PathVariable("walletId") UUID walletId, @RequestBody TransactionRequest transactionRequest) throws SQLException {
        return transactionService.doTransaction(walletId, transactionRequest);
    }
}
