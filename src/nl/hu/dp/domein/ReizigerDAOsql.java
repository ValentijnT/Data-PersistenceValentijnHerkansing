package nl.hu.dp.domein;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO {
    private Connection connection;
    private AdresDAO adresDAO;

    public ReizigerDAOsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, reiziger.getid());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, java.sql.Date.valueOf(reiziger.getGeboortedatum()));

            statement.executeUpdate();
            statement.close();

            if(this.adresDAO != null){
                this.adresDAO.save(reiziger.getAdres());
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error bij Reiziger Save: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?"
            );
            statement.setInt(5, reiziger.getid());
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, java.sql.Date.valueOf(reiziger.getGeboortedatum()));

            statement.executeUpdate();
            statement.close();

            if(this.adresDAO != null){
                this.adresDAO.update(reiziger.getAdres());
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error bij Reiziger Update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            if(this.adresDAO != null){
                this.adresDAO.delete(reiziger.getAdres());
            }

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM reiziger WHERE reiziger_id =?"
            );
            statement.setInt(1, reiziger.getid());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error bij Reiziger Delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM reiziger WHERE reiziger_id = ?"
            );
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum").toLocalDate()
                );
                rs.close();
                statement.close();
                return reiziger;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij Reiziger findById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate geboortedatum) {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM reiziger WHERE geboortedatum = ?"
            );
            statement.setDate(1, java.sql.Date.valueOf(geboortedatum));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                reizigers.add(new Reiziger(
                    rs.getInt("reiziger_id"),
                    rs.getString("voorletters"),
                    rs.getString("tussenvoegsel"),
                    rs.getString("achternaam"),
                    rs.getDate("geboortedatum").toLocalDate()
                ));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij Reiziger findByGbdatum: " + e.getMessage());
        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM reiziger"
            );
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                    rs.getInt("reiziger_id"),
                    rs.getString("voorletters"),
                    rs.getString("tussenvoegsel"),
                    rs.getString("achternaam"),
                    rs.getDate("geboortedatum").toLocalDate()
                );
                reizigers.add(reiziger);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij Reiziger findAll: " + e.getMessage());
        }
        return reizigers;
    }
}
