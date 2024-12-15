package nl.hu.dp.domein;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection connection;
    private ReizigerDAO reizigerDAO;

    public OVChipkaartDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.setDate(2, java.sql.Date.valueOf(ovChipkaart.getGeldig_tot()));
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setInt(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReiziger().getid());

            statement.executeUpdate();
            statement.close();
            return true;
        }catch (SQLException e){
            System.out.println("Error bij OVChipkaart Save: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?"
            );
            statement.setInt(5, ovChipkaart.getKaart_nummer());
            statement.setDate(1, java.sql.Date.valueOf(ovChipkaart.getGeldig_tot()));
            statement.setInt(2, ovChipkaart.getKlasse());
            statement.setInt(3, ovChipkaart.getSaldo());
            statement.setInt(4, ovChipkaart.getReiziger().getid());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error bij OVChipkaart Update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?"
            );
            statement.setInt(1, ovChipkaart.getKaart_nummer());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error bij OVChipkaart Delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public OVChipkaart findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?"
            );
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int kaart_nummer = rs.getInt("kaart_nummer");
                LocalDate geldig_tot = rs.getDate("geldig_tot").toLocalDate();
                int klasse = rs.getInt("klasse");
                int saldo = rs.getInt("saldo");
                int reizigerId = rs.getInt("reiziger_id");

                reizigerDAO = new ReizigerDAOsql(connection);
                Reiziger reiziger = reizigerDAO.findById(reizigerId);

                OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger);

                rs.close();
                statement.close();
                return ovChipkaart;
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error bij OVChipkaart findById: " + e.getMessage());
        }
        return null;
    }


    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?"
            );
            statement.setInt(1, reiziger.getid());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int kaart_nummer = rs.getInt("kaart_nummer");
                LocalDate geldig_tot = rs.getDate("geldig_tot").toLocalDate();
                int klasse = rs.getInt("klasse");
                int saldo = rs.getInt("saldo");

                OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger);

                ovChipkaarten.add(ovChipkaart);
            }
            rs.close();
            statement.close();
            return ovChipkaarten;
        }catch (SQLException e){
            System.out.println("Error bij OVChipkaart findByReiziger: " + e.getMessage());
        }
        return null;
    }
}
