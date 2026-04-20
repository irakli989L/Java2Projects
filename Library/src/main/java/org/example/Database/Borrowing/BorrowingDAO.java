package org.example.Database.Borrowing;

import java.util.List;

public interface BorrowingDAO {
    List<Borrowing> findAll();
    boolean isBookBorrowed(String bookCode);
    Borrowing save(String bookCode, int memberId);
    void returnBook(String bookCode);
}
