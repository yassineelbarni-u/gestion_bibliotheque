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
            ps.setString(2, password); // En production: utiliser hashage (bcrypt/SHA256)
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setUsername(rs.getString("username"));
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
