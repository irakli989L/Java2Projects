package org.example.Database.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Database.DatabaseConnection.*;

public class BookDAOImpl implements BookDAO{

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
                b.setCode(rs.getString("code"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Book findByCode(String code) {
        Book book = null;

        String sql = "Select * FROM books WHERE code=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                book = new Book();
                book.setCode(rs.getString("code"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO books(code, title, author) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getCode());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title=?, author=? WHERE code=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCode());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public void delete(String code) {
        String sql = "DELETE FROM books WHERE code = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
