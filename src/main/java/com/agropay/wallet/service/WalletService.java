package com.agropay.wallet.service;

import com.agropay.wallet.dao.PassbookRepository;
import com.agropay.wallet.dao.WalletRepository;
import com.agropay.wallet.ext.CreateWalletRequest;
import com.agropay.wallet.ext.CreateWalletResponse;
import com.agropay.wallet.model.Passbook;
import com.agropay.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PassbookRepository passbookRepository;

    public CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest) {

        final Wallet wallet = new Wallet(createWalletRequest.getOverdraftLimit());
        final Wallet newWallet = walletRepository.save(wallet);

        final Passbook passbook = new Passbook(newWallet, Collections.emptyList());
        passbookRepository.save(passbook);

        return new CreateWalletResponse(newWallet.getId());
    }
}
