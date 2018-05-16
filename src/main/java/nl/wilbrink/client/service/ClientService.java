package nl.wilbrink.client.service;

import nl.wilbrink.client.dto.ClientDTO;
import nl.wilbrink.client.entity.Client;
import nl.wilbrink.client.mapper.ClientMapper;
import nl.wilbrink.client.repository.ClientRepository;
import nl.wilbrink.common.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientDTO createClient(ClientDTO client) {
        Client entity = clientMapper.toEntity(client);

        Client createdEntity = clientRepository.save(entity);

        return clientMapper.toDTO(createdEntity);
    }

    public ClientDTO updateClient(ClientDTO client) {
        Client entity = findClientEntity(client.getId());

        clientMapper.toEntity(entity, client);

        Client updatedEntity = clientRepository.save(entity);

        return clientMapper.toDTO(updatedEntity);
    }

    public ClientDTO getclient(Long id) {
        Client entity = findClientEntity(id);

        return clientMapper.toDTO(entity);
    }

    private Client findClientEntity(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Client", id));
    }
}
