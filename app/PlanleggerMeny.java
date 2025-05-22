package app;

import dao.LoginDAO;
import service.FilmService;
import service.VisningService;
import service.StatistikkService;

import java.util.Scanner;

public class PlanleggerMeny {

    public static void start(Scanner scanner) {
        FilmService filmService = new FilmService();
        VisningService visningService = new VisningService();
        StatistikkService statistikkService = new StatistikkService();
        LoginDAO loginDAO = new LoginDAO();  // Nytt

        int valg;
        do {
            System.out.println("\n=== PLANLEGGER-MENY ===");
            System.out.println("1. Registrer ny film");
            System.out.println("2. Opprett ny visning");
            System.out.println("3. Endre eller slett visning");
            System.out.println("4. Generer statistikk for film");
            System.out.println("5. Generer statistikk for kinosal");
            System.out.println("6. Opprett ny bruker");
            System.out.println("7. Slett bruker");
            System.out.println("0. Logg ut");
            System.out.print("Velg et alternativ: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Skriv inn et tall.");
                scanner.next();
            }
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
                    System.out.println(suksess ? "Film registrert." : " Kunne ikke registrere film.");
                }

                case 2 -> {
                    System.out.print("Film-ID: ");
                    int filmId = scanner.nextInt();
                    System.out.print("Kinosalnummer: ");
                    int salNr = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Dato (yyyy-MM-dd): ");
                    String dato = scanner.nextLine();
                    System.out.print("Starttid (HH:mm:ss): ");
                    String tid = scanner.nextLine();
                    System.out.print("Pris: ");
                    int pris = scanner.nextInt();
                    scanner.nextLine();

                    boolean suksess = visningService.opprettVisning(filmId, salNr, dato, tid, pris);
                    System.out.println(suksess ? "Visning opprettet." : " Feil ved oppretting.");
                }

                case 3 -> {
                    System.out.print("Visnings-ID: ");
                    int visningId = scanner.nextInt();
                    scanner.nextLine();

                    if (!visningService.harSolgteBilletter(visningId)) {
                        System.out.println("1. Endre visningstidspunkt");
                        System.out.println("2. Slett visning");
                        System.out.print("Velg: ");
                        int valgEndre = scanner.nextInt();
                        scanner.nextLine();

                        if (valgEndre == 1) {
                            System.out.print("Nytt starttidspunkt (HH:mm:ss): ");
                            String nyttTid = scanner.nextLine();
                            visningService.endreTidspunkt(visningId, nyttTid);
                            System.out.println("Tidspunkt oppdatert.");
                        } else if (valgEndre == 2) {
                            visningService.slettVisning(visningId);
                            System.out.println("Visning slettet.");
                        } else {
                            System.out.println("Ugyldig valg.");
                        }
                    } else {
                        System.out.println("Kan ikke endre/slette – det er allerede solgte billetter.");
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

                case 6 -> {
                    System.out.print("Brukernavn: ");
                    String nyBruker = scanner.nextLine();
                    System.out.print("PIN-kode: ");
                    String nyPin = scanner.nextLine();
                    System.out.print("Rolle (betjent/planlegger): ");
                    String rolle = scanner.nextLine().toLowerCase();

                    if (rolle.equals("betjent") || rolle.equals("planlegger")) {
                        boolean opprettet = loginDAO.opprettBruker(nyBruker, nyPin, rolle);
                        System.out.println(opprettet ? "Bruker opprettet." : "Kunne ikke opprette bruker.");
                    } else {
                        System.out.println("Ugyldig rolle. Må være 'betjent' eller 'planlegger'.");
                    }
                }

                case 7 -> {
                    System.out.print("Brukernavn som skal slettes: ");
                    String slett = scanner.nextLine();
                    boolean slettet = loginDAO.slettBruker(slett);
                    System.out.println(slettet ? " Bruker slettet." : " Kunne ikke slette bruker.");
                }

                case 0 -> System.out.println("Logger ut...");
                default -> System.out.println("Ugyldig valg.");
            }

        } while (valg != 0);
    }
}
