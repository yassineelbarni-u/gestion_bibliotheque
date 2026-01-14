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
    private ILivreDao livreDao; //couplage faible

    @Override
    public void init() throws ServletException {
        livreDao = new LivreDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // lecture des donnees envoye
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        int stock = Integer.parseInt(request.getParameter("stock"));
        
        Livre livre = new Livre(titre, auteur, stock);
        //Appel le couche DAO
        livreDao.save(livre);
        
        response.sendRedirect("livres");
    }
}
