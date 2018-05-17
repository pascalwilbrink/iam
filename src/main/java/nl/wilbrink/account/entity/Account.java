package nl.wilbrink.account.entity;

import nl.wilbrink.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {

    @Column
    private String username;

    @Column(name = "email_address")
    private String emailAddress;

    @Column
    private boolean locked;

    @Column
    private boolean enabled;

    @Column
    private boolean expired;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isExpired() {
        return expired;
    }

}
