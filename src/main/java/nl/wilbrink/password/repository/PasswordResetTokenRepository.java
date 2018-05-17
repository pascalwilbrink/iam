package nl.wilbrink.password.repository;

import nl.wilbrink.password.entity.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    List<PasswordResetToken> findAllByAccountId(Long accountId);

    Optional<PasswordResetToken> findByToken(String token);

}
