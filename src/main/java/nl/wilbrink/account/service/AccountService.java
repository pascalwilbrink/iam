package nl.wilbrink.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.wilbrink.account.dto.AccountDTO;
import nl.wilbrink.account.entity.Account;
import nl.wilbrink.account.mapper.AccountMapper;
import nl.wilbrink.account.repository.AccountRepository;
import nl.wilbrink.common.exception.NotFoundException;

import static java.lang.String.format;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDTO createAccount(AccountDTO account) {
        Account entity = accountMapper.toEntity(account);

        entity = accountRepository.save(entity);

        return accountMapper.toDTO(entity);
    }

    public AccountDTO updateAccount(AccountDTO account) {
        Account entity = findAccountEntity(account.getId());

        accountMapper.toEntity(entity, account);

        entity = accountRepository.save(entity);

        return accountMapper.toDTO(entity);
    }

    public AccountDTO getAccount(Long id) {
        Account entity = findAccountEntity(id);

        return accountMapper.toDTO(entity);
    }

    public AccountDTO findByUsername(String username) {
        Account entity = accountRepository.getByUsername(username)
            .orElseThrow(() -> new NotFoundException(format("Account with username %s not found", username)));

        return accountMapper.toDTO(entity);
    }

    private Account findAccountEntity(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Account", id));
    }

}
