package web;

import java.io.Serializable;
import java.util.List;

import dao.AdherentDaoImpl;
import dao.EmpruntDaoImpl;
import dao.IAdherentDao;
import dao.IEmpruntDao;
import dao.ILivreDao;
import dao.LivreDaoImpl;
import metier.entities.Emprunt;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("dashboardBean")
@RequestScoped
public class DashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private ILivreDao livreDao = new LivreDaoImpl();
    private IAdherentDao adherentDao = new AdherentDaoImpl();
    private IEmpruntDao empruntDao = new EmpruntDaoImpl();

    private int nbLivres;
    private int nbAdherents;
    private int nbEmpruntsEnCours;
    private int nbEmpruntsTotal;

    public DashboardBean() {
    	
        List<?> livres = livreDao.getAllLivres();
        this.nbLivres = (livres != null) ? livres.size() : 0;

        List<?> adherents = adherentDao.getAllAdherents();
        this.nbAdherents = (adherents != null) ? adherents.size() : 0;

        
        List<Emprunt> listeComplete = empruntDao.getAllEmprunts();
        if (listeComplete != null) {
            this.nbEmpruntsTotal = listeComplete.size();
            
            this.nbEmpruntsEnCours = 0;
            for (Emprunt e : listeComplete) {
                if (e.getDateRetourEffective() == null) {
                    this.nbEmpruntsEnCours++;
                    
                }
            }
        } else {
            this.nbEmpruntsTotal = 0;
            this.nbEmpruntsEnCours = 0;
        }
    }
 
    
    // Getters utilise par JSF
    public int getNbLivres() { return nbLivres; }
    public int getNbAdherents() { return nbAdherents; }
    public int getNbEmpruntsEnCours() { return nbEmpruntsEnCours; }
    public int getNbEmpruntsTotal() { return nbEmpruntsTotal; }
}