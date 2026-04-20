package org.example.Database.Book;

import java.util.List;

public interface BookDAO {
    List<Book> findAll();
    Book findByCode(String code);
    Book save(Book book);
    Book update(Book book);
    void delete(String code);
}
