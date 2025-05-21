package app;


import service.FilmService;
import service.VisningService;
import service.StatistikkService;

import java.util.Scanner;

public class PlanleggerMeny {

    public static void start(Scanner scanner) {
        FilmService filmService = new FilmService();
        VisningService visningService = new VisningService();
        StatistikkService statistikkService = new StatistikkService();

        int valg;
        do {
            System.out.println("\n=== PLANLEGGER-MENY ===");
            System.out.println("1. Registrer ny film");
            System.out.println("2. Opprett ny visning");
            System.out.println("3. Endre eller slett visning");
            System.out.println("4. Generer statistikk for film");
            System.out.println("5. Generer statistikk for kinosal");
            System.out.println("0. Logg ut");
            System.out.print("Velg et alternativ: ");
            valg = scanner.nextInt();
            scanner.nextLine(); // rydder buffer

            switch (valg) {
                case 1 -> {
                    System.out.print("Tittel på film: ");
                    String tittel = scanner.nextLine();
                    System.out.print("Sjanger: ");
                    String sjanger = scanner.nextLine();
                    System.out.print("Lengde i minutter: ");
                    int lengde = scanner.nextInt();
                    scanner.nextLine();

                    boolean suksess = filmService.registrerFilm(tittel, sjanger, lengde);
                    System.out.println(suksess ? "✅ Film registrert." : "❌ Kunne ikke registrere film.");
                }

                case 2 -> {
                    System.out.print("Film-ID: ");
                    int filmId = scanner.nextInt();
                    System.out.print("Kinosalnummer: ");
                    int salNr = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Starttidspunkt (yyyy-MM-dd HH:mm): ");
                    String tidspunkt = scanner.nextLine();

                    boolean suksess = visningService.opprettVisning(filmId, salNr, tidspunkt);
                    System.out.println(suksess ? "✅ Visning opprettet." : "❌ Feil ved oppretting.");
                }

                case 3 -> {
                    System.out.print("Visnings-ID: ");
                    int visningId = scanner.nextInt();
                    scanner.nextLine();

                    if (!visningService.harSolgteBilletter(visningId)) {
                        System.out.println("1. Endre visning");
                        System.out.println("2. Slett visning");
                        System.out.print("Velg: ");
                        int valgEndre = scanner.nextInt();
                        scanner.nextLine();

                        if (valgEndre == 1) {
                            System.out.print("Nytt tidspunkt (yyyy-MM-dd HH:mm): ");
                            String nyttTid = scanner.nextLine();
                            visningService.endreTidspunkt(visningId, nyttTid);
                            System.out.println("✅ Tidspunkt oppdatert.");
                        } else if (valgEndre == 2) {
                            visningService.slettVisning(visningId);
                            System.out.println("✅ Visning slettet.");
                        } else {
                            System.out.println("❌ Ugyldig valg.");
                        }
                    } else {
                        System.out.println("❌ Kan ikke endre/slette – det er allerede solgte billetter.");
                    }
                }

                case 4 -> {
                    System.out.print("Film-ID: ");
                    int filmId = scanner.nextInt();
                    scanner.nextLine();
                    statistikkService.genererFilmStatistikk(filmId);
                }

                case 5 -> {
                    System.out.print("Kinosalnummer: ");
                    int salNr = scanner.nextInt();
                    scanner.nextLine();
                    statistikkService.genererSalsStatistikk(salNr);
                }

                case 0 -> System.out.println("Logger ut...");
                default -> System.out.println("Ugyldig valg.");
            }

        } while (valg != 0);
    }
}
