package nl.wilbrink.account.dto;

import nl.wilbrink.account.entity.Account;
import nl.wilbrink.password.entity.AccountPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.time.LocalDateTime.now;

public class AppUserDetails implements UserDetails {

    private final Account account;
    private final AccountPassword accountPassword;

    public AppUserDetails(Account account, AccountPassword accountPassword) {
        this.account = account;
        this.accountPassword = accountPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return accountPassword.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !account.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return accountPassword.getExpiresAt().isBefore(now());
    }

    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }

}
