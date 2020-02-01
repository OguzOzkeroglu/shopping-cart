package com.foo.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super(null, null, false, false);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
