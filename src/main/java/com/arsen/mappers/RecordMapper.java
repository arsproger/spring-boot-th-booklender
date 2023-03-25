package com.arsen.mappers;

import com.arsen.dto.RecordDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordDTO recordToRecordDTO(Record record);
    Record recordDTOtoRecord(RecordDTO recordDTO);
    List<RecordDTO> recordListToRecordDTOList(List<Record> recordList);
}
