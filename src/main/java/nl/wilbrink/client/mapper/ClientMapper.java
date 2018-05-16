package nl.wilbrink.client.mapper;

import nl.wilbrink.client.dto.ClientDTO;
import nl.wilbrink.client.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client entity);

    Client toEntity(ClientDTO dto);

    void toEntity(@MappingTarget Client entity, ClientDTO dto);

}
