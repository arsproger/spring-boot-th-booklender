package com.arsen.mappers;

import com.arsen.dto.BookDTO;
import com.arsen.dto.CommentDTO;
import com.arsen.models.Book;
import com.arsen.models.BookComment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookCommentMapper {
    private ModelMapper modelMapper;


    @Autowired
    public BookCommentMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

//    public CommentDTO convertToDTO(BookComment bookComment){
//        return modelMapper.map(bookComment, CommentDTO.class)
//    }
    public BookComment convertToEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, BookComment.class);
    }
}
