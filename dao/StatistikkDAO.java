package dao;


import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatistikkDAO {

    public void filmStatistikk(int filmId) {
        String sql = """
            SELECT COUNT(*) AS besok,
                   SUM(CASE WHEN b_betalt = 1 THEN 1 ELSE 0 END) AS solgte
            FROM tblBillett
            JOIN tblVisning ON b_visningsid = v_id
            WHERE v_flmnr = ?
        """;
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Besøkende: " + rs.getInt("besok"));
                System.out.println("Solgte billetter: " + rs.getInt("solgte"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salStatistikk(int salNr) {
        String sql = """
            SELECT AVG(salg.antall) as snitt
            FROM (
                SELECT COUNT(*) as antall
                FROM tblBillett b
                JOIN tblVisning v ON b.b_visningsid = v.v_id
                WHERE v.v_kinosalnr = ?
                GROUP BY v.v_id
            ) as salg
        """;
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salNr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Snitt solgte billetter per visning: " + rs.getDouble("snitt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

