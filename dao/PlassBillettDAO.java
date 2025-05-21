package dao;

import util.Database;
import model.PlassBillett;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlassBillettDAO {

    // Knytter plass til billett via objekt
    public boolean leggTilPlassBillett(PlassBillett pb) {
        String sql = "INSERT INTO kino.tblplassbillett " +
                "(pb_radnr, pb_setenr, pb_kinosalnr, pb_billettkode) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pb.getRadnr());
            stmt.setInt(2, pb.getSetenr());
            stmt.setInt(3, pb.getKinosalnr());
            stmt.setString(4, pb.getBillettkode());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Alternativ metode for enkel input
    public boolean koblePlassTilBillett(int rad, int sete, int kinosalnr, String billettkode) {
        String sql = "INSERT INTO kino.tblplassbillett " +
                "(pb_radnr, pb_setenr, pb_kinosalnr, pb_billettkode) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rad);
            stmt.setInt(2, sete);
            stmt.setInt(3, kinosalnr);
            stmt.setString(4, billettkode);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
