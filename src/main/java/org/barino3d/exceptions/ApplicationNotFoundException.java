package org.barino3d.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RepositoryException {
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
