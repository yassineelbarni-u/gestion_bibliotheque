package web;

import java.io.Serializable;
import java.util.List;

import dao.IAdherentDao;
import dao.AdherentDaoImpl;
import dao.ILivreDao;
import dao.LivreDaoImpl;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import metier.entities.Adherent;
import metier.entities.Emprunt;
import metier.entities.Livre;
import metier.service.IEmpruntService;
import metier.service.EmpruntServiceImpl;

@Named("empruntBean")
@SessionScoped
public class EmpruntBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private IEmpruntService empruntService = new EmpruntServiceImpl();

    private ILivreDao livreDao = new LivreDaoImpl();
    private IAdherentDao adherentDao = new AdherentDaoImpl();


    private Emprunt emprunt = new Emprunt();
    private List<Emprunt> emprunts;
    private List<Emprunt> empruntsEnCours;

    public EmpruntBean() {
        empruntsEnCours = empruntService.getEmpruntsEnCours();
        emprunts = empruntService.getAllEmprunts();
    }


    public String emprunter() {
        try {
            empruntService.emprunter(emprunt);

            emprunt = new Emprunt();
            empruntsEnCours = empruntService.getEmpruntsEnCours();

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
        empruntService.retourner(id);
        empruntsEnCours = empruntService.getEmpruntsEnCours();

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Retour enregistré avec succès", null));

        return "empruntsEnCours?faces-redirect=true";
    }

    public String nouveauEmprunt() {
        emprunt = new Emprunt();
        return "empruntForm?faces-redirect=true";
    }

    public String getNomLivre(int livreId) {
        Livre livre = livreDao.getLivre(livreId);
        return livre != null ? livre.getTitre() : "Inconnu";
    }

    public String getNomAdherent(int adherentId) {
        Adherent adherent = adherentDao.getAdherent(adherentId);
        return adherent != null
            ? adherent.getNom() + " " + adherent.getPrenom()
            : "Inconnu";
    }

    public List<Livre> getLivresDisponibles() {
        return livreDao.getAllLivres();
    }

    public List<Adherent> getAllAdherents() {
        return adherentDao.getAllAdherents();
    }

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
