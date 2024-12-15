package nl.hu.dp.domein;

import java.time.LocalDate;


public class OVChipkaart {
    private int kaart_nummer;
    private LocalDate geldig_tot;
    private int klasse;
    private int saldo;
    private Reiziger reiziger;

    public OVChipkaart(){

    }

    public OVChipkaart(int kaart_nummer, LocalDate geldig_tot, int klasse, int saldo, Reiziger reiziger){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public LocalDate getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(LocalDate geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public String getVolledigeNaam(){
        String tussenvoegsel = "";
        if (getReiziger().getTussenvoegsel() != null){
            tussenvoegsel = getReiziger().getTussenvoegsel();
        }
        return getReiziger().getVoorletters() + " " + tussenvoegsel + " " + getReiziger().getAchternaam();
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
        if (!reiziger.getOvChipkaarten().contains(this)) {
            reiziger.voegOVChipkaartToe(this);
        }
    }

    public String toString(){
        return "{nummer: "+ kaart_nummer + " is geldig tot " + geldig_tot + ". Klasse " + klasse + ". Naam: " + getVolledigeNaam() + ". Heeft een saldo van: €" + saldo + ",-}";
    }
}
