package org.example.Services;

import org.example.Database.Member.*;

import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAOImpl();

    public List<Member> getAllMembers() {
        return memberDAO.findAll();
    }

    public Member createMember(Member member) {
        if (member.getEmail() == null || member.getEmail().isEmpty()) throw new RuntimeException("Email is required");

        if (memberDAO.findByEmail(member.getEmail()) != null) throw new RuntimeException("Email already exists");

        member.setId(0);

        if (member.getJoin_date() == null) member.setJoin_date(java.time.LocalDate.now());

        return memberDAO.save(member);
    }

    public Member updateMember(Member member) {

        if (memberDAO.findById(member.getId()) == null) throw new RuntimeException("Member not found");

        return memberDAO.update(member);
    }

    public void deleteMember(int id) {

        if (memberDAO.findById(id) == null) throw new RuntimeException("Member not found");

        memberDAO.delete(id);
    }
}
