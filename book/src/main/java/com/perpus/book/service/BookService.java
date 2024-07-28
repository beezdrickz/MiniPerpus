package com.perpus.book.service;


import com.perpus.book.dto.in.BookOrderRequest;
import com.perpus.book.dto.in.BookRequest;
import com.perpus.book.dto.out.BookResult;
import com.perpus.book.model.Book;
import com.perpus.book.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public List<BookResult> getAllBook() {
        List<Book> listBookModel = bookRepository.findAll();
        return listBookModel.stream().map(this::convertToDTOList).collect(Collectors.toList());

    }

    public BookResult findBook(Long id) {
        BookResult bookResult = new BookResult();
        Book bookModel = bookRepository.findById(id).get();
        if (bookModel != null) {
            bookResult.setTitle(bookModel.getTitle());
            bookResult.setAuthor(bookModel.getAuthor());
            bookResult.setStock(bookModel.getStock());
            bookResult.setId(bookModel.getId());
        }
        return bookResult;
    }

    @Transactional
    public BookResult findBookForOrder(BookOrderRequest request) {
        BookResult bookResult = new BookResult();

        String jpql = "SELECT b FROM Book b WHERE b.id =:ID AND b.stock >=:STOCK";
        Book bookModel = entityManager.createQuery(jpql, Book.class)
                .setParameter("ID", request.getId()).setParameter("STOCK", request.getStock())
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult();

        if (bookModel != null) {

            bookModel.setStock(bookModel.getStock() - request.getStock());
            entityManager.merge(bookModel);

            bookResult.setTitle(bookModel.getTitle());
            bookResult.setAuthor(bookModel.getAuthor());
            bookResult.setStock(bookModel.getStock());
            bookResult.setId(bookModel.getId());
        }

        return bookResult;
    }

    public List<Book> getAllBookPagination() {
        return null;
    }

    public void insertBook(BookRequest request) throws Exception {
        Book bookModel = new Book();
        bookModel.setAuthor(request.getAuthor());
        bookModel.setTitle(request.getTitle());
        bookModel.setStock(request.getStock());

        try {
            bookRepository.save(bookModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public void updateBook(BookRequest request, Long id) throws Exception {
        Book bookModel = bookRepository.findById(id).get();
        bookModel.setAuthor(request.getAuthor());
        bookModel.setTitle(request.getTitle());
        bookModel.setStock(request.getStock());

        try {
            bookRepository.save(bookModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private BookResult convertToDTOList(Book bookModel) {
        BookResult bookResult = new BookResult();
        bookResult.setAuthor(bookModel.getAuthor());
        bookResult.setId(bookModel.getId());
        bookResult.setStock(bookModel.getStock());
        bookResult.setTitle(bookModel.getTitle());
        return bookResult;

    }


}
