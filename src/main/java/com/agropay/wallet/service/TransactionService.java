package com.agropay.wallet.service;

import com.agropay.wallet.common.TransactionType;
import com.agropay.wallet.dao.PassbookRepository;
import com.agropay.wallet.dao.TransactionRepository;
import com.agropay.wallet.dao.WalletRepository;
import com.agropay.wallet.ext.TransactionRequest;
import com.agropay.wallet.ext.TransactionResponse;
import com.agropay.wallet.model.Passbook;
import com.agropay.wallet.model.Transaction;
import com.agropay.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PassbookRepository passbookRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public TransactionResponse doTransaction(UUID walletId, TransactionRequest transactionRequest) throws RuntimeException {

        final Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new ValidationException("wallet not found"));

        switch (transactionRequest.getType()) {
            case DEBIT:
                return doDebitTransaction(wallet, transactionRequest);
            case CREDIT:
                return doCreditTransaction(wallet, transactionRequest);
            default:
                throw new ValidationException("Incorrect transaction type");
        }
    }

    private TransactionResponse doCreditTransaction(Wallet wallet, TransactionRequest transactionRequest) throws RuntimeException {

        checkAndupdateWallet(wallet, transactionRequest);

        Transaction updatedTransaction = createTransaction(transactionRequest);

        updatePassbook(wallet, updatedTransaction);

        return new TransactionResponse(updatedTransaction.getId());
    }

    private void updatePassbook(Wallet wallet, Transaction updatedTransaction) {
        final Passbook passbook = passbookRepository.findPassbookByWalletId(wallet.getId());
        passbook.getTransactions().add(updatedTransaction);
        final Passbook updatedPassbook = passbookRepository.save(passbook);
        throw new RuntimeException("Hello this is an error message");
    }

    private Transaction createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction(TransactionType.CREDIT, transactionRequest.getAmount(), getUTCTime());
        return transactionRepository.save(transaction);
    }

    private void checkAndupdateWallet(Wallet wallet, TransactionRequest transactionRequest) {
        wallet.setBalance(wallet.getBalance().add(transactionRequest.getAmount()));
        Wallet updatedWallet = walletRepository.save(wallet);
    }

    private TransactionResponse doDebitTransaction(Wallet wallet, TransactionRequest transactionRequest) {
        return null;
    }

    private ZonedDateTime getUTCTime() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }
}
