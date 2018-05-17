package nl.wilbrink.email.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import nl.wilbrink.email.dto.MailDTO;
import nl.wilbrink.email.dto.RecipientDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.mashape.unirest.http.Unirest.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static nl.wilbrink.email.dto.RecipientDTO.Type.TO;

@Service
public class EmailService {

    private final String mailgunAPI;
    private final String mailgunKey;

    public EmailService() {
        this.mailgunAPI = "sandbox9c901cfe35fc4ce0be80e72982c25513.mailgun.org";
        this.mailgunKey = "key-fc2e9f3e5e508eb2f301e8a3e7972fb6";
    }

    public void sendMail(MailDTO mail) {
        HttpResponse<JsonNode> response = null;
        try {
            HttpRequestWithBody request = post(
                    format("https://api.mailgun.net/v3/%s/messages", mailgunAPI)
            ).basicAuth("api", mailgunKey)
                .queryString("from", "Domotica <pascal.wilbrink@gmail.com>")
                .queryString("to", mail.getRecipientsOfType(TO)
                    .stream()
                    .map(recipient -> format("<%s> %s", recipient.getName(), recipient.getEmailAddress()))
                    .collect(Collectors.joining(", "))
                )
                .queryString("subject", mail.getSubject())
                .queryString("html", mail.getBody());

            response = request.asJson();
        } catch(UnirestException e) {
            System.out.println("Error");
        }

        JsonNode body = response.getBody();

    }
}


