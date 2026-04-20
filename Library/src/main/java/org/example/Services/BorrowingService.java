package org.example.Services;

import org.example.Database.Book.*;
import org.example.Database.Borrowing.*;
import org.example.Database.Member.*;

import java.util.List;

public class BorrowingService {
    private final BorrowingDAO borrowingDAO = new BorrowingDAOImpl();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final MemberDAO memberDAO = new MemberDAOImpl();

    public List<Borrowing> getAllBorrowings() {
        return borrowingDAO.findAll();
    }

    public Borrowing borrowBook(String bookCode, int memberId) {
        if (bookDAO.findByCode(bookCode) == null) throw new RuntimeException("Book not found");

        if (memberDAO.findById(memberId) == null) throw new RuntimeException("Member not found");

        if (borrowingDAO.isBookBorrowed(bookCode)) throw new RuntimeException("Book is already borrowed");

        return borrowingDAO.save(bookCode, memberId);
    }

    public void returnBook(String bookCode) {
        if (!borrowingDAO.isBookBorrowed(bookCode)) throw new RuntimeException("Book is not currently borrowed");

        borrowingDAO.returnBook(bookCode);
    }
}
