package nl.wilbrink.account.service;

import java.util.ArrayList;
import java.util.Collection;

import nl.wilbrink.account.dto.AppUserDetails;
import nl.wilbrink.account.entity.Account;
import nl.wilbrink.account.repository.AccountRepository;
import nl.wilbrink.common.exception.WebException;
import nl.wilbrink.password.entity.AccountPassword;
import nl.wilbrink.password.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordRepository passwordRepository;

    @Autowired
    public AppUserDetailsService(
            AccountRepository accountRepository,
            PasswordRepository passwordRepository) {
        this.accountRepository = accountRepository;
        this.passwordRepository = passwordRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("Username %s not found", username)));
        AccountPassword password = passwordRepository.findActiveByAccountId(account.getId())
                .orElseThrow(() -> new WebException(format("No active password found for user %s", account.getUsername()), 400));

        return new AppUserDetails(account, password);
    }
}
