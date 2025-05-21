package dao;

import util.Database;
import model.Visning;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VisningDAO {

    /* Hent alle visninger – brukes til oversikts­listing */
    public List<Visning> hentAlleVisninger() {
        List<Visning> visninger = new ArrayList<>();

        String sql = "SELECT v_visningnr, v_dato, v_starttid, v_filmnr, " +
                "       v_kinosalnr, v_pris " +
                "FROM kino.tblvisning " +
                "ORDER BY v_dato, v_starttid";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int nr        = rs.getInt("v_visningnr");
                LocalDate dato= rs.getDate("v_dato").toLocalDate();
                LocalTime tid = rs.getTime("v_starttid").toLocalTime();
                int filmnr    = rs.getInt("v_filmnr");
                int salnr     = rs.getInt("v_kinosalnr");
                double pris   = rs.getDouble("v_pris");

                visninger.add(new Visning(nr, filmnr, salnr, dato, tid, pris));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visninger;
    }

    /* 🔍 Hent én spesifikk visning etter primærnøkkel */
    public Visning hentVisning(int visningnr) {
        String sql = "SELECT v_visningnr, v_dato, v_starttid, v_filmnr, " +
                "       v_kinosalnr, v_pris " +
                "FROM kino.tblvisning " +
                "WHERE v_visningnr = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visningnr);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dato  = rs.getDate("v_dato").toLocalDate();
                    LocalTime tid   = rs.getTime("v_starttid").toLocalTime();
                    int filmnr      = rs.getInt("v_filmnr");
                    int salnr       = rs.getInt("v_kinosalnr");
                    double pris     = rs.getDouble("v_pris");

                    return new Visning(visningnr, filmnr, salnr, dato, tid, pris);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // ikke funnet
    }

    public boolean opprettVisning(int filmId, int salNr, String tidspunkt) {
        return false;
    }

    public boolean harSolgteBilletter(int visningId) {
        return false;
    }

    public void endreTidspunkt(int visningId, String nyttTidspunkt) {
    }

    public void slettVisning(int visningId) {
    }
}
