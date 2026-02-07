package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.entities.Livre;

public class LivreDaoImpl implements ILivreDao {

    @Override
    public Livre save(Livre livre) {
    	
    	// ouvrir la connexion a la base des donnees
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO livres(titre, auteur, stock) VALUES(?, ?, ?)"
            );
            // rempissage de 1ere ? par titre 2em par Auteur ..
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getStock());
            ps.executeUpdate();
            
            // auto increment 
            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(id) AS MAXID FROM livres");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                livre.setId(rs.getInt("MAXID"));
            }
            ps.close();
            ps2.close();
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livre;
    }



    @Override
    public Livre getLivre(int id) {
    	//aucun resultat recupere
        Livre livre = null;
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM livres WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                livre = new Livre();
                
                //remplir l'objet avec les donnees (Mapping)
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setStock(rs.getInt("stock"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livre;
    }

    @Override
    public Livre update(Livre livre) {
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE livres SET titre = ?, auteur = ?, stock = ? WHERE id = ?"
            );
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getStock());
            ps.setInt(4, livre.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livre;
    }

    @Override
    public void deleteLivre(int id) {
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM livres WHERE id = ?");
            ps.setInt(1, id); //remplace ? par valeur id
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Livre> getAllLivres() {
        List<Livre> livres = new ArrayList<>();
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM livres ORDER BY id DESC");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Livre livre = new Livre();
                
                //remplir l'objet avec les donnees (Mapping)
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setStock(rs.getInt("stock"));
                livres.add(livre);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
    
    @Override
    public List<Livre> livresParMotCle(String mc) {
        List<Livre> livres = new ArrayList<>();
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM livres WHERE titre LIKE ? OR auteur LIKE ?");
            ps.setString(1, "%" + mc + "%");
            ps.setString(2, "%" + mc + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Livre livre = new Livre();
                //remplissage de objet livre
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setStock(rs.getInt("stock"));
                livres.add(livre);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
}
