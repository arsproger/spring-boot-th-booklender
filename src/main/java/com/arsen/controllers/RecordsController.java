package com.arsen.controllers;

import com.arsen.models.Record;
import com.arsen.repositories.RecordRepository;
import com.arsen.services.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/records")
public class RecordsController {

    private final RecordRepository recordRepository;
    private final RecordService recordService;

    public RecordsController(RecordRepository recordRepository, RecordService recordService) {
        this.recordRepository = recordRepository;
        this.recordService = recordService;
    }
    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id){
        return recordService.getRecordById(id);
    }
}
