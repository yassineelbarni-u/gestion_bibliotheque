package metier.service;

import java.util.List;
import metier.entities.Adherent;
import metier.entities.Emprunt;
import metier.entities.Livre;

public interface IEmpruntService {
	
    Emprunt emprunter(Emprunt emprunt);
    Emprunt retourner(int id);
    List<Emprunt> getEmpruntsEnCours();
    List<Emprunt> getAllEmprunts();
    
    List<Livre> getAllLivres();
    List<Adherent> getAllAdherents();  
    
    Livre getLivreParId(int id);  
    Adherent getAdherentParId(int id);
    
}