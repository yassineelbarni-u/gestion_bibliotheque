package web;

import java.io.IOException;

import dao.ILivreDao;
import dao.LivreDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.entities.Livre;

@WebServlet("/ajouter")
public class ControleurServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ILivreDao livreDao; //couplage faible on declare une inerface pas une class le servlet ne depend pas de implementation on changer implementation sans change servlet

    @Override
    public void init() throws ServletException {
        livreDao = new LivreDaoImpl(); //on cree un objet DAO une seule fois
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
        String titre = request.getParameter("titre"); //recupere la valeur envoyer par le formulaire
        String auteur = request.getParameter("auteur");
        int stock = Integer.parseInt(request.getParameter("stock"));
        
        Livre livre = new Livre(titre, auteur, stock); 
        //Appel le couche DAO
        livreDao.save(livre);
        
        response.sendRedirect("livres");
    }
}
