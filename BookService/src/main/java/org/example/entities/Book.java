package org.example.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
@Table(name = "book", schema = "public")
@Schema(description = "Объект книги")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "Название", example = "Война и мир")
    private String title;

    @Schema(description = "Автор", example = "Лев Николаевич Толстой")
    private String author;

    @Schema(description = "Международный книжный номер", example = "978-5-17-118103-1")
    private String isbn;

    @Schema(description = "Жанр", example = "Роман")
    private String genre;

    @Schema(description = "Описание",
            example = "Ужасно длинная и скучная книга, в которой на протяжении 34 страниц описывается дуб")
    private String description;

    public Book() {
    }

    public Long getId() {
        return id;
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
