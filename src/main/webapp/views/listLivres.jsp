<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des livres</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        h2 { color: #333; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        tr:hover { background-color: #f5f5f5; }
        .btn { padding: 6px 12px; text-decoration: none; margin: 2px; }
        .btn-edit { background-color: #2196F3; color: white; }
        .btn-delete { background-color: #f44336; color: white; }
        .search-form { margin: 20px 0; }
    </style>
</head>
<body>
    <h2>Liste des livres</h2>
    
    <a href="views/addLivre.jsp">Ajouter un livre</a>
    
    <form action="chercher" method="post" class="search-form">
        <input type="text" name="motCle" placeholder="Rechercher (titre ou auteur)">
        <button type="submit">Chercher</button>
    </form>
    
    <table>
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>Auteur</th>
            <th>Stock</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="livre" items="${livres}">
            <tr>
                <td>${livre.id}</td>
                <td>${livre.titre}</td>
                <td>${livre.auteur}</td>
                <td>${livre.stock}</td>
                <td>
                    <a href="editer?id=${livre.id}" class="btn btn-edit">Modifier</a>
                    <a href="supprimer?id=${livre.id}" class="btn btn-delete" 
                       onclick="return confirm('Etes-vous Sur?')">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
