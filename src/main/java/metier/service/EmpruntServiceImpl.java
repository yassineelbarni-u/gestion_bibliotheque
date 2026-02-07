
 package metier.service;

import java.time.LocalDate;
import java.util.List;

import dao.EmpruntDaoImpl;
import dao.IEmpruntDao;
import dao.ILivreDao;
import dao.LivreDaoImpl;
import metier.entities.Emprunt;
import metier.entities.Livre;

public class EmpruntServiceImpl implements IEmpruntService {

     private IEmpruntDao empruntDao = new EmpruntDaoImpl();
    private ILivreDao livreDao = new LivreDaoImpl();

    @Override
    public Emprunt emprunter(Emprunt emprunt) {

        Livre livre = livreDao.getLivre(emprunt.getLivreId());

        if(livre ==null){
            throw new RuntimeException("Livre non trouvé");
        }

        if(livre.getStock() <= 0){
            throw new RuntimeException("Livre en rupture de stock");
        }

        // date d'emprunt

        emprunt.setDateEmprunt(LocalDate.now());

        return empruntDao.save(emprunt);

    }

    @Override
    public Emprunt retourner(int id) {
        return empruntDao.retourner(id);
    }

    @Override
    public List<Emprunt> getEmpruntsEnCours() {
        return empruntDao.getEmpruntsEnCours();
    }

    @Override
    public List<Emprunt> getAllEmprunts() {
        return empruntDao.getAllEmprunts();
    }

    
}