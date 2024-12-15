package nl.hu.dp;

import nl.hu.dp.domein.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?";
        String user = "postgres";
        String password = "geheim";

        try {
            Connection db = DriverManager.getConnection(url, user, password);
            System.out.println("Verbinding met database is succesvol");

            testp1(db);

            ReizigerDAOsql reizigerDAO = new ReizigerDAOsql(db);
//            testReizigerDAO(reizigerDAO);
//
//            AdresDAOPsql adresDAOPsql = new AdresDAOPsql(db);
//            testAdresDAO(adresDAOPsql, reizigerDAO);

            OVChipkaartDAOPsql ovChipkaartDAOPsql = new OVChipkaartDAOPsql(db);
            testOVChipkaartDAO(ovChipkaartDAOPsql, reizigerDAO);

            db.close();
        } catch (Exception e) {
            System.out.println("Er is een fout opgetreden in de Main: " + e.getMessage());
        }


    }

    private static void testp1(Connection connection) throws SQLException {
        System.out.println("\n---------- Test p1 -------------");

        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT * FROM reiziger";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                String geboortedatum = rs.getString("geboortedatum").toString();
                if (tussenvoegsel == null) {
                    tussenvoegsel = "";
                } else {
                    tussenvoegsel = tussenvoegsel + " ";
                }

                System.out.println("#" + id + ": " + voorletters + ". " + tussenvoegsel + "" + achternaam + " (" + geboortedatum + ")");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Er is een fout opgetreden: " + e.getMessage());
        }

    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum).toLocalDate());
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //test findById
        System.out.println("[Test] ReizigerDAO.findById(77) geeft de volgende reiziger: ");
        Reiziger gevondenReizigerById = rdao.findById(77);
        if (gevondenReizigerById != null) {
            System.out.println(gevondenReizigerById + "\n");
        } else {
            System.out.println("Geen reiziger met id 1\n");
        }

        //test Delete
        System.out.println("[Test] ReizigerDAO.delete() verwijderd reiziger Sietske");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.print("[Test] Na ReizigerDAO.delete() zijn er nog " + reizigers.size() + " reizigers\n");

        //test of sietske echt is verwijderd
        Reiziger verwijderd = rdao.findById(77);
        if (verwijderd == null) {
            System.out.println("[Test] Sietske is van de aardbodem verdwenen");
        } else {
            System.out.println("[Test] verdorrie er is iets fout want Sietske is er nog");
        }
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Maak een nieuwe reiziger aan en persisteer deze in de database voor Adres
        Reiziger Valentijn = rdao.findById(69);
        if (Valentijn == null) {
            String gbdatumV = "2003-09-26";
            Valentijn = new Reiziger(69, "V", "", "Tollenaar", Date.valueOf(gbdatumV).toLocalDate());
            rdao.save(Valentijn);
            System.out.println("Reiziger Valentijn aangemaakt\n");
        }

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres adresNieuw = new Adres(77, "1234AB", "69", "Zeisterweg", "Zeist", Valentijn);
        adao.save(adresNieuw);
        System.out.println("[Test] nieuw adres aangemaakt en gekoppeld aan " + rdao.findById(Valentijn.getid()).getVoorletters() + " " + rdao.findById(Valentijn.getid()).getAchternaam() + ": " + adao.findById(adresNieuw.getId()));
        System.out.println();

        //test update
        adresNieuw.setHuisnummer("420");
        adresNieuw.setStraat("Utrechtseweg");
        adresNieuw.setWoonplaats("Utrecht");
        adao.update(adresNieuw);
        System.out.println("[Test] Adres aangepast " + adao.findById(adresNieuw.getId()));
        System.out.println();

        //test delete
        System.out.println("[Test] AdresDAO.delete() verwijderd adres adresNieuw");
        adao.delete(adresNieuw);
        Adres testAdresDelete = adao.findById(adresNieuw.getId());
        if (testAdresDelete == null) {
            System.out.println("Adres succesvol verwijderd");
        } else {
            System.out.println("Adres bestaat nog: " + adao.findById(77));
        }
        System.out.println();

        //test findByReiziger
        Adres testFindByReiziger = adao.findByReiziger(rdao.findById(5));
        System.out.println("[Test] findByReiziger op basis van reizigerId: " + testFindByReiziger);
        System.out.println();

        //test AdresDAOPsql.findAll
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        Reiziger Valentijn = rdao.findById(69);

        //test Save
        String geldigTot = "2021-12-12";
        OVChipkaart ovChipkaartNieuw = new OVChipkaart(8, Date.valueOf(geldigTot).toLocalDate(), 2, 69, Valentijn);
        odao.save(ovChipkaartNieuw);
        System.out.println("[Test] nieuwe OvChipkaart aangemaakt en gekoppeld aan reiziger met id: " + ovChipkaartNieuw.getReiziger().getid());
        System.out.println("[Test] " + ovChipkaartNieuw);
        System.out.println();

        //test update
        ovChipkaartNieuw.setKlasse(1);
        ovChipkaartNieuw.setSaldo(420);
        ovChipkaartNieuw.setGeldig_tot(Date.valueOf("2020-12-12").toLocalDate());
        odao.update(ovChipkaartNieuw);
        System.out.println("[Test] OVChipkaart aangepast " + ovChipkaartNieuw);
        System.out.println();

        //test delete
        System.out.println("[Test] OVChipkaartDAO.delete() verwijderd OVChipkaart ovChipkaartNieuw");
        odao.delete(ovChipkaartNieuw);
        OVChipkaart testOVChipkaartDelete = odao.findById(ovChipkaartNieuw.getKaart_nummer());
        if (testOVChipkaartDelete == null) {
            System.out.println("OVChipkaart succesvol verwijderd");
        } else {
            System.out.println("OVChipkaart bestaat nog: " + odao.findById(8));
        }
        System.out.println();

        //test findByReiziger
        int reiziger_id = 2;
        List<OVChipkaart> testFindByReiziger = odao.findByReiziger(rdao.findById(reiziger_id));
        System.out.println("[Test] findByReiziger op basis van reizigerId " + rdao.findById(reiziger_id).getid() + ": ");
        for (OVChipkaart o : testFindByReiziger) {
            System.out.println(o);
        }
        System.out.println();

        //test attribuut in reiziger
        OVChipkaart ovChipkaartNieuw2 = new OVChipkaart(8, Date.valueOf(geldigTot).toLocalDate(), 2, 69, Valentijn);
        System.out.println(ovChipkaartNieuw2);
        Valentijn.voegOVChipkaartToe(ovChipkaartNieuw2);
        System.out.println(Valentijn.getOvChipkaarten());
        System.out.println();

        //test attribuut in OVChipkaart
        ovChipkaartNieuw2.setReiziger(Valentijn);
        ovChipkaartNieuw2.setReiziger(Valentijn);
        System.out.println(Valentijn.getOvChipkaarten());
    }
}
