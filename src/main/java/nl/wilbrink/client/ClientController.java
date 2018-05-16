package nl.wilbrink.client;

import java.net.URI;

import nl.wilbrink.client.dto.ClientDTO;
import nl.wilbrink.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO client) {
        ClientDTO createdClient = clientService.createClient(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdClient.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("id") Long id, @RequestBody ClientDTO client) {
        client.setId(id);

        ClientDTO updatedClient = clientService.updateClient(client);

        return ResponseEntity.accepted().body(updatedClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable("id") Long id) {
        ClientDTO client = clientService.getclient(id);

        return ResponseEntity.ok(client);
    }

}
