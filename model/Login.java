package model;

public class Login {
    private String brukernavn;
    private String hashedPin;
    private String rolle;

    public Login(String brukernavn, String hashedPin, String rolle) {
        this.brukernavn = brukernavn;
        this.hashedPin = hashedPin;
        this.rolle = rolle;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public String getHashedPin() {
        return hashedPin;
    }

    public String getRolle() {
        return rolle;
    }
}
