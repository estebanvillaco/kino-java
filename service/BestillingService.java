package service;

import dao.*;
import model.*;
import util.BillettKodeGenerator;

import java.util.List;
import java.util.Scanner;

public class BestillingService {

    private final VisningDAO visningDAO = new VisningDAO();
    private final FilmDAO filmDAO = new FilmDAO();
    private final PlassDAO plassDAO = new PlassDAO();
    private final BillettDAO billettDAO = new BillettDAO();
    private final PlassBillettDAO plassBillettDAO = new PlassBillettDAO();

    public void startBestilling(Scanner scanner, boolean betalMedEnGang) {
        System.out.println("Tilgjengelige visninger:");
        List<Visning> visninger = visningDAO.hentAlleVisninger();

        for (Visning v : visninger) {
            Film f = filmDAO.hentFilm(v.getFilmnr());
            System.out.println(v.getVisningnr() + ": " + f.getFilmnavn() + " - " + v.getDato() +
                    " kl " + v.getStarttid() + " i sal " + v.getKinosalnr() + " (" + v.getPris() + " kr)");
        }

        System.out.print("Oppgi visningsID: ");
        int visningsnr = scanner.nextInt();
        scanner.nextLine();

        Visning valgt = visningDAO.hentVisning(visningsnr);

        List<Plass> ledigePlasser = plassDAO.hentLedigePlasser(visningsnr);
        System.out.println("Ledige plasser:");
        for (Plass p : ledigePlasser) {
            System.out.println("Rad: " + p.getRadnr() + "Sete: " + p.getSetenr());
        }

        System.out.print("Hvor mange billetter ønsker du? ");
        int antall = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < antall; i++) {
            System.out.println("Velg plass " + (i + 1));
            System.out.print("Radnummer: ");
            int rad = scanner.nextInt();
            System.out.print("Setenummer: ");
            int sete = scanner.nextInt();
            scanner.nextLine();

            String billettkode = BillettKodeGenerator.genererKode();

            Billett billett = new Billett(billettkode, visningsnr, betalMedEnGang);
            boolean okBillett = billettDAO.opprettBillett(billett);

            PlassBillett pb = new PlassBillett(rad, sete, valgt.getKinosalnr(), billettkode);
            boolean okPlass = plassBillettDAO.leggTilPlassBillett(pb);

            if (okBillett && okPlass) {
                System.out.println("Billett " + billettkode + "opprettet og " +
                        (betalMedEnGang ? "betalt" : "reservert"));
            } else {
                System.out.println("Kunne ikke opprette billett " + billettkode);
            }
        }
    }
}
