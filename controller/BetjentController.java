package controller;

import dao.BillettDAO;
import model.Billett;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BetjentController {
    public void slettUbetalteOgLogg(int visningsId) {
        BillettDAO billettDAO = new BillettDAO();
        List<Billett> billetter = billettDAO.hentUbetalteBilletterForVisning(visningsId);

        if (billetter.isEmpty()) {
            System.out.println("Ingen ubetalte billetter å slette for visning " + visningsId);
            return;
        }

        for (Billett b : billetter) {
            billettDAO.slettBillettOgPlasser(b.getBillettkode());
        }

        loggSletting(visningsId, billetter.size());
    }
    private void loggSletting(int visningsId, int antallSlettet) {
        String loggFil = "slettinger.dat";

        try (FileWriter writer = new FileWriter(loggFil, true)) {
            String tid = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            String linje = String.format("VisningID: %d | Slettet %d ubetalte billetter | Tidspunkt: %s%n",
                    visningsId, antallSlettet, tid);
            writer.write(linje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
