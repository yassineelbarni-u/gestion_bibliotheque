package web;

import java.io.Serializable;

import dao.IUtilisateurDao;
import dao.UtilisateurDaoImpl;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import metier.entities.Utilisateur;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    
    private Utilisateur utilisateurConnecte;
    private IUtilisateurDao userDao = new UtilisateurDaoImpl(); //objet de types IUtilisateurDao (la class depend interface User pas implementation de interface couplage faible)
    
    public String login() {
        utilisateurConnecte = userDao.authentifier(username, password);
        
        if (utilisateurConnecte != null) {
            FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("utilisateur", utilisateurConnecte);
            
            return "/livres.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Identifiants incorrects", 
                    "Veuillez vérifier votre nom d'utilisateur et mot de passe"));
            return null;
        }
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
    
    public boolean isLoggedIn() {
        return utilisateurConnecte != null;
    }
    
    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }
}
