package dao;

import util.Database;
import model.Visning;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VisningDAO {

    //  Hent alle visninger
    public List<Visning> hentAlleVisninger() {
        List<Visning> visninger = new ArrayList<>();
        String sql = "SELECT v_visningnr, v_dato, v_starttid, v_filmnr, " +
                "v_kinosalnr, v_pris FROM kino.tblvisning ORDER BY v_dato, v_starttid";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int nr = rs.getInt("v_visningnr");
                LocalDate dato = rs.getDate("v_dato").toLocalDate();
                LocalTime tid = rs.getTime("v_starttid").toLocalTime();
                int filmnr = rs.getInt("v_filmnr");
                int salnr = rs.getInt("v_kinosalnr");
                double pris = rs.getDouble("v_pris");

                visninger.add(new Visning(nr, filmnr, salnr, dato, tid, pris));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visninger;
    }

    //  Hent én spesifikk visning
    public Visning hentVisning(int visningnr) {
        String sql = "SELECT v_visningnr, v_dato, v_starttid, v_filmnr, " +
                "v_kinosalnr, v_pris FROM kino.tblvisning WHERE v_visningnr = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visningnr);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dato = rs.getDate("v_dato").toLocalDate();
                    LocalTime tid = rs.getTime("v_starttid").toLocalTime();
                    int filmnr = rs.getInt("v_filmnr");
                    int salnr = rs.getInt("v_kinosalnr");
                    double pris = rs.getDouble("v_pris");

                    return new Visning(visningnr, filmnr, salnr, dato, tid, pris);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  Opprett visning
    public boolean opprettVisning(int filmId, int salNr, String dato, String tid, int pris) {
        String sql = "INSERT INTO kino.tblvisning (v_filmnr, v_kinosalnr, v_dato, v_starttid, v_pris) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filmId);
            stmt.setInt(2, salNr);
            stmt.setDate(3, Date.valueOf(dato)); // yyyy-MM-dd
            stmt.setTime(4, Time.valueOf(tid));  // HH:mm:ss
            stmt.setInt(5, pris);

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Feil ved oppretting av visning: " + e.getMessage());
        }

        return false;
    }

    // Sjekk om visning har solgte billetter
    public boolean harSolgteBilletter(int visningId) {
        String sql = "SELECT COUNT(*) FROM kino.tblbillett WHERE b_visningnr = ? AND b_betalt = true";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visningId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true; // safer fallback
    }

    // Endre tidspunkt
    public void endreTidspunkt(int visningId, String nyttTidspunkt) {
        String sql = "UPDATE kino.tblvisning SET v_starttid = ? WHERE v_visningnr = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTime(1, Time.valueOf(nyttTidspunkt)); // HH:mm:ss
            stmt.setInt(2, visningId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(" Feil ved oppdatering: " + e.getMessage());
        }
    }

    // Slett visning
    public void slettVisning(int visningId) {
        String sql = "DELETE FROM kino.tblvisning WHERE v_visningnr = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visningId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(" Feil ved sletting: " + e.getMessage());
        }
    }
}
