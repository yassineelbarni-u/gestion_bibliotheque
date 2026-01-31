package dao;
import java.util.List;
import metier.entities.Adherent;

public interface IAdherentDao {
	
    Adherent save(Adherent adherent);
    List<Adherent> getAllAdherents();
    Adherent getAdherent(int id);
    Adherent update(Adherent adherent);
    void deleteAdherent(int id);
    List<Adherent> adherentsParMotCle(String mc);
}
