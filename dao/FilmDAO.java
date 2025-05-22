package dao;

import model.Film;
import util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    //  Registrer ny film i databasen
    public boolean registrerFilm(String tittel, String sjanger, int lengde) {
        String sql = "INSERT INTO kino.tblfilm (f_filmnavn, f_sjanger, f_lengde) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tittel);
            stmt.setString(2, sjanger);
            stmt.setInt(3, lengde);

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Feil ved registrering av film: " + e.getMessage());
        }
        return false;
    }

    //  Henter alle filmer fra databasen
    public List<Film> hentAlleFilmer() {
        List<Film> filmer = new ArrayList<>();
        String sql = "SELECT f_filmnr, f_filmnavn FROM kino.tblfilm";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int filmnr = rs.getInt("f_filmnr");
                String navn = rs.getString("f_filmnavn");
                filmer.add(new Film(filmnr, navn));
            }

        } catch (SQLException e) {
            System.err.println("Feil ved henting av filmer: " + e.getMessage());
        }

        return filmer;
    }

    //  Hent én film basert på filmnummer
    public Film hentFilm(int filmnr) {
        String sql = "SELECT f_filmnr, f_filmnavn FROM kino.tblfilm WHERE f_filmnr = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filmnr);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Film(
                            rs.getInt("f_filmnr"),
                            rs.getString("f_filmnavn")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Feil ved henting av film: " + e.getMessage());
        }

        return null;
    }
}
