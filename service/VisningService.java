package service;


import dao.VisningDAO;

public class VisningService {
    private final VisningDAO dao = new VisningDAO();

    public boolean opprettVisning(int filmId, int salNr, String tidspunkt) {
        return dao.opprettVisning(filmId, salNr, tidspunkt);
    }

    public boolean harSolgteBilletter(int visningId) {
        return dao.harSolgteBilletter(visningId);
    }

    public void endreTidspunkt(int visningId, String nyttTidspunkt) {
        dao.endreTidspunkt(visningId, nyttTidspunkt);
    }

    public void slettVisning(int visningId) {
        dao.slettVisning(visningId);
    }
}

