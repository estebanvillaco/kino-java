package dao;

import util.Database;
import model.Billett;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillettDAO {

    // Opprett ny billett
    public boolean opprettBillett(Billett billett) {
        String sql = "INSERT INTO kino.tblbillett (b_billettkode, b_visningsnr, b_erBetalt) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billett.getBillettkode());
            stmt.setInt(2, billett.getVisningsnr());
            stmt.setBoolean(3, billett.isErBetalt());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // (Valgfritt) Hent en billett ved kode
    public Billett hentBillett(String billettkode) {
        String sql = "SELECT b_billettkode, b_visningsnr, b_erBetalt FROM kino.tblbillett WHERE b_billettkode = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billettkode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Billett(
                            rs.getString("b_billettkode"),
                            rs.getInt("b_visningsnr"),
                            rs.getBoolean("b_erBetalt")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // (Valgfritt) Sett en billett som betalt
    public boolean settBetalt(String billettkode) {
        String sql = "UPDATE kino.tblbillett SET b_erBetalt = true WHERE b_billettkode = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billettkode);

            int updated = stmt.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Billett> hentUbetalteBilletterForVisning(int visningsId) {
        List<Billett> billetter = new ArrayList<>();

        String sql = "SELECT * FROM kino.tblbillett WHERE b_visningsnr = ? AND b_erbetalt = false";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visningsId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Billett b = new Billett(
                        rs.getString("b_billettkode"),
                        rs.getInt("b_visningsnr"),
                        rs.getBoolean("b_erbetalt")
                );
                billetter.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billetter;
    }
    public boolean registrerBetaling(String billettkode) {
        String sql = "UPDATE kino.tblbillett SET b_erbetalt = true WHERE b_billettkode = ? AND b_erbetalt = false";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billettkode);
            int rader = stmt.executeUpdate();

            return rader > 0; // true hvis én eller flere rader ble oppdatert

        } catch (SQLException e) {
            System.out.println("Feil ved registrering av betaling: " + e.getMessage());
            return false;
        }
    }
    public boolean opprettOgBetalBillett(String billettkode, int visningsnr) {
        String sql = "INSERT INTO kino.tblbillett (b_billettkode, b_visningsnr, b_erBetalt) VALUES (?, ?, true)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billettkode);
            stmt.setInt(2, visningsnr);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void slettBillettOgPlasser(String billettkode) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            // Slett fra tblPlassBillett først pga. FK-avhengighet
            try (PreparedStatement stmt1 = conn.prepareStatement(
                    "DELETE FROM kino.tblplassbillett WHERE pb_billettkode = ?")) {
                stmt1.setString(1, billettkode);
                stmt1.executeUpdate();
            }

            // Slett billett
            try (PreparedStatement stmt2 = conn.prepareStatement(
                    "DELETE FROM kino.tblbillett WHERE b_billettkode = ?")) {
                stmt2.setString(1, billettkode);
                stmt2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
