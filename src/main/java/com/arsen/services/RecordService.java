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

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }

    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }// можем отступы делать ? зачем так лепить код
    public Record updateRecord(Long id, Record newRecord){
        Record updatedRecord = getRecordById(id);
        updatedRecord.setUser(newRecord.getUser());
        updatedRecord.setBook(newRecord.getBook());
        updatedRecord.setLoanDate(newRecord.getLoanDate());
        updatedRecord.setReturnDate(newRecord.getReturnDate());
        return recordRepository.save(updatedRecord);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }


    public List<Record> findByUser(User user) {
        return recordRepository.findByUser(user);
    }

    // Столько не нужных методов - убираем мусор
}
