package model;

public class Plass {
    private int radnr;
    private int setenr;
    private int kinosalnr;

    public Plass(int radnr, int setenr, int kinosalnr) {
        this.radnr = radnr;
        this.setenr = setenr;
        this.kinosalnr = kinosalnr;
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

    @Override
    public String toString() {
        return "Plass{" +
                "radnr=" + radnr +
                ", setenr=" + setenr +
                ", kinosalnr=" + kinosalnr +
                '}';
    }
}
