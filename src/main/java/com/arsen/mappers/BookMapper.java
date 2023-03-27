package com.arsen.mappers;

import com.arsen.dto.BookDTO;
import com.arsen.dto.BookListDTO;
import com.arsen.models.Book;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);

    Book bookDTOtoBook(BookDTO bookDTO);

    List<BookDTO> bookListToBookDTOList(List<Book> bookList);

    List<BookListDTO> bookListToBookListDTO(List<Book> bookList);

}