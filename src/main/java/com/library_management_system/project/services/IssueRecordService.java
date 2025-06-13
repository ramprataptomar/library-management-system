package com.library_management_system.project.services;

import com.library_management_system.project.entities.Book;
import com.library_management_system.project.entities.IssueRecord;
import com.library_management_system.project.entities.User;
import com.library_management_system.project.repositories.BookReporitory;
import com.library_management_system.project.repositories.IssueRecordRepository;
import com.library_management_system.project.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueRecordService {

    private final BookReporitory bookReporitory;
    private final IssueRecordRepository issueRecordRepository;
    private final UserRepository userRepository;

    public IssueRecordService(BookReporitory bookReporitory, IssueRecordRepository issueRecordRepository, UserRepository userRepository) {
        this.bookReporitory = bookReporitory;
        this.issueRecordRepository = issueRecordRepository;
        this.userRepository = userRepository;
    }

    public IssueRecord issueTheBook(Long bookId) {
        Book book = bookReporitory.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.getQuantity() <= 0 || !book.getAvailable()){
            throw new RuntimeException("Book not available");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByName(username).orElseThrow(() -> new RuntimeException("User not found"));

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setUser(user);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setReturnDate(null);
        issueRecord.setReturned(false);

        book.setQuantity(book.getQuantity() - 1);
        if(book.getQuantity() == 0){
            book.setAvailable(false);
        }
        bookReporitory.save(book);

        return issueRecordRepository.save(issueRecord);
    }

    public IssueRecord returnTheBook(Long issueRecordId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if(issueRecord.getReturned()){
            throw new RuntimeException("Book is already returned");
        }

        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setAvailable(true);
        bookReporitory.save(book);

        issueRecord.setReturnDate(LocalDate.now());
        issueRecord.setReturned(true);

        return issueRecordRepository.save(issueRecord);
    }
}
