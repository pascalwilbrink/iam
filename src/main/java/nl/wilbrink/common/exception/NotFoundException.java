package nl.wilbrink.common.exception;

import static java.lang.String.format;

public class NotFoundException extends WebException {

    public NotFoundException(String message) {
        super(message, 404);
    }

    public NotFoundException(String entity, Long id) {
        super(format("%s with id %d not found", entity, id), 404);
    }
}
