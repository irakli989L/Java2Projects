package org.example.Services;

import org.example.Database.Book.*;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO = new BookDAOImpl();

    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    public Book createBook(Book book) {

        if (book.getCode() == null || book.getCode().isEmpty()) throw new RuntimeException("Code is required");

        if (bookDAO.findByCode(book.getCode()) != null) throw new RuntimeException("Book already exists");

        return bookDAO.save(book);
    }

    public Book updateBook(Book book) {

        if (bookDAO.findByCode(book.getCode()) == null) throw new RuntimeException("Book not found");

        return bookDAO.update(book);
    }

    public void deleteBook(String code) {
        bookDAO.delete(code);
    }
}
