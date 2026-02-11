package web;

import java.io.Serializable;
import java.util.List;

import dao.IAdherentDao;
import dao.AdherentDaoImpl;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import metier.entities.Adherent;

@Named("adherentBean")
@SessionScoped
public class AdherentBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    private IAdherentDao adherentDao = new AdherentDaoImpl();
    
    private Adherent adherent = new Adherent();
    private List<Adherent> adherents;
    private String motCle;
    
    public AdherentBean() {
    	
        adherents = adherentDao.getAllAdherents();
    }
    
    
    public String afficherAdherents() {
        adherents = adherentDao.getAllAdherents();
        return "adherents?faces-redirect=true";
    }
    
   
    
    
    public String enregistrer() {
        if (adherent.getId() == 0) {
            adherentDao.save(adherent);
        } else {
            adherentDao.update(adherent);
        }
        adherent = new Adherent();
        adherents = adherentDao.getAllAdherents();
        return "adherents?faces-redirect=true";
    }
    
    public String editer(int id) {
        adherent = adherentDao.getAdherent(id);
        return "adherentForm?faces-redirect=true";
    }
    
  
    
    public String supprimer(int id) {
        adherentDao.deleteAdherent(id);
        adherents = adherentDao.getAllAdherents();
        return "adherents?faces-redirect=true";
    }
    
    public String chercher() {
        if (motCle != null && !motCle.isEmpty()) {
            adherents = adherentDao.adherentsParMotCle(motCle);
        } else {
            adherents = adherentDao.getAllAdherents();
        }
        return "adherents?faces-redirect=true";
    }
    
    public String nouveauAdherent() {
        adherent = new Adherent();
        return "adherentForm?faces-redirect=true";
    }
    
    
    // Getters et Setters
    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public List<Adherent> getAdherents() {
        return adherents;
    }

    public void setAdherents(List<Adherent> adherents) {
        this.adherents = adherents;
    }

    public String getMotCle() {
        return motCle;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }
}
