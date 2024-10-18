package org.example.mappers;

import org.example.dtos.LibraryBookDTO;
import org.example.entities.LibraryBook;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryBookMapper {
    LibraryBookDTO entityToDto(LibraryBook libraryBook);
    LibraryBook dtoToEntity(LibraryBookDTO libraryBookDTO);
    List<LibraryBookDTO> entitiesToDtos(List<LibraryBook> libraryBooks);
}
