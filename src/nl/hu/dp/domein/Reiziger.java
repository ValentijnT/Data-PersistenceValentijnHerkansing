package nl.hu.dp.domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger(){

    }

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum){
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public int getid() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public void voegOVChipkaartToe(OVChipkaart ovChipkaart){
        if (!ovChipkaarten.contains(ovChipkaart)){
            ovChipkaarten.add(ovChipkaart);
            ovChipkaart.setReiziger(this);
        }
    }

    public String toString(){
        if(tussenvoegsel == null){
            tussenvoegsel = "";
        } else {
            tussenvoegsel += " ";
        }

        return "Reiziger {#"+reiziger_id+": "+voorletters+". "+tussenvoegsel+achternaam+", geb. "+geboortedatum + "}";
    }
}
