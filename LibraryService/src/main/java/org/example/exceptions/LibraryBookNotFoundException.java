package org.example.exceptions;

public class LibraryBookNotFoundException extends RuntimeException {
    public LibraryBookNotFoundException(Long id) {
        super("Library book not found: " + id);
    }
}
