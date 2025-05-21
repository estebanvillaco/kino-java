package service;

import dao.StatistikkDAO;

public class StatistikkService {
    private final StatistikkDAO dao = new StatistikkDAO();

    public void genererFilmStatistikk(int filmId) {
        dao.filmStatistikk(filmId);
    }

    public void genererSalsStatistikk(int salNr) {
        dao.salStatistikk(salNr);
    }
}
