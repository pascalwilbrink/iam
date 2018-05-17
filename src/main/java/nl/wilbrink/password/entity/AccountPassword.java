package nl.wilbrink.password.entity;

import nl.wilbrink.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_passwords")
public class AccountPassword extends AbstractEntity {

    @Column(name = "account_id")
    private Long accountId;

    @Column
    private String password;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column
    private boolean active;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

}
