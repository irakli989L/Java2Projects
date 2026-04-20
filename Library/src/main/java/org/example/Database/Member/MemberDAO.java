package org.example.Database.Member;

import java.util.List;

public interface MemberDAO {
    List<Member> findAll();
    Member findById(int id);
    Member findByEmail(String email);
    Member save(Member member);
    Member update(Member member);
    void delete(int id);
}
