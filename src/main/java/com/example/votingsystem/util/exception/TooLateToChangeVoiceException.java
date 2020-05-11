package com.example.votingsystem.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "It is too late to vote") //406
public class TooLateToChangeVoiceException extends RuntimeException {
    public TooLateToChangeVoiceException(String message) {
        super(message);
    }
}
