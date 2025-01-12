package io.github.gabrielhenrique.minha_agenda_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidTextInputException extends RuntimeException {
    public InvalidTextInputException(String message) {
        super(message);
    }
}
