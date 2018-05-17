package nl.wilbrink.email.dto;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MailDTO {

    private String subject;

    private String body;

    private List<RecipientDTO> recipients = new ArrayList<>();

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void addRecipient(RecipientDTO recipient) {
        this.recipients.add(recipient);
    }

    public List<RecipientDTO> getRecipients() {
        return recipients;
    }

    public List<RecipientDTO> getRecipientsOfType(RecipientDTO.Type type) {
        return recipients
            .stream()
            .filter(recipient -> recipient.getType().equals(type))
            .collect(toList());
    }

}
