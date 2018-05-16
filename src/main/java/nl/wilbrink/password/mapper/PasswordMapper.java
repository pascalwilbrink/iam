package nl.wilbrink.password.mapper;

import nl.wilbrink.password.dto.PasswordDTO;
import nl.wilbrink.password.entity.AccountPassword;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PasswordMapper {

    PasswordDTO toDTO(AccountPassword entity);

    AccountPassword toEntity(PasswordDTO dto);

    void toEntity(@MappingTarget AccountPassword enttiy, PasswordDTO dto);

}
