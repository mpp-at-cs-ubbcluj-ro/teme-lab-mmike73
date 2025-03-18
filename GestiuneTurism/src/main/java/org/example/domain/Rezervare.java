package org.example.domain;

public class Rezervare extends Entity<Integer> {
    private AgentieTurism agentieTurism;
    public Excursie excursie;
    public String numeClient, numarTelefon;
    public int nrLocuriRezervate;

    public Rezervare(String numeClient, int nrLocuriRezervate, String numarTelefon, Excursie excursie, AgentieTurism agentieTurism) {
        this.excursie = excursie;
        this.nrLocuriRezervate = nrLocuriRezervate;
        this.numarTelefon = numarTelefon;
        this.numeClient = numeClient;
        this.agentieTurism = agentieTurism;
    }

    public Rezervare(Integer id, String numeClient, int nrLocuriRezervate, String numarTelefon, Excursie excursie, AgentieTurism agentieTurism) {
        this.setId(id);
        this.excursie = excursie;
        this.nrLocuriRezervate = nrLocuriRezervate;
        this.numarTelefon = numarTelefon;
        this.numeClient = numeClient;
        this.agentieTurism = agentieTurism;
    }

    public AgentieTurism getAgentieTurism() {
        return agentieTurism;
    }

    public void setAgentieTurism(AgentieTurism agentieTurism) {
        this.agentieTurism = agentieTurism;
    }

    public Excursie getExcursie() {
        return excursie;
    }

    public void setExcursie(Excursie excursie) {
        this.excursie = excursie;
    }

    public int getNrLocuriRezervate() {
        return nrLocuriRezervate;
    }

    public void setNrLocuriRezervate(int nrLocuriRezervate) {
        this.nrLocuriRezervate = nrLocuriRezervate;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }
}
