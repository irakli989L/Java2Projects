package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Database.Borrowing.Borrowing;
import org.example.Requests.BorrowRequest;
import org.example.Requests.ReturnRequest;
import org.example.Services.BorrowingService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/borrow/*")
public class BorrowingServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final BorrowingService service = new BorrowingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Borrowing> list = service.getAllBorrowings();

        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), list);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                Map<String, Object> body = mapper.readValue(req.getInputStream(), Map.class);

                service.borrowBook(
                        (String) body.get("bookCode"),
                        (Integer) body.get("memberId")
                );

            } else if (path.equals("/return")) {
                Map<String, String> body = mapper.readValue(req.getInputStream(), Map.class);

                service.returnBook(body.get("bookCode"));
            }

            resp.setStatus(200);

        } catch (RuntimeException e) {
            resp.setStatus(422);
            resp.getWriter().write(e.getMessage());
        }

    }
}
