package app;

import dao.BillettDAO;
import model.Billett;

import java.util.Scanner;

public class BetalingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BillettDAO billettDAO = new BillettDAO();

        System.out.print("Tast inn billettkode for betaling: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        Billett billett = billettDAO.hentBillett(kode);

        if (billett == null) {
            System.out.println("Fant ingen billett med kode: " + kode);
            return;
        }

        if (billett.isErBetalt()) {
            System.out.println("Billetten er allerede betalt.");
            return;
        }

        boolean ok = billettDAO.settBetalt(kode);
        if (ok) {
            System.out.println("Billett betalt. God fornøyelse!");
        } else {
            System.out.println("Noe gikk galt ved betaling.");
        }
    }
}
