package nl.hu.dp.domein;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{
    private Connection connection;
    private ReizigerDAO reizigerDAO;

    public AdresDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getid());

            statement.executeUpdate();
            statement.close();
            return true;
        }catch (SQLException e){
            System.out.println("Error bij Adres Save: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?"
            );
            statement.setInt(6, adres.getId());
            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getReiziger().getid());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error bij Adres Update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM adres WHERE adres_id =?"
            );
            statement.setInt(1, adres.getId());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error bij Adres Delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM adres WHERE adres_id = ?"
            );
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int adresId = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reizigerId = rs.getInt("reiziger_id");

                reizigerDAO = new ReizigerDAOsql(connection);
                Reiziger reiziger = reizigerDAO.findById(reizigerId);

                Adres adres = new Adres(adresId, postcode, huisnummer, straat, woonplaats, reiziger);

                rs.close();
                statement.close();
                return adres;
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij Adres findById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM adres WHERE reiziger_id = ?"
            );
            statement.setInt(1, reiziger.getid());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int adresId = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");

                Adres adres = new Adres(adresId, postcode, huisnummer, straat, woonplaats, reiziger);

                rs.close();
                statement.close();
                return adres;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij Adres findByReiziger: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM adres"
            );
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int adresId = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reizigerId = rs.getInt("reiziger_id");

                reizigerDAO = new ReizigerDAOsql(connection);
                Reiziger reiziger = reizigerDAO.findById(reizigerId);

                Adres adres = new Adres(adresId, postcode, huisnummer, straat, woonplaats, reiziger);
                adressen.add(adres);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij Adres findByAll: " + e.getMessage());
        }
        return adressen;
    }

}
