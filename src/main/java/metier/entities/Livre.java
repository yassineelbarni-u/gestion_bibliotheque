package metier.entities;

import java.io.Serializable;

public class Livre implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String titre;
    private String auteur;
    private int stock;

    public Livre() {
        super();
    }

    public Livre(String titre, String auteur, int stock) {
        super();
        this.titre = titre;
        this.auteur = auteur;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", stock=" + stock + "]";
    }
}
