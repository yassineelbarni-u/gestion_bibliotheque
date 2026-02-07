package metier.service;

import java.util.List;

import metier.entities.Emprunt;

public interface IEmpruntService {
	
	Emprunt emprunter(Emprunt emprunt);
	Emprunt retourner(int id);
    List<Emprunt> getEmpruntsEnCours();
    List<Emprunt> getAllEmprunts();
    
}
