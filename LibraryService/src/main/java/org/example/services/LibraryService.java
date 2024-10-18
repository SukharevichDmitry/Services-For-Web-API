package org.example.services;

import org.example.dtos.BookDTO;
import org.example.dtos.LibraryBookDTO;
import org.example.entities.LibraryBook;
import org.example.exceptions.LibraryBookNotFoundException;
import org.example.mappers.LibraryBookMapper;
import org.example.repositories.LibraryBookRepository;
import org.example.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    @Autowired
    private LibraryBookRepository libraryBookRepository;

    @Autowired
    private LibraryBookMapper libraryBookMapper;

    @Value("${book.service.url}")
    private String BOOK_SERVICE_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<LibraryBookDTO> getAllBooks() {
        List<LibraryBook> books = libraryBookRepository.findAll();
        return libraryBookMapper.entitiesToDtos(books);
    }

    public Optional<LibraryBookDTO> getBookById(Long id) {
        LibraryBook book = libraryBookRepository.findById(id)
                .orElseThrow(() -> new LibraryBookNotFoundException(id));
        return Optional.of(libraryBookMapper.entityToDto(book));
    }

    public LibraryBookDTO createBook(LibraryBookDTO libraryBookDTO) {
        LibraryBook libraryBook = libraryBookMapper.dtoToEntity(libraryBookDTO);
        libraryBook.setId(libraryBookDTO.getId());
        libraryBookRepository.save(libraryBook);
        return libraryBookMapper.entityToDto(libraryBook);
    }

    public LibraryBookDTO updateBook(Long id, LibraryBookDTO libraryBookDTO) {
        LibraryBook libraryBook = libraryBookRepository.findById(id)
                .orElseThrow(() -> new LibraryBookNotFoundException(id));

        libraryBook.setAvailable(libraryBookDTO.isAvailable());
        libraryBook.setBorrowTime(libraryBookDTO.getBorrowTime());
        libraryBook.setReturnTime(libraryBookDTO.getReturnTime());


        return libraryBookMapper.entityToDto(libraryBookRepository.save(libraryBook));
    }

    public void deleteBook(Long id) {
        libraryBookRepository.deleteById(id);
    }

    public List<LibraryBookDTO> getAllAvailableBooks() {
        List<LibraryBook> availableBooks = libraryBookRepository.findAll().stream()
                .filter(LibraryBook::isAvailable)
                .collect(Collectors.toList());

        List<LibraryBookDTO> resultBooks = new ArrayList<>();

        for (LibraryBook libraryBook : availableBooks) {
            String url = BOOK_SERVICE_URL + libraryBook.getId();

            HttpHeaders headers = new HttpHeaders();
            String authHeader = JwtRequestFilter.getAuthorizationHeader();
            if (authHeader != null) {
                headers.set("Authorization", authHeader);
            }

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<BookDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, BookDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                BookDTO bookDTO = response.getBody();
                LibraryBookDTO libraryBookDTO = new LibraryBookDTO();

                if (bookDTO != null) {
                    libraryBookDTO.setId(bookDTO.getId());
                    libraryBookDTO.setTitle(bookDTO.getTitle());
                    libraryBookDTO.setAuthor(bookDTO.getAuthor());
                    libraryBookDTO.setDescription(bookDTO.getDescription());
                    libraryBookDTO.setGenre(bookDTO.getGenre());
                    libraryBookDTO.setIsbn(bookDTO.getIsbn());
                    libraryBookDTO.setAvailable(libraryBook.isAvailable());
                    libraryBookDTO.setBorrowTime(libraryBook.getBorrowTime());
                    libraryBookDTO.setReturnTime(libraryBook.getReturnTime());

                }

                resultBooks.add(libraryBookDTO);
            } else {
                throw new RuntimeException("BookService не смог вернуть книгу с id: " + libraryBook.getId());
            }
        }

        JwtRequestFilter.clear();
        return resultBooks;
    }

}
