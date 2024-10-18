package org.example.dtos;

import java.io.Serializable;

public class BookDTO implements Serializable {
    private Long id;                // Уникальный идентификатор книги
    private String title;          // Заголовок книги
    private String author;         // Автор книги
    private String isbn;           // ISBN книги
    private String genre;          // Жанр книги
    private String description;    // Описание книги

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, String isbn, String genre, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
