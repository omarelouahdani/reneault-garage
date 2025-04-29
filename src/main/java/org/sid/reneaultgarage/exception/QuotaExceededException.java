package org.sid.reneaultgarage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Levée lorsqu’un garage dépasse son quota de véhicules.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuotaExceededException extends RuntimeException {
    public QuotaExceededException(String message) {
        super(message);
    }
}