package nl.hu.dp;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
        try{
            Connection connection = getConnection();
            ReizigerDAOsql reizigerDAOsql = new ReizigerDAOsql(connection);

            List<Reiziger> reizigers = reizigerDAOsql.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            for (Reiziger r : reizigers) {
                System.out.println(r);
            }
            System.out.println();

            // Maak een nieuwe reiziger aan en persisteer deze in de database
            LocalDate gbdatum = LocalDate.of(1981,3,14);
            Reiziger sietske = new Reiziger(77, "S", "", "Boers", gbdatum);
            System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            reizigerDAOsql.save(sietske);
            reizigers = reizigerDAOsql.findAll();
            System.out.println(reizigers.size() + " reizigers\n");

            closeConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        String url =
                "jdbc:postgresql://localhost/ovchip?user=postgres&password=geheim";
        connection = DriverManager.getConnection(url);
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void testConnection() throws SQLException {
        getConnection();
        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        while (set != null && set.next()) {
            String reiziger_id = set.getString("reiziger_id");
            String voorletters = set.getString("voorletters");
            String tussenvoegsel = set.getString("tussenvoegsel");
            String achternaam = set.getString("achternaam");
            String geboortedatum = set.getString("geboortedatum");

            if(tussenvoegsel == null){
                tussenvoegsel = "";
            } else {
                tussenvoegsel += " ";
            }

            System.out.println("#"+reiziger_id+": "+voorletters+". "+tussenvoegsel+achternaam+" ("+geboortedatum+")");
        }
        closeConnection();
    }

}
