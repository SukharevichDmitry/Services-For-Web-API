package org.example.services;

import org.example.dtos.LibraryBookDTO;
import org.example.entities.Book;
import org.example.exceptions.BookNotFoundException;
import org.example.exceptions.InvalidBookException;
import org.example.repositories.BookRepository;
import org.example.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${library.service.url}")
    private String LIBRARY_SERVICE_URL;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidBookException("Title of book can not be empty.");
        }

        Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new InvalidBookException("Book with that ISBN is already exists.");
        }

        Book savedBook = bookRepository.save(book);

        LibraryBookDTO libraryBookDTO = new LibraryBookDTO();
        libraryBookDTO.setId(savedBook.getId());
        libraryBookDTO.setAvailable(true);
        libraryBookDTO.setBorrowTime(null);
        libraryBookDTO.setReturnTime(null);

        String authHeader = JwtRequestFilter.getAuthorizationHeader();
        HttpHeaders headers = new HttpHeaders();
        if (authHeader != null) {
            headers.set("Authorization", authHeader);
        }

        HttpEntity<LibraryBookDTO> entity = new HttpEntity<>(libraryBookDTO, headers);

        try {
            restTemplate.postForEntity(LIBRARY_SERVICE_URL+"/add", entity, String.class);
        } catch (Exception e) {
            System.err.println("Error during send to LibraryService: " + e.getMessage());
        }

        JwtRequestFilter.clear();

        return savedBook;
    }

    public Book updateBook(Long id, Book bookDetails) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidBookException("Title of book can not be empty.");
        }

        Optional<Book> existingBook = bookRepository.findByIsbn(bookDetails.getIsbn());
        if (existingBook.isPresent() && !existingBook.get().getId().equals(id)) {
            throw new InvalidBookException("Book with that ISBN is already exists.");
        }

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setGenre(bookDetails.getGenre());
        book.setDescription(bookDetails.getDescription());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        LibraryBookDTO libraryBookDTO = new LibraryBookDTO();
        libraryBookDTO.setId(id);

        String authHeader = JwtRequestFilter.getAuthorizationHeader();
        HttpHeaders headers = new HttpHeaders();
        if (authHeader != null) {
            headers.set("Authorization", authHeader);
        }

        HttpEntity<LibraryBookDTO> entity = new HttpEntity<>(libraryBookDTO, headers);

        try {
            restTemplate.exchange(
                    LIBRARY_SERVICE_URL + "/books/" + id,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            System.err.println("Error during send to LibraryService: " + e.getMessage());
        }

        bookRepository.deleteById(id);
    }


}
