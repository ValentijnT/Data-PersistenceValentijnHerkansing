package nl.hu.dp;

import nl.hu.dp.domein.P2.Reiziger;
import nl.hu.dp.domein.P2.ReizigerDAO;
import nl.hu.dp.domein.P2.ReizigerDAOsql;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?";
        String user = "postgres";
        String password = "geheim";

        try{
            Connection db = DriverManager.getConnection(url, user, password);
            System.out.println("Verbinding met database is succesvol");

            testp1(db);

            ReizigerDAOsql reizigerDAO = new ReizigerDAOsql(db);
            testReizigerDAO(reizigerDAO);

            db.close();
        } catch (Exception e){
            System.out.println("Er is een fout opgetreden in de Main: " + e.getMessage());
        }


    }

    private static void testp1(Connection connection) throws SQLException {
        System.out.println("\n---------- Test p1 -------------");

        try{
            Statement stmt = connection.createStatement();

            String query = "SELECT * FROM reiziger";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                String geboortedatum = rs.getString("geboortedatum").toString();

                if (tussenvoegsel == null){
                    tussenvoegsel = "";
                } else {
                    tussenvoegsel = tussenvoegsel + " ";
                }

                System.out.println("#" + id + ": " + voorletters + ". " + tussenvoegsel + "" + achternaam + " (" + geboortedatum + ")");
            }

            rs.close();
            stmt.close();
        } catch (Exception e){
            System.out.println("Er is een fout opgetreden: " + e.getMessage());
        }

    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
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
        if (gevondenReizigerById != null){
            System.out.println(gevondenReizigerById + "\n");
        } else {
            System.out.println("Geen reiziger met id 1\n");
        }

        //test Delete
        System.out.println("[Test] ReizigerDAO.delete() verwijderd reiziger Sietske");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.print("[Test] Na ReizigerDAO.delete() zijn er nog " + reizigers.size() + " reizigers\n");

        //test of sietske echt is verijderd
        Reiziger verwijderd = rdao.findById(77);
        if (verwijderd == null){
            System.out.println("[Test] Sietske is van de aardbodem verdwenen");
        } else {
            System.out.println("[Test] verdorrie er is iets fout want Sietske is er nog");
        }
    }
}