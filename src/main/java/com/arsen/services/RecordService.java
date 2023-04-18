package com.arsen.services;

import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }

    public void saveRecord(Record record) {
        recordRepository.save(record);
    }

    public List<Record> findByUser(User user) {
        return recordRepository.findByUser(user);
    }

}
