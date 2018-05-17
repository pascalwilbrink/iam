package nl.wilbrink.password;

import nl.wilbrink.password.dto.RequestPasswordResetDTO;
import nl.wilbrink.password.dto.ResetPasswordDTO;
import nl.wilbrink.password.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity requestPasswordReset(@RequestBody RequestPasswordResetDTO requestPasswordReset) {
        passwordService.requestPasswordReset(requestPasswordReset);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPassword) {
        passwordService.resetPassword(resetPassword);

        return ResponseEntity.ok().build();
    }
}
