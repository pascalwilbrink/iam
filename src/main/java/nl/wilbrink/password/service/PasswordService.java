package nl.wilbrink.password.service;

import nl.wilbrink.common.exception.WebException;
import nl.wilbrink.password.dto.PasswordDTO;
import nl.wilbrink.password.entity.AccountPassword;
import nl.wilbrink.password.mapper.PasswordMapper;
import nl.wilbrink.password.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;

@Service
public class PasswordService {

    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordMapper passwordMapper;

    @Autowired
    public PasswordService(
            PasswordRepository passwordRepository,
            PasswordEncoder passwordEncoder,
            PasswordMapper passwordMapper) {
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordMapper = passwordMapper;
    }

    public PasswordDTO createPassword(PasswordDTO password) {
        AccountPassword entity = passwordMapper.toEntity(password);

        AccountPassword createdEntity = passwordRepository.save(entity);

        return passwordMapper.toDTO(createdEntity);
    }

    public PasswordDTO updatePassword(String password, long accountId) {
        AccountPassword oldPassword = passwordRepository.findActiveByAccountId(accountId)
                .orElseThrow(() -> new WebException("No active password found", 500));

        AccountPassword newPassword = new AccountPassword();
        newPassword.setActive(true);
        newPassword.setUserId(accountId);
        newPassword.setPassword(passwordEncoder.encode(password));
        newPassword.setExpiresAt(now().plusYears(1));

        oldPassword.setActive(false);

        passwordRepository.save(oldPassword);
        newPassword = passwordRepository.save(newPassword);

        return passwordMapper.toDTO(newPassword);
    }

}
