package web;

import java.io.Serializable;
import java.util.List;

import dao.ILivreDao;
import dao.LivreDaoImpl;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import metier.entities.Livre;

@Named("livreBean")
@SessionScoped
public class LivreBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ILivreDao livreDao = new LivreDaoImpl();
    private Livre livre = new Livre();
    private List<Livre> livres;
    private String motCle;
    
    public LivreBean() {
        livres = livreDao.getAllLivres();
    }
    
    public String afficherLivres() {
        livres = livreDao.getAllLivres();
        return "livres?faces-redirect=true";
    }
    
    public String enregistrer() {
        if (livre.getId() == 0) {
            livreDao.save(livre);
        } else {
            livreDao.update(livre);
        }
        livre = new Livre();
        livres = livreDao.getAllLivres();
        return "livres?faces-redirect=true";
    }
    
    public String ajouter() {
        livreDao.save(livre);
        livre = new Livre();
        livres = livreDao.getAllLivres();
        return "livres?faces-redirect=true";
    }
    
    public String editer(int id) {
        livre = livreDao.getLivre(id);
        return "livreForm?faces-redirect=true";
    }
    
    public String modifier() {
        livreDao.update(livre);
        livre = new Livre();
        livres = livreDao.getAllLivres();
        return "livres?faces-redirect=true";
    }
    
    public String supprimer(int id) {
        livreDao.deleteLivre(id);
        livres = livreDao.getAllLivres();
        return "livres?faces-redirect=true";
    }
    

    public String chercher() {
        if (motCle != null && !motCle.isEmpty()) {
            livres = livreDao.livresParMotCle(motCle);
        } else {
            livres = livreDao.getAllLivres();
        }
        return "livres?faces-redirect=true";
    }
    

    public String nouveauLivre() {
        livre = new Livre();
        return "livreForm?faces-redirect=true";
    }
    

    
    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }

    public String getMotCle() {
        return motCle;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }
}
