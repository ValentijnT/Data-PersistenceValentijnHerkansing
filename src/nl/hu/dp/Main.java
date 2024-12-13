package nl.hu.dp;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?";
        String user = "postgres";
        String password = "geheim";

        try{
            Connection db = DriverManager.getConnection(url, user, password);
            System.out.println("Verbinding met database is succesvol");

            Statement stmt = db.createStatement();

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
            db.close();
        } catch (Exception e){
            System.out.println("Er is een fout opgetreden: " + e.getMessage());
        }
    }
}