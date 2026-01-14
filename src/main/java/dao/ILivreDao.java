package dao;

import java.util.List;
import metier.entities.Livre;

public interface ILivreDao {
    
    public Livre save(Livre livre);
    
    public List<Livre> livresParMotCle(String mc);
    
    public Livre getLivre(int id);
    
    public Livre update(Livre livre);
    
    public void deleteLivre(int id);
    
    public List<Livre> getAllLivres();
}
