package com.library_management_system.project.controllers;

import com.library_management_system.project.entities.IssueRecord;
import com.library_management_system.project.services.IssueRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issuerecords")
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    public IssueRecordController(IssueRecordService issueRecordService) {
        this.issueRecordService = issueRecordService;
    }

    @PostMapping("/issuethebook/{bookId}")
    public ResponseEntity<IssueRecord> issueTheBook(@PathVariable Long bookId){
        return ResponseEntity.ok(issueRecordService.issueTheBook(bookId));
    }

    @PostMapping("returnthebook/{issueRecordId}")
    public ResponseEntity<IssueRecord> returnTheBook(@PathVariable Long issueRecordId){
        return ResponseEntity.ok(issueRecordService.returnTheBook(issueRecordId));
    }
}
