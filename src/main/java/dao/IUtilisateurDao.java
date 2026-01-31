package dao;
import metier.entities.Utilisateur;

public interface IUtilisateurDao {
    Utilisateur authentifier(String username, String password);
}
