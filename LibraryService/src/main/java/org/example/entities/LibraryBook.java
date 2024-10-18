package org.example.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "library_book", schema = "public")
public class LibraryBook {

    @Id
    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Свободна ли книга", example = "true")
    private boolean isAvailable;
    @Column(name = "borrow_time")
    @Schema(description = "Дата, когда книгу взяли", example = "\"2024-10-14 18:45:17.722095\"")
    private LocalDateTime borrowTime;
    @Column(name = "return_time")
    @Schema(description = "Дата, когда книгу необходимо вернуть", example = "\"2024-10-21 18:45:17.722095\"")
    private LocalDateTime returnTime;

    public LibraryBook() {
    }

    public void setId(Long bookId) {
        this.id = bookId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(LocalDateTime borrowTime) {
        this.borrowTime = borrowTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

}
