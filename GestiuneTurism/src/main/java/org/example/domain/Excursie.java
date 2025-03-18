package org.example.domain;

import java.time.LocalDateTime;

public class Excursie extends Entity<Integer> {
    public int nrLocuri, pret;
    public LocalDateTime dataOraPlcare;
    public String obiectiv, firmaTransport;

    public Excursie(String obiectiv, int nrLocuri, int pret, String firmaTransport, LocalDateTime dataOraPlcare) {
        this.dataOraPlcare = dataOraPlcare;
        this.firmaTransport = firmaTransport;
        this.nrLocuri = nrLocuri;
        this.obiectiv = obiectiv;
        this.pret = pret;
    }

    public Excursie(Integer excursieId, String obiectiv, int nrLocuri, int pret, String firmaTransport, LocalDateTime dataOraPlcare) {
        this.setId(excursieId);
        this.dataOraPlcare = dataOraPlcare;
        this.firmaTransport = firmaTransport;
        this.nrLocuri = nrLocuri;
        this.obiectiv = obiectiv;
        this.pret = pret;
    }

    public LocalDateTime getDataOraPlcare() {
        return dataOraPlcare;
    }

    public void setDataOraPlcare(LocalDateTime dataOraPlcare) {
        this.dataOraPlcare = dataOraPlcare;
    }

    public String getFirmaTransport() {
        return firmaTransport;
    }

    public void setFirmaTransport(String firmaTransport) {
        this.firmaTransport = firmaTransport;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public String getObiectiv() {
        return obiectiv;
    }

    public void setObiectiv(String obiectiv) {
        this.obiectiv = obiectiv;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

}
