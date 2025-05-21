import dao.LoginDAO;
import service.BestillingService;
import java.util.Scanner;

public class HovedMeny {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BestillingService bestillingService = new BestillingService();
        LoginDAO loginDAO = new LoginDAO();

        int valg;
        do {
            System.out.println("\n HOVEDMENY - KINOSYSTEM");
            System.out.println("1. Kunde – reserver billett");
            System.out.println("2. Ansatt – administrasjon");
            System.out.println("0. Avslutt");
            System.out.print("Velg et alternativ: ");
            valg = scanner.nextInt();
            scanner.nextLine(); // rydder buffer

            switch (valg) {
                case 1 -> {
                    System.out.println("\n Velkommen, kunde! Du kan nå reservere billetter.");
                    bestillingService.startBestilling(scanner, false);  // false = ikke betalt
                }
                case 2 -> {
                    System.out.println("\n== Innlogging for ansatte ==");
                    System.out.print("Brukernavn: ");
                    String brukernavn = scanner.nextLine();
                    System.out.print("PIN-kode: ");
                    String pin = scanner.nextLine();

                    if (loginDAO.login(brukernavn, pin)) {
                        String rolle = loginDAO.hentRolle(brukernavn);
                        switch (rolle) {
                            case "betjent" -> {
                                System.out.println("✅ Logget inn som betjent.");
                                app.BetjentMeny.start(scanner);
                            }
                            case "planlegger" -> {
                                System.out.println("✅ Logget inn som planlegger.");
                                app.PlanleggerMeny.start(scanner);  // Forutsetter at denne klassen finnes
                            }
                            default -> System.out.println("⚠️ Ugyldig rolle. Tilgang nektes.");
                        }
                    } else {
                        System.out.println("❌ Feil brukernavn eller PIN.");
                    }
                }
                case 0 -> System.out.println("Avslutter programmet...");
                default -> System.out.println("Ugyldig valg. Prøv igjen.");
            }
        } while (valg != 0);

        scanner.close();
    }
}
