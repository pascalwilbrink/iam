package nl.wilbrink.password.repository;

import nl.wilbrink.password.entity.AccountPassword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRepository extends CrudRepository<AccountPassword, Long> {

    @Query("SELECT ap from AccountPassword ap WHERE ap.active = true AND ap.accountId = ?1")
    Optional<AccountPassword> findActiveByAccountId(long accountId);

}
