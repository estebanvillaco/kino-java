package app;

import dao.LoginDAO;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginDAO loginDAO = new LoginDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== KINO SYSTEM ===");
            System.out.println("1. Logg inn");
            System.out.println("2. Opprett ny bruker");
            System.out.println("3. Endre PIN-kode");
            System.out.println("0. Avslutt");
            System.out.print("Velg et alternativ: ");
            String valg = scanner.nextLine();

            switch (valg) {
                case "1" -> {
                    System.out.print("Brukernavn: ");
                    String brukernavn = scanner.nextLine();
                    System.out.print("PIN-kode: ");
                    String pin = scanner.nextLine();

                    if (loginDAO.login(brukernavn, pin)) {
                        System.out.println("Innlogging vellykket!");
                        String rolle = loginDAO.hentRolle(brukernavn);
                        System.out.println("Innlogget som: " + rolle);
                        // Her kan du sende videre til riktig meny, f.eks. HovedMeny.main()
                    } else {
                        System.out.println("Feil brukernavn eller PIN.");
                    }
                }

                case "2" -> {
                    System.out.print("Nytt brukernavn: ");
                    String nyBruker = scanner.nextLine();
                    System.out.print("PIN-kode: ");
                    String nyPin = scanner.nextLine();
                    System.out.print("Er planlegger? (true/false): ");
                    boolean erPlanlegger = Boolean.parseBoolean(scanner.nextLine());
                    String rolle = erPlanlegger ? "planlegger" : "betjent";

                    if (loginDAO.opprettBruker(nyBruker, nyPin, rolle)) {
                        System.out.println("Bruker opprettet som " + rolle + ".");
                    } else {
                        System.out.println("Kunne ikke opprette bruker.");
                    }
                }

                case "3" -> {
                    System.out.println("\n== Endre PIN-kode ==");
                    System.out.print("Brukernavn: ");
                    String user = scanner.nextLine();
                    System.out.print("Gammel PIN: ");
                    String gammelPin = scanner.nextLine();

                    if (loginDAO.login(user, gammelPin)) {
                        System.out.print("Ny PIN: ");
                        String nyPin = scanner.nextLine();
                        if (loginDAO.oppdaterPin(user, nyPin)) {
                            System.out.println(" PIN-kode oppdatert.");
                        } else {
                            System.out.println("Kunne ikke oppdatere PIN.");
                        }
                    } else {
                        System.out.println("Feil brukernavn eller gammel PIN.");
                    }
                }

                case "0" -> {
                    System.out.println("Avslutter programmet...");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Ugyldig valg, prøv igjen.");
            }
        }
    }
}
