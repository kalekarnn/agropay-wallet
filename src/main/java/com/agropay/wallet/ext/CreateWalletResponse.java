package com.agropay.wallet.ext;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CreateWalletResponse implements Serializable {
    private static final long serialVersionUID = -7217712718292894944L;

    private UUID walletId;

    public CreateWalletResponse(UUID walletId) {
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateWalletResponse that = (CreateWalletResponse) o;
        return Objects.equals(walletId, that.walletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId);
    }

    @Override
    public String toString() {
        return "CreateWalletResponse{" +
                "walletId=" + walletId +
                '}';
    }
}
