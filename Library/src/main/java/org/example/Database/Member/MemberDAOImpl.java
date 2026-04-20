package org.example.Database.Member;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.example.Database.DatabaseConnection.*;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();

        String sql = "SELECT * FROM members";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setJoin_date(rs.getDate("join_date").toLocalDate());
                members.add(member);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return members;
    }

    @Override
    public Member findById(int id) {
        String sql = "SELECT * FROM members WHERE id=?";
        Member member = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setJoin_date(rs.getDate("join_date").toLocalDate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        Member member = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setJoin_date(rs.getDate("join_date").toLocalDate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO members(name, email, join_date) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println(member.getName());
            System.out.println(member.getEmail());
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setDate(3, Date.valueOf(member.getJoin_date()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                member.setId(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    @Override
    public Member update(Member member) {
        String sql = "UPDATE members SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setInt(3, member.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
