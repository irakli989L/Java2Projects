package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Database.Book.Book;
import org.example.Services.BookService;

import java.io.IOException;
import java.util.List;

@WebServlet("/books/*")
public class BookServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Book> books = bookService.getAllBooks();

        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), books);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Book book = mapper.readValue(req.getInputStream(), Book.class);
            Book saved = bookService.createBook(book);

            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), saved);

        } catch (RuntimeException e) {
            resp.setStatus(422);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String code = req.getPathInfo().substring(1);

            Book book = mapper.readValue(req.getInputStream(), Book.class);
            book.setCode(code);

            Book updated = bookService.updateBook(book);

            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), updated);

        } catch (RuntimeException e) {
            resp.setStatus(422);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getPathInfo().substring(1);
        bookService.deleteBook(code);
        resp.setStatus(204);
    }
}
