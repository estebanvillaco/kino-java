package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visning {
    private int visningnr;
    private int filmnr;
    private int kinosalnr;
    private LocalDate dato;
    private LocalTime starttid;
    private double pris;

    public Visning(int visningnr, int filmnr, int kinosalnr, LocalDate dato, LocalTime starttid, double pris) {
        this.visningnr = visningnr;
        this.filmnr = filmnr;
        this.kinosalnr = kinosalnr;
        this.dato = dato;
        this.starttid = starttid;
        this.pris = pris;
    }

    public int getVisningnr() {
        return visningnr;
    }

    public int getFilmnr() {
        return filmnr;
    }

    public int getKinosalnr() {
        return kinosalnr;
    }

    public LocalDate getDato() {
        return dato;
    }

    public LocalTime getStarttid() {
        return starttid;
    }

    public double getPris() {
        return pris;
    }

    @Override
    public String toString() {
        return "Visning{" +
                "visningnr=" + visningnr +
                ", filmnr=" + filmnr +
                ", kinosalnr=" + kinosalnr +
                ", dato=" + dato +
                ", starttid=" + starttid +
                ", pris=" + pris +
                '}';
    }
}
