package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DatabaseManager;
import org.example.Entity.MessageEntity;
import org.example.Entity.UserEntity;

import java.io.IOException;
import java.util.List;

@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MessageEntity msg = mapper.readValue(req.getInputStream(), MessageEntity.class);

        if (msg.getReceiver() == null || msg.getMessage() == null || msg.getReceiver().isEmpty() || msg.getMessage().isEmpty()) {
            resp.setStatus(422);
            return;
        }

        EntityManager em = DatabaseManager.getInstance().getEntityManager();

        try {
            TypedQuery<UserEntity> query = em.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class);
            query.setParameter("username", msg.getReceiver());

            if (query.getResultList().isEmpty()) {
                em.getTransaction().rollback();
                resp.setStatus(404);
                return;
            }

            em.getTransaction().begin();
            em.persist(msg);
            em.getTransaction().commit();

            resp.setStatus(201);

        } finally {
            em.close();
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        EntityManager em = DatabaseManager.getInstance().getEntityManager();

        try {
            TypedQuery<UserEntity> userQuery = em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password", UserEntity.class);
            userQuery.setParameter("username", username);
            userQuery.setParameter("password", password);

            if (userQuery.getResultList().isEmpty()) {
                resp.setStatus(404);
                return;
            }

            TypedQuery<MessageEntity> msgQuery = em.createQuery("SELECT m FROM MessageEntity m WHERE m.receiver = :username", MessageEntity.class);
            msgQuery.setParameter("username", username);

            List<MessageEntity> messages = msgQuery.getResultList();

            resp.setContentType("application/json");
            resp.setStatus(200);
            System.out.println("Fetching messages for: " + username);
            mapper.writeValue(resp.getOutputStream(), messages);

        } finally {
            em.close();
        }
    }
}
