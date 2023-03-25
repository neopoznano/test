package ru.chuev.natlexquest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GeodataNotFoundException extends ResponseStatusException {
    public GeodataNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("Geodata with id '%d' not found.", id));
    }

}
