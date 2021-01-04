package org.barino3d.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateApplicationNameException extends RepositoryException {
    public DuplicateApplicationNameException(String message) {
        super(message);
    }
}
