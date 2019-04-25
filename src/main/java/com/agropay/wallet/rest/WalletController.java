package com.agropay.wallet.rest;

import com.agropay.wallet.ext.*;
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
        return transactionService.processTransaction(walletId, transactionRequest);
    }

    @GetMapping("/{walletId}")
    public CurrentBalanceResponse getCurrentBalance(@PathVariable("walletId") UUID walletId) {
        return new CurrentBalanceResponse(transactionService.getCurrentBalance(walletId));
    }

    @GetMapping("/{walletId}/transactions")
    public TransactionsResponse getTransactions(@PathVariable("walletId") UUID walletId) throws SQLException {
        return new TransactionsResponse(transactionService.getTransactions(walletId));
    }

    @DeleteMapping("/{walletId}/transactions/{transactionId}")
    public DeleteTransactionResponse deleteTransaction(@PathVariable("walletId") UUID walletId, @PathVariable UUID transactionId) throws SQLException {
        return transactionService.deleteTransaction(walletId, transactionId);
    }
}
