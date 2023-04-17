package com.arsen.mappers;

import com.arsen.dto.RecordDTO;
import com.arsen.models.Record;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public RecordMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RecordDTO convertToDTO(Record record) {
        return modelMapper.map(record, RecordDTO.class);
    }

    public Record convertToEntity(RecordDTO recordDTO) {
        return modelMapper.map(recordDTO, Record.class);
    }
// если не приходились методы маппера, можно думаю и убрать
}
