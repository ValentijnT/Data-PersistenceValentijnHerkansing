package nl.hu.dp;

import java.sql.*;

public class Main {
    private static Connection connection;

    public static void main(String[] args) {
        System.out.println("Alle reizigers:");

        try{
            testConnection();
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
