package com.arsen.mappers;

import com.arsen.dto.BookDTO;
import com.arsen.models.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookToBookDTO(Book book);
    Book bookDTOtoBook(BookDTO bookDTO);
    List<BookDTO> bookListToBookDTOList(List<Book> bookList);
}
