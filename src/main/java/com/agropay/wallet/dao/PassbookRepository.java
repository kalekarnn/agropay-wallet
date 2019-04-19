package com.agropay.wallet.dao;

import com.agropay.wallet.model.Passbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassbookRepository extends JpaRepository<Passbook, UUID> {
    Passbook findPassbookByWalletId(UUID walletId);
}
