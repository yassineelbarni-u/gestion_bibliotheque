package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import metier.entities.Utilisateur;

public class UtilisateurDaoImpl implements IUtilisateurDao {

    @Override
    public Utilisateur authentifier(String username, String password) {
        Utilisateur utilisateur = null;
        Connection connection = SingletonConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM utilisateurs WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                utilisateur = new Utilisateur();
                
                // Mapping manuel BD  -> Objet chaque ligne de table dans la base des donnees devient  un objet 
                utilisateur.setId(rs.getInt("id"));  
                utilisateur.setUsername(rs.getString("username")); //je pendre la valeur de column Username et stock dans le variable userName de class Utilisateur
                utilisateur.setPassword(rs.getString("password"));
                utilisateur.setRole(rs.getString("role"));
            }
            ps.close();
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }
}
