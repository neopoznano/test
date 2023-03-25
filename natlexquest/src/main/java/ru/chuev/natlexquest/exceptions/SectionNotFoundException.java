package ru.chuev.natlexquest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SectionNotFoundException extends ResponseStatusException {
    public SectionNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("Section with id '%d' not found.", id));
    }

}
