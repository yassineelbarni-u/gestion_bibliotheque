package web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import dao.IEmpruntDao;
import dao.EmpruntDaoImpl;
import dao.ILivreDao;
import dao.LivreDaoImpl;
import dao.IAdherentDao;
import dao.AdherentDaoImpl;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import metier.entities.Emprunt;
import metier.entities.Livre;
import metier.entities.Adherent;

@Named("empruntBean")
@SessionScoped
public class EmpruntBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private IEmpruntDao empruntDao = new EmpruntDaoImpl();
    private ILivreDao livreDao = new LivreDaoImpl();
    private IAdherentDao adherentDao = new AdherentDaoImpl();
    
    private Emprunt emprunt = new Emprunt();
    private List<Emprunt> emprunts;
    private List<Emprunt> empruntsEnCours;
    
    public EmpruntBean() {
        empruntsEnCours = empruntDao.getEmpruntsEnCours();
        emprunts = empruntDao.getAllEmprunts();
    }
    
    public String afficherEmprunts() {
        emprunts = empruntDao.getAllEmprunts();
        return "emprunts?faces-redirect=true";
    }
    
    public String afficherEmpruntsEnCours() {
        empruntsEnCours = empruntDao.getEmpruntsEnCours();
        return "empruntsEnCours?faces-redirect=true";
    }
    
    public String emprunter() {
        // Vérifier le stock
        Livre livre = livreDao.getLivre(emprunt.getLivreId());
        if (livre == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Livre introuvable", null));
            return null;
        }
        
        if (livre.getStock() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Stock insuffisant", null));
            return null;
        }
        
        // Définir la date d'emprunt à aujourd'hui
        emprunt.setDateEmprunt(LocalDate.now());
        empruntDao.save(emprunt);
        
        emprunt = new Emprunt();
        empruntsEnCours = empruntDao.getEmpruntsEnCours();
        
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Emprunt enregistré avec succès", null));
        
        return "empruntsEnCours?faces-redirect=true";
    }
    
    public String retourner(int id) {
        empruntDao.retourner(id);
        empruntsEnCours = empruntDao.getEmpruntsEnCours();
        
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Retour enregistré", null));
        
        return "empruntsEnCours?faces-redirect=true";
    }
    
    public String nouveauEmprunt() {
        emprunt = new Emprunt();
        return "empruntForm?faces-redirect=true";
    }
    
    // Méthodes utilitaires pour afficher les noms dans les vues
    public String getNomLivre(int livreId) {
        Livre livre = livreDao.getLivre(livreId);
        return livre != null ? livre.getTitre() : "Inconnu";
    }
    
    public String getNomAdherent(int adherentId) {
        Adherent adherent = adherentDao.getAdherent(adherentId);
        return adherent != null ? adherent.getNom() + " " + adherent.getPrenom() : "Inconnu";
    }
    
    public List<Livre> getLivresDisponibles() {
        return livreDao.getAllLivres(); // Filtrer stock > 0 si besoin
    }
    
    public List<Adherent> getAllAdherents() {
        return adherentDao.getAllAdherents();
    }
    
    // Getters et Setters
    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    public List<Emprunt> getEmpruntsEnCours() {
        return empruntsEnCours;
    }

    public void setEmpruntsEnCours(List<Emprunt> empruntsEnCours) {
        this.empruntsEnCours = empruntsEnCours;
    }
}
