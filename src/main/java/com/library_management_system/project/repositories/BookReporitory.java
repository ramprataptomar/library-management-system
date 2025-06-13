package com.library_management_system.project.repositories;

import com.library_management_system.project.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReporitory extends JpaRepository<Book, Long> {
}
