package org.barino3d.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommandNotFoundException extends RepositoryException {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
