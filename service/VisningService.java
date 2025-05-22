package service;

import dao.VisningDAO;

public class VisningService {

    private final VisningDAO visningDAO = new VisningDAO();

    public boolean opprettVisning(int filmId, int salNr, String dato, String tid, int pris) {
        return visningDAO.opprettVisning(filmId, salNr, dato, tid, pris);
    }

    public boolean harSolgteBilletter(int visningId) {
        return visningDAO.harSolgteBilletter(visningId);
    }

    public void endreTidspunkt(int visningId, String nyttTidspunkt) {
        visningDAO.endreTidspunkt(visningId, nyttTidspunkt);
    }

    public void slettVisning(int visningId) {
        visningDAO.slettVisning(visningId);
    }
}
