package app;

import controller.BetjentController;
import dao.BillettDAO;
import dao.PlassBillettDAO;
import service.BestillingService;

import java.util.Scanner;
import java.util.UUID;

public class BetjentMeny {

    public static void start(Scanner scanner) {
        BillettDAO billettDAO = new BillettDAO();
        BetjentController betjentController = new BetjentController();
        PlassBillettDAO plassDAO = new PlassBillettDAO();
        BestillingService service = new BestillingService();

        System.out.println("🎟 Velkommen til Kinobetjent-modul");

        int valg;
        do {
            System.out.println("\n=== Betjentmeny ===");
            System.out.println("1. Registrer betaling for billett");
            System.out.println("2. Slett ubetalte bestillinger for visning");
            System.out.println("3. Selg billett (betalt med én gang)");
            System.out.println("0. Logg ut");
            System.out.print("Velg et alternativ: ");
            while (!scanner.hasNextInt()) {
                System.out.println("❌ Ugyldig inndata. Prøv igjen.");
                scanner.next(); // kast feil input
                System.out.print("Velg et alternativ: ");
            }
            valg = scanner.nextInt();
            scanner.nextLine(); // rydder buffer

            switch (valg) {
                case 1 -> {
                    System.out.print("Oppgi billettkode: ");
                    String kode = scanner.nextLine();
                    boolean ok = billettDAO.registrerBetaling(kode);
                    if (ok) {
                        System.out.println("✅ Betaling registrert.");
                    } else {
                        System.out.println("❌ Kunne ikke registrere betaling. Sjekk billettkode.");
                    }
                }
                case 2 -> {
                    System.out.print("Oppgi visningsID: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("❌ Må være et tall. Prøv igjen.");
                        scanner.next();
                        System.out.print("Oppgi visningsID: ");
                    }
                    int visningId = scanner.nextInt();
                    scanner.nextLine();
                    betjentController.slettUbetalteOgLogg(visningId);
                }
                case 3 -> {
                    System.out.println("💳 Starter direktesalg...");
                    service.startBestilling(scanner, true); // true = betal nå
                }
                case 0 -> System.out.println("🔓 Logger ut...");
                default -> System.out.println("❌ Ugyldig valg. Prøv igjen.");
            }

        } while (valg != 0);

        // Ikke lukk scanner – den eies av HovedMeny
    }

    // Ikke brukt i menyen per nå – men beholdes for mulig utvidelse
    private static String genererKode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
