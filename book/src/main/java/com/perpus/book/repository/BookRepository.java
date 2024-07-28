package com.perpus.book.repository;

import com.perpus.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {


    @Query(value = "SELECT * FROM book WHERE id =:ID AND stock >=:STOCK", nativeQuery = true)
    Book findBookByIdForUpdate(@Param("ID") Long id, @Param("STOCK") int stock);
}
