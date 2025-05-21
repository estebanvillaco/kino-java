import dao.LoginDAO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginDAO loginDAO = new LoginDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== KINO SYSTEM ===");
            System.out.println("1. Logg inn");
            System.out.println("2. Endre PIN-kode");
            System.out.println("3. Avslutt");
            System.out.println("4. Opprett ny bruker");
            System.out.print("Velg et alternativ: ");
            String valg = scanner.nextLine();

            switch (valg) {
                case "1":
                    System.out.print("Brukernavn: ");
                    String brukernavn = scanner.nextLine();
                    System.out.print("PIN-kode: ");
                    String pin = scanner.nextLine();

                    if (loginDAO.login(brukernavn, pin)) {
                        System.out.println("✅ Innlogging vellykket!");
                        String rolle = loginDAO.hentRolle(brukernavn);
                        System.out.println("Innlogget som: " + rolle);
                        // Her kan du legge til betjent/planlegger-meny basert på rollen
                    } else {
                        System.out.println("❌ Feil brukernavn eller PIN.");
                    }
                    break;

                case "2":
                    System.out.print("Brukernavn: ");
                    String user = scanner.nextLine();
                    System.out.print("Gammel PIN: ");
                    String gammelPin = scanner.nextLine();

                    if (loginDAO.login(user, gammelPin)) {
                        System.out.print("Ny PIN: ");
                        String nyPin = scanner.nextLine();
                        if (loginDAO.oppdaterPin(user, nyPin)) {
                            System.out.println("✅ PIN-kode oppdatert.");
                        } else {
                            System.out.println("❌ Kunne ikke oppdatere PIN.");
                        }
                    } else {
                        System.out.println("❌ Feil brukernavn eller gammel PIN.");
                    }
                    break;

                case "3":
                    System.out.println("Avslutter programmet...");
                    scanner.close();
                    return;

                case "4":
                    System.out.print("Nytt brukernavn: ");
                    String nyBruker = scanner.nextLine();
                    System.out.print("PIN-kode: ");
                    String nyPin = scanner.nextLine();
                    System.out.print("Er planlegger? (true/false): ");
                    boolean erPlanlegger = Boolean.parseBoolean(scanner.nextLine());
                    String rolle = erPlanlegger ? "planlegger" : "betjent";

                    if (loginDAO.opprettBruker(nyBruker, nyPin, rolle)) {
                        System.out.println("✅ Bruker opprettet som " + rolle + ".");
                    } else {
                        System.out.println("❌ Kunne ikke opprette bruker.");
                    }
                    break;

                default:
                    System.out.println("Ugyldig valg, prøv igjen.");
            }
        }
    }
}
