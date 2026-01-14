package test;

import dao.LivreDaoImpl;
import metier.entities.Livre;

public class TestDao {

    public static void main(String[] args) {
        
        LivreDaoImpl dao = new LivreDaoImpl();
        
        Livre l1 = dao.save(new Livre("Java ", "oracle", 10));
        Livre l2 = dao.save(new Livre("Python", "Test", 5));
        
        System.out.println("Livre 1 ajoute : " + l1);
        System.out.println("Livre 2 ajoute : " + l2);
        
        System.out.println("\nRecherche 'Java' :");
        dao.livresParMotCle("Java").forEach(System.out::println);
        
        System.out.println("\nTous les livres :");
        dao.getAllLivres().forEach(System.out::println);
    }
}
