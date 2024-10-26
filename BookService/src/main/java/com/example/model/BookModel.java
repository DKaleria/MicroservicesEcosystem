package com.example.model;

import java.io.Serial;
import java.io.Serializable;

public record BookModel(
        String name,
        String authorName,
        String authorSurname,
        String genre
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
