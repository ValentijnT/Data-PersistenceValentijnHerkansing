package nl.hu.dp;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO{
    private Connection connection = null;

    public ReizigerDAOsql(Connection inConnection) throws SQLException{
        this.connection = inConnection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, reiziger.getReiziger_id());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, java.sql.Date.valueOf(reiziger.getGeboortedatum()));

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?"
            );
            statement.setInt(5, reiziger.getReiziger_id());
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, java.sql.Date.valueOf(reiziger.getGeboortedatum()));

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                "DELETE FROM reiziger WHERE reiziger_id =?"
            );
            statement.setInt(1, reiziger.getReiziger_id());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM reiziger WHERE reiziger_id = ?"
            );
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate date) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM reiziger WHERE geboortedatum = ?"
            );
            statement.setDate(1, java.sql.Date.valueOf(reiziger.getGeboortedatum());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        return null;
    }
}
