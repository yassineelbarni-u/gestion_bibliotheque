package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.entities.Adherent;

public class AdherentDaoImpl implements IAdherentDao {

    @Override
    public Adherent save(Adherent adherent) {
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO adherents(nom, prenom, email, telephone) VALUES(?, ?, ?, ?)"
            );
            ps.setString(1, adherent.getNom());
            ps.setString(2, adherent.getPrenom());
            ps.setString(3, adherent.getEmail());
            ps.setString(4, adherent.getTelephone());
            ps.executeUpdate();
            
            // Récupérer l'ID auto-généré
            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(id) AS MAXID FROM adherents");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                adherent.setId(rs.getInt("MAXID"));
            }
            ps.close();
            ps2.close();
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherent;
    }

    @Override
    public List<Adherent> getAllAdherents() {
        List<Adherent> adherents = new ArrayList<>();
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM adherents ORDER BY id DESC");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setEmail(rs.getString("email"));
                adherent.setTelephone(rs.getString("telephone"));
                adherents.add(adherent);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }

    @Override
    public Adherent getAdherent(int id) {
        Adherent adherent = null;
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM adherents WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setEmail(rs.getString("email"));
                adherent.setTelephone(rs.getString("telephone"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherent;
    }

    @Override
    public Adherent update(Adherent adherent) {
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE adherents SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id = ?"
            );
            ps.setString(1, adherent.getNom());
            ps.setString(2, adherent.getPrenom());
            ps.setString(3, adherent.getEmail());
            ps.setString(4, adherent.getTelephone());
            ps.setInt(5, adherent.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherent;
    }

    @Override
    public void deleteAdherent(int id) {
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM adherents WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Adherent> adherentsParMotCle(String mc) {
        List<Adherent> adherents = new ArrayList<>();
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM adherents WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ?"
            );
            ps.setString(1, "%" + mc + "%");
            ps.setString(2, "%" + mc + "%");
            ps.setString(3, "%" + mc + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Adherent adherent = new Adherent();
                adherent.setId(rs.getInt("id"));
                adherent.setNom(rs.getString("nom"));
                adherent.setPrenom(rs.getString("prenom"));
                adherent.setEmail(rs.getString("email"));
                adherent.setTelephone(rs.getString("telephone"));
                adherents.add(adherent);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }
}
