package metier.entities;
import java.io.Serializable;

public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password; // haché en bcrypt/SHA256 dans la BD
    private String role; // ex: "BIBLIOTHECAIRE"
    
    
	public Utilisateur() {
		super();
	}


	public Utilisateur(int id, String username, String password, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}
    
	
	
    
}
