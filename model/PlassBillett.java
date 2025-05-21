package model;

public class PlassBillett {
    private int radnr;
    private int setenr;
    private int kinosalnr;
    private String billettkode;

    public PlassBillett(int radnr, int setenr, int kinosalnr, String billettkode) {
        this.radnr = radnr;
        this.setenr = setenr;
        this.kinosalnr = kinosalnr;
        this.billettkode = billettkode;
    }

    public int getRadnr() {
        return radnr;
    }

    public int getSetenr() {
        return setenr;
    }

    public int getKinosalnr() {
        return kinosalnr;
    }

    public String getBillettkode() {
        return billettkode;
    }

    @Override
    public String toString() {
        return "PlassBillett{" +
                "radnr=" + radnr +
                ", setenr=" + setenr +
                ", kinosalnr=" + kinosalnr +
                ", billettkode='" + billettkode + '\'' +
                '}';
    }
}
