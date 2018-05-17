package nl.wilbrink.password.service;

import nl.wilbrink.account.dto.AccountDTO;
import nl.wilbrink.account.service.AccountService;
import nl.wilbrink.common.exception.NotFoundException;
import nl.wilbrink.common.exception.WebException;
import nl.wilbrink.email.dto.MailDTO;
import nl.wilbrink.email.dto.RecipientDTO;
import nl.wilbrink.email.service.EmailService;
import nl.wilbrink.password.dto.PasswordDTO;
import nl.wilbrink.password.dto.RequestPasswordResetDTO;
import nl.wilbrink.password.dto.ResetPasswordDTO;
import nl.wilbrink.password.entity.AccountPassword;
import nl.wilbrink.password.entity.PasswordResetToken;
import nl.wilbrink.password.mapper.PasswordMapper;
import nl.wilbrink.password.repository.PasswordRepository;
import nl.wilbrink.password.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static nl.wilbrink.email.dto.RecipientDTO.Type.TO;

@Service
@Transactional
public class PasswordService {

    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordMapper passwordMapper;
    private final AccountService accountService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Autowired
    public PasswordService(
        PasswordRepository passwordRepository,
        PasswordEncoder passwordEncoder,
        PasswordMapper passwordMapper,
        AccountService accountService,
        PasswordResetTokenRepository passwordResetTokenRepository,
        EmailService emailService
    ) {

        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordMapper = passwordMapper;
        this.accountService = accountService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
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
        newPassword.setAccountId(accountId);
        newPassword.setPassword(passwordEncoder.encode(password));
        newPassword.setExpiresAt(now().plusYears(1));

        oldPassword.setActive(false);

        passwordRepository.save(oldPassword);
        newPassword = passwordRepository.save(newPassword);

        return passwordMapper.toDTO(newPassword);
    }

    public void requestPasswordReset(RequestPasswordResetDTO requestPasswordReset) {
        AccountDTO account = accountService.findByUsername(requestPasswordReset.getUsername());

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(randomUUID().toString());
        resetToken.setExpiresAt(now().plusHours(2));
        resetToken.setAccountId(account.getId());

        resetToken = passwordResetTokenRepository.save(resetToken);

        MailDTO mail = new MailDTO();
        mail.setSubject("Reset password");

        RecipientDTO recipient = new RecipientDTO();
        recipient.setType(TO);
        recipient.setEmailAddress(account.getEmailAddress());
        recipient.setName(account.getUsername());

        mail.addRecipient(recipient);
        mail.setBody(format("Reset password: %s", resetToken.getToken()));

        emailService.sendMail(mail);
    }

    public void resetPassword(ResetPasswordDTO resetPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(resetPassword.getToken())
            .orElseThrow(() -> new NotFoundException(format("Token %s not found", resetPassword.getToken())));

        if (resetToken.getExpiresAt().isBefore(now())) {
            throw new WebException("Token is already expired", 400);
        }

        if (resetToken.getConsumedAt() != null) {
            throw new WebException("Token is already consumed", 400);
        }

        AccountDTO account = accountService.getAccount(resetToken.getAccountId());

        if (!account.getEmailAddress().equals(resetPassword.getEmailAddress())) {
            throw new WebException("Provided email address does not match", 400);
        }

        passwordRepository.findAllByAccountId(account.getId())
            .forEach(password -> {
                password.setActive(false);
                passwordRepository.save(password);
            });

        AccountPassword password = new AccountPassword();
        password.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        password.setActive(true);
        password.setExpiresAt(now().plusYears(1));
        password.setAccountId(resetToken.getAccountId());

        passwordRepository.save(password);
        resetToken.setConsumedAt(now());

        passwordResetTokenRepository.save(resetToken);
    }
}
