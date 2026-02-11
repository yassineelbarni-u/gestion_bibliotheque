package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import metier.entities.Emprunt;

public class EmpruntDaoImpl implements IEmpruntDao {

    @Override
    public Emprunt save(Emprunt emprunt) {
        Connection connection = SingletonConnection.getConnection();
        try {
        	
            // Calculer dateRetourPrevue = dateEmprunt + 7 jrs
            LocalDate dateRetourPrevue = emprunt.getDateEmprunt().plusDays(7);
            emprunt.setDateRetourPrevue(dateRetourPrevue);
            
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO emprunts(livre_id, adherent_id, date_emprunt, date_retour_prevue) VALUES(?, ?, ?, ?)"
            );
            
            ps.setInt(1, emprunt.getLivreId());
            ps.setInt(2, emprunt.getAdherentId());
            ps.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            ps.setDate(4, java.sql.Date.valueOf(dateRetourPrevue));
            ps.executeUpdate();
            
            
            // Recuperer l'ID auto-genere
            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(id) AS MAXID FROM emprunts");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                emprunt.setId(rs.getInt("MAXID"));
            }
            
            // Decrementer le stock du livre
            
            PreparedStatement psStock = connection.prepareStatement("UPDATE livres SET stock = stock - 1 WHERE id = ?");
            psStock.setInt(1, emprunt.getLivreId());
            psStock.executeUpdate();
            
            ps.close();
            ps2.close();
            rs.close();
            psStock.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunt;
    }

    
    @Override
    public List<Emprunt> getEmpruntsEnCours() {
        List<Emprunt> emprunts = new ArrayList<>();
        Connection connection = SingletonConnection.getConnection();
        try {
        	
        	// pas en cours retourne
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM emprunts WHERE date_retour_effective IS NULL ORDER BY date_emprunt DESC"
            );
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                emprunt.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());
                
                // date_retour_effective peut être NULL
                java.sql.Date dateRetourEffective = rs.getDate("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective.toLocalDate());
                }
                
                emprunts.add(emprunt);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getAllEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        Connection connection = SingletonConnection.getConnection();
        try {
        	
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM emprunts ORDER BY id DESC");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                emprunt.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());
                
                java.sql.Date dateRetourEffective = rs.getDate("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective.toLocalDate());
                }
                
                emprunts.add(emprunt);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public Emprunt getEmprunt(int id) {
        Emprunt emprunt = null;
        Connection connection = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM emprunts WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                emprunt = new Emprunt();
                emprunt.setId(rs.getInt("id"));
                emprunt.setLivreId(rs.getInt("livre_id"));
                emprunt.setAdherentId(rs.getInt("adherent_id"));
                emprunt.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                emprunt.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());
                
                java.sql.Date dateRetourEffective = rs.getDate("date_retour_effective");
                if (dateRetourEffective != null) {
                    emprunt.setDateRetourEffective(dateRetourEffective.toLocalDate());
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunt;
    }

    @Override
    public Emprunt retourner(int id) {
    	
        Emprunt emprunt = getEmprunt(id);
        if (emprunt != null && emprunt.isEnCours()) {
            Connection connection = SingletonConnection.getConnection();
            try {
                // Mettre à jour la date de retour effective
                PreparedStatement ps = connection.prepareStatement(
                    "UPDATE emprunts SET date_retour_effective = ? WHERE id = ?"
                );
                ps.setDate(1, java.sql.Date.valueOf(LocalDate.now())); // Date d'aujourd'hui
                ps.setInt(2, id);
                
                ps.executeUpdate();
                
                // Incrémenter le stock du livre
                PreparedStatement psStock = connection.prepareStatement("UPDATE livres SET stock = stock + 1 WHERE id = ?");
                psStock.setInt(1, emprunt.getLivreId());
                psStock.executeUpdate();
                
                //Mise a jour de l'objet Java (Synchronisation)
                emprunt.setDateRetourEffective(LocalDate.now());
                
                ps.close();
                psStock.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return emprunt;
    }
}
