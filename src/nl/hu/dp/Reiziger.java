package nl.hu.dp;

import java.time.LocalDate;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum){
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
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

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String toString(){
        if(tussenvoegsel == null){
            tussenvoegsel = "";
        } else {
            tussenvoegsel += " ";
        }
        String string = "#"+reiziger_id+": "+voorletters+". "+tussenvoegsel+achternaam+" ("+geboortedatum+")";
        return string;
    }
}
