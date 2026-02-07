package dao;
import metier.entities.Utilisateur;

// une interface definit ce qu'on doit faire
public interface IUtilisateurDao {
	
	//declaration de methode authentifier retourne un objet Utilisateur
    Utilisateur authentifier(String username, String password);
}
