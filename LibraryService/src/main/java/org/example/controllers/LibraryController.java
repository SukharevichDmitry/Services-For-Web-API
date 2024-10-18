package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dtos.LibraryBookDTO;
import org.example.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/books")
    @Operation(summary = "Получить все книги", description = "Возвращает список всех книг.")
    public List<LibraryBookDTO> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    @Operation(summary = "Получить книгу по id", description = "Возвращает книгу с указанным id.")
    public ResponseEntity<LibraryBookDTO> getBookById(@PathVariable Long id) {
        return libraryService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/books/available")
    @Operation(summary = "Получить список свободных книг", description = "Возвращает список свободных книг.")
    public ResponseEntity<List<LibraryBookDTO>> getAvailableBooks() {
        List<LibraryBookDTO> availableBooks = libraryService.getAllAvailableBooks();
        if (availableBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availableBooks);
    }


    @PostMapping("/add")
    @Operation(summary = "Создать книгу", description = "Создаёт книгу с указанными параметрами.")
    public LibraryBookDTO createBook(@RequestBody LibraryBookDTO libraryBookDTO) {
        return libraryService.createBook(libraryBookDTO);
    }

    @PutMapping("/books/{id}")
    @Operation(summary = "Изменить книгу", description = "Изменяет параметры книги с указанным id на указанные")
    public ResponseEntity<LibraryBookDTO> updateBook(@PathVariable Long id, @RequestBody LibraryBookDTO libraryBookDTO) {
        return ResponseEntity.ok(libraryService.updateBook(id, libraryBookDTO));
    }

    @DeleteMapping("/books/{id}")
    @Operation(summary = "Удалить книгу", description = "Удаляет книгу с указанным id.")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        libraryService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}
