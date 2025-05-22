package dao;

import util.Database;
import model.Plass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlassDAO {

    // Henter ALLE plasser i en gitt sal
    public List<Plass> hentPlasserForKinosal(int kinosalnr) {
        List<Plass> plasser = new ArrayList<>();
        String sql = "SELECT p_radnr, p_setenr, p_kinosalnr FROM kino.tblplass WHERE p_kinosalnr = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, kinosalnr);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int rad = rs.getInt("p_radnr");
                    int sete = rs.getInt("p_setenr");
                    int sal = rs.getInt("p_kinosalnr");

                    plasser.add(new Plass(rad, sete, sal));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plasser;
    }

    // Henter KUN ledige plasser for en visning (ikke koblet til billett)
    public List<Plass> hentLedigePlasser(int visningnr) {
        List<Plass> ledige = new ArrayList<>();
        String sql = """
                SELECT p.p_radnr, p.p_setenr, p.p_kinosalnr
                FROM kino.tblplass p
                JOIN kino.tblvisning v ON p.p_kinosalnr = v.v_kinosalnr
                WHERE v.v_visningnr = ?
                AND NOT EXISTS (
                    SELECT 1 FROM kino.tblplassbillett pb
                    JOIN kino.tblbillett b ON pb.pb_billettkode = b.b_billettkode
                    WHERE pb.pb_radnr = p.p_radnr
                    AND pb.pb_setenr = p.p_setenr
                    AND pb.pb_kinosalnr = p.p_kinosalnr
                    AND b.b_visningsnr = v.v_visningnr
                )
                ORDER BY p.p_radnr, p.p_setenr
                """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visningnr);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rad = rs.getInt("p_radnr");
                int sete = rs.getInt("p_setenr");
                int sal = rs.getInt("p_kinosalnr");
                ledige.add(new Plass(rad, sete, sal));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ledige;
    }
}
