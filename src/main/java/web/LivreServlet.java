package web;

import java.io.IOException;
import java.util.List;

import dao.ILivreDao;
import dao.LivreDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.entities.Livre;

@WebServlet(name="ls", urlPatterns = {"/", "/livres", "/chercher", "/supprimer", "/editer", "/modifier"})
public class LivreServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ILivreDao livreDao;

    @Override
    public void init() throws ServletException {
        livreDao = new LivreDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if (path.equals("/") || path.equals("/livres")) {
            List<Livre> livres = livreDao.getAllLivres();
            request.setAttribute("livres", livres);
            request.getRequestDispatcher("views/listLivres.jsp").forward(request, response);
            
        } else if (path.equals("/supprimer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            livreDao.deleteLivre(id);
            response.sendRedirect("livres");
            
        } else if (path.equals("/editer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Livre livre = livreDao.getLivre(id); //recupere livre depuis la BD
            request.setAttribute("livre", livre);
            request.getRequestDispatcher("views/putLivre.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if (path.equals("/chercher")) {
            String mc = request.getParameter("motCle");
            List<Livre> livres = livreDao.livresParMotCle(mc);
            request.setAttribute("livres", livres);
            request.getRequestDispatcher("views/listLivres.jsp").forward(request, response);
            
        } else if (path.equals("/modifier")) {
            int id = Integer.parseInt(request.getParameter("id"));
            //recuperation des nouvelles valeurs
            String titre = request.getParameter("titre");
            String auteur = request.getParameter("auteur");
            int stock = Integer.parseInt(request.getParameter("stock"));
            
            Livre livre = new Livre(titre, auteur, stock);
            livre.setId(id);
            livreDao.update(livre);
            response.sendRedirect("livres");
        }
    }
}
