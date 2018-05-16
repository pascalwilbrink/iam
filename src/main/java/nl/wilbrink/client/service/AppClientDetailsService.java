package nl.wilbrink.client.service;

import nl.wilbrink.client.dto.AppClientDetails;
import nl.wilbrink.client.entity.Client;
import nl.wilbrink.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class AppClientDetailsService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client entity = clientRepository.findByClientId(clientId)
            .orElseThrow(() -> new ClientRegistrationException(format("Client with clientId %s not found", clientId)));

        entity.setClientSecret(passwordEncoder.encode("clientSecret"));
        return new AppClientDetails(entity);
    }
}
