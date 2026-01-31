package dao;
import java.util.List;
import metier.entities.Emprunt;

public interface IEmpruntDao {
    Emprunt save(Emprunt emprunt);
    List<Emprunt> getEmpruntsEnCours(); // WHERE dateRetourEffective IS NULL
    List<Emprunt> getAllEmprunts();
    Emprunt getEmprunt(int id);
    Emprunt retourner(int id); // met dateRetourEffective = NOW(), incremente stock livre
}
