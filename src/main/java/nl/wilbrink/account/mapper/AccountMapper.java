package nl.wilbrink.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import nl.wilbrink.account.dto.AccountDTO;
import nl.wilbrink.account.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDTO(Account entity);

    Account toEntity(AccountDTO dto);

    void toEntity(@MappingTarget Account entity, AccountDTO dto);

}
