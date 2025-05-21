package service;


import dao.FilmDAO;

public class FilmService {
    private final FilmDAO filmDAO = new FilmDAO();

    public boolean registrerFilm(String tittel, String sjanger, int lengde) {
        return filmDAO.registrerFilm(tittel, sjanger, lengde);
    }
}

