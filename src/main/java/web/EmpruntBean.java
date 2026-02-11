package web;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import metier.entities.Adherent;
import metier.entities.Emprunt;
import metier.entities.Livre;
import metier.service.EmpruntServiceImpl;
import metier.service.IEmpruntService;

@Named("empruntBean")
@SessionScoped
public class EmpruntBean implements Serializable {

    private static final long serialVersionUID = 1L;
    

    private IEmpruntService empruntService = new EmpruntServiceImpl();

    // Données
    private Emprunt emprunt = new Emprunt();
    private List<Emprunt> emprunts; //historique complet   
    private List<Emprunt> empruntsEnCours;  // Seulement Livre non rendus
    
    
    public EmpruntBean() {
        refreshListes();
    }

    // pour eviter de repeter le code a chauqe fois que je veux les donnees
    private void refreshListes() {
        this.empruntsEnCours = empruntService.getEmpruntsEnCours();
        this.emprunts = empruntService.getAllEmprunts();
    }


    public String emprunter() {
        try {
            // Appel au Service pour la logique métier
            empruntService.emprunter(emprunt);
            
            // Réinitialiser le formulaire
            emprunt = new Emprunt();
            
            // Mettre à jour les tableaux
            refreshListes();

            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Emprunt enregistré avec succès", null));

            return "empruntsEnCours?faces-redirect=true";

        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                e.getMessage(), null));
            return null;
        }
    }
    
    public String retourner(int id) {
        try {
            empruntService.retourner(id);
            
            refreshListes(); 

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Retour enregistré avec succès", null));

            return "empruntsEnCours?faces-redirect=true";
            
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Erreur lors du retour", null));
            return null;
        }
    }

    public String nouveauEmprunt() {
        emprunt = new Emprunt();
        return "empruntForm?faces-redirect=true";
    }

    

    public String getNomLivre(int livreId) {

    	Livre livre = empruntService.getLivreParId(livreId);
        return livre != null ? livre.getTitre() : "Inconnu";
    }

    public String getNomAdherent(int adherentId) {
        Adherent adherent = empruntService.getAdherentParId(adherentId);
        return adherent != null
            ? adherent.getNom() + " " + adherent.getPrenom()
            : "Inconnu";
    }
    
    public List<Livre> getLivresDisponibles() {
        return empruntService.getAllLivres();
    }

    public List<Adherent> getAllAdherents() {
        return empruntService.getAllAdherents();
    }

    //  Getters / Setters  
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