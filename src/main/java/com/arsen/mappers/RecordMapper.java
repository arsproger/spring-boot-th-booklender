package com.arsen.mappers;

import com.arsen.dto.RecordDTO;
import com.arsen.models.Record;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordDTO recordToRecordDTO(Record record);
    Record recordDTOtoRecord(RecordDTO recordDTO);
    List<RecordDTO> recordListToRecordDTOList(List<Record> recordList);
}
