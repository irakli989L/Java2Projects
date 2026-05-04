package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DatabaseManager;
import org.example.Entity.UserEntity;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserEntity user = mapper.readValue(req.getInputStream(), UserEntity.class);

        if (user.getUsername() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            resp.setStatus(422);
            return;
        }

        EntityManager em = DatabaseManager.getInstance().getEntityManager();

        try {
            em.getTransaction().begin();

            TypedQuery<UserEntity> query = em.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class);
            query.setParameter("username", user.getUsername());

            if (!query.getResultList().isEmpty()) {
                em.getTransaction().rollback();
                resp.setStatus(409);
                return;
            }

            em.persist(user);
            System.out.println("USER SAVED");
            em.getTransaction().commit();

            resp.setStatus(201);

        } finally {
            em.close();
        }
    }
}
