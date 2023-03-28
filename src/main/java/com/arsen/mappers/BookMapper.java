package com.arsen.mappers;

import com.arsen.dto.BookDTO;
import com.arsen.models.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookDTO convertToDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    public Book convertToEntity(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }
}
