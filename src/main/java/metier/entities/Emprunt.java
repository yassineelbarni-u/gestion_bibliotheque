package metier.entities;
import java.io.Serializable;
import java.time.LocalDate;

public class Emprunt implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int livreId;
    private int adherentId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue; // = dateEmprunt + 7 jours
    private LocalDate dateRetourEffective; // null si pas encore rendu
    
    // Constructeurs, getters, setters
    
    public Emprunt() {
		super();
	}
      
    
    public Emprunt(int id, int livreId, int adherentId, LocalDate dateEmprunt, LocalDate dateRetourPrevue,
			LocalDate dateRetourEffective) {
		super();
		this.id = id;
		this.livreId = livreId;
		this.adherentId = adherentId;
		this.dateEmprunt = dateEmprunt;
		this.dateRetourPrevue = dateRetourPrevue;
		this.dateRetourEffective = dateRetourEffective;
	}
    

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getLivreId() {
		return livreId;
	}


	public void setLivreId(int livreId) {
		this.livreId = livreId;
	}

	public int getAdherentId() {
		return adherentId;
	}


	public void setAdherentId(int adherentId) {
		this.adherentId = adherentId;
	}




	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}


	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}


	public LocalDate getDateRetourPrevue() {
		return dateRetourPrevue;
	}


	public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
		this.dateRetourPrevue = dateRetourPrevue;
	}


	public LocalDate getDateRetourEffective() {
		return dateRetourEffective;
	}


	public void setDateRetourEffective(LocalDate dateRetourEffective) {
		this.dateRetourEffective = dateRetourEffective;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public boolean isEnCours() {
        return dateRetourEffective == null;
    }
    

	public boolean isEnRetard() {
        return isEnCours() && LocalDate.now().isAfter(dateRetourPrevue);
    }


	@Override
	public String toString() {
		return "Emprunt [id=" + id + ", livreId=" + livreId + ", adherentId=" + adherentId + ", dateEmprunt="
				+ dateEmprunt + ", dateRetourPrevue=" + dateRetourPrevue + ", dateRetourEffective="
				+ dateRetourEffective + "]";
	}
	
	
}
