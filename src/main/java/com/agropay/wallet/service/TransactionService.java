package com.agropay.wallet.service;

import com.agropay.wallet.common.TransactionStatus;
import com.agropay.wallet.common.TransactionType;
import com.agropay.wallet.dao.PassbookRepository;
import com.agropay.wallet.dao.TransactionRepository;
import com.agropay.wallet.dao.WalletRepository;
import com.agropay.wallet.ext.DeleteTransactionResponse;
import com.agropay.wallet.ext.TransactionRequest;
import com.agropay.wallet.ext.TransactionResponse;
import com.agropay.wallet.model.Passbook;
import com.agropay.wallet.model.Transaction;
import com.agropay.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PassbookRepository passbookRepository;

    @Transactional
    public TransactionResponse processTransaction(UUID walletId, TransactionRequest transactionRequest) throws RuntimeException {

        final Wallet wallet = getWallet(walletId);

        switch (transactionRequest.getType()) {
            case DEBIT:
                return processDebitTransaction(wallet, transactionRequest);
            case CREDIT:
                return processCreditTransaction(wallet, transactionRequest);
            default:
                throw new ValidationException("Incorrect transaction type");
        }
    }

    public List<Transaction> getTransactions(UUID walletId) {
        final Passbook passbook = passbookRepository.findPassbookByWalletId(walletId);
        return passbook.getTransactions();
    }

    public DeleteTransactionResponse deleteTransaction(UUID walletId, UUID transactionId) {
        final Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ValidationException("Not Found"));
        if (transaction.getStatus() == TransactionStatus.CANCELLED) {
            throw new ValidationException("already deleted this transaction.");
        }

        final Wallet wallet = getWallet(walletId);
        final Passbook passbook = passbookRepository.findPassbookByWalletId(walletId);

        if (!passbook.getTransactions().contains(transaction)) {
            throw new ValidationException("transaction and wallet mismatch");
        }

        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        }
        walletRepository.save(wallet);

        transaction.setStatus(TransactionStatus.CANCELLED);
        transactionRepository.save(transaction);

        return new DeleteTransactionResponse(transactionId, TransactionStatus.CANCELLED);
    }

    public BigDecimal getCurrentBalance(UUID walletId) {
        final Wallet wallet = getWallet(walletId);
        return wallet.getBalance();
    }

    private TransactionResponse processCreditTransaction(Wallet wallet, TransactionRequest transactionRequest) throws RuntimeException {

        checkAndCreditWallet(wallet, transactionRequest);

        final Transaction updatedTransaction = createTransaction(transactionRequest, TransactionType.CREDIT, TransactionStatus.PENDIND);

        updatePassbook(wallet, updatedTransaction);

        updateTransactionStatus(updatedTransaction, TransactionStatus.COMPLETED);

        return new TransactionResponse(updatedTransaction.getId());
    }

    private TransactionResponse processDebitTransaction(Wallet wallet, TransactionRequest transactionRequest) {
        checkAndDebitWallet(wallet, transactionRequest);

        final Transaction updatedTransaction = createTransaction(transactionRequest, TransactionType.DEBIT, TransactionStatus.PENDIND);

        updatePassbook(wallet, updatedTransaction);

        updateTransactionStatus(updatedTransaction, TransactionStatus.COMPLETED);

        return new TransactionResponse(updatedTransaction.getId());
    }

    private void updateTransactionStatus(Transaction updatedTransaction, TransactionStatus completed) {
        updatedTransaction.setStatus(completed);
        transactionRepository.save(updatedTransaction);
    }

    private ZonedDateTime getUTCTime() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }


    private void updatePassbook(Wallet wallet, Transaction updatedTransaction) {
        final Passbook passbook = passbookRepository.findPassbookByWalletId(wallet.getId());
        passbook.getTransactions().add(updatedTransaction);
        final Passbook updatedPassbook = passbookRepository.save(passbook);
    }

    private Transaction createTransaction(TransactionRequest transactionRequest, TransactionType transactionType, TransactionStatus status) {
        Transaction transaction = new Transaction(transactionType, status, transactionRequest.getAmount(), getUTCTime());
        return transactionRepository.save(transaction);
    }

    private void checkAndCreditWallet(Wallet wallet, TransactionRequest transactionRequest) {
        wallet.setBalance(wallet.getBalance().add(transactionRequest.getAmount()));
        Wallet updatedWallet = walletRepository.save(wallet);
    }

    private void checkAndDebitWallet(Wallet wallet, TransactionRequest transactionRequest) {
        if (isNotEnoughBalance(wallet, transactionRequest) && isOverdraftLimitReached(wallet, transactionRequest)) {
            throw new ValidationException("Insufficient Balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(transactionRequest.getAmount()));
        Wallet updatedWallet = walletRepository.save(wallet);
    }

    private boolean isOverdraftLimitReached(Wallet wallet, TransactionRequest transactionRequest) {
        return wallet.getBalance().subtract(wallet.getOverdraftLimit()).compareTo(transactionRequest.getAmount()) < 0;
    }

    private boolean isNotEnoughBalance(Wallet wallet, TransactionRequest transactionRequest) {
        return wallet.getBalance().compareTo(transactionRequest.getAmount()) < 0;
    }

    private Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new ValidationException("wallet not found"));
    }
}
