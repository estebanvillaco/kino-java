package dao;

import util.Database;
import util.HashUtil;

import java.sql.*;

public class LoginDAO {

    // Brukerinnlogging
    public boolean login(String brukernavn, String pin) {
        String sql = "SELECT l_pinkode FROM kino.tblLogin WHERE l_brukernavn = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, brukernavn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashed = rs.getString("l_pinkode");
                return HashUtil.verifyHash(pin, hashed);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Endre PIN-kode
    public boolean oppdaterPin(String brukernavn, String nyPin) {
        String nyHash = HashUtil.hash(nyPin);
        String sql = "UPDATE kino.tblLogin SET l_pinkode = ? WHERE l_brukernavn = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nyHash);
            stmt.setString(2, brukernavn);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Opprett ny bruker med rolle ("betjent", "planlegger", osv.)
    public boolean opprettBruker(String brukernavn, String pin, String rolle) {
        String hash = HashUtil.hash(pin);
        String sql = "INSERT INTO kino.tblLogin (l_brukernavn, l_pinkode, rolle) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, brukernavn);
            stmt.setString(2, hash);
            stmt.setString(3, rolle);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Feil ved oppretting av bruker: " + e.getMessage());
        }
        return false;
    }

    // Hent rollen til en bruker ("betjent", "planlegger", "admin", ...)
    public String hentRolle(String brukernavn) {
        String sql = "SELECT rolle FROM kino.tblLogin WHERE l_brukernavn = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, brukernavn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("rolle");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
