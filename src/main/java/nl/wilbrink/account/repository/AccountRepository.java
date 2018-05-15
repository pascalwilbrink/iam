package nl.wilbrink.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.wilbrink.account.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
