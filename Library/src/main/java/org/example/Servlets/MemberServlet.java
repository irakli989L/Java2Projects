package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Database.Member.Member;
import org.example.Services.MemberService;

import java.io.IOException;
import java.util.List;

@WebServlet("/members/*")
public class MemberServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final MemberService memberService = new MemberService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Member> members = memberService.getAllMembers();

        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), members);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Member member = mapper.readValue(req.getInputStream(), Member.class);
            Member saved = memberService.createMember(member);

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
            int id = Integer.parseInt(req.getPathInfo().substring(1));

            Member member = mapper.readValue(req.getInputStream(), Member.class);
            member.setId(id);

            Member updated = memberService.updateMember(member);

            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), updated);

        } catch (RuntimeException e) {
            resp.setStatus(422);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        memberService.deleteMember(id);
        resp.setStatus(204);
    }
}
