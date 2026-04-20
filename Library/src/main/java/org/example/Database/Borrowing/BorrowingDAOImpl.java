package org.example.Database.Borrowing;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.example.Database.DatabaseConnection.*;

public class BorrowingDAOImpl implements BorrowingDAO {

    @Override
    public List<Borrowing> findAll() {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM borrowings";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setBook_code(rs.getString("book_code"));
                borrowing.setMember_id(rs.getInt("member_id"));
                borrowing.setBorrow_date(rs.getDate("borrow_date").toLocalDate());

                Date returnDate = rs.getDate("return_date");
                if (returnDate != null) borrowing.setReturn_date(returnDate.toLocalDate());

                borrowings.add(borrowing);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return borrowings;
    }

    @Override
    public boolean isBookBorrowed(String bookCode) {
        String sql = """
            SELECT 1 FROM borrowings
            WHERE book_code = ? AND return_date IS NULL
            LIMIT 1
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bookCode);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Borrowing save(String bookCode, int memberId) {
        String sql = """
            INSERT INTO borrowings(book_code, member_id, borrow_date)
            VALUES (?, ?, CURRENT_DATE)
        """;

        Borrowing book = new Borrowing();
        book.setBook_code(bookCode);
        book.setMember_id(memberId);
        book.setBorrow_date(java.time.LocalDate.now());

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bookCode);
            ps.setInt(2, memberId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public void returnBook(String bookCode) {
        String sql = """
            UPDATE borrowings
            SET return_date = CURRENT_DATE
            WHERE book_code = ?
              AND return_date IS NULL
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bookCode);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
