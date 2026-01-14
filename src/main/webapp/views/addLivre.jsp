<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un livre</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        form { max-width: 400px; }
        label { display: block; margin-top: 10px; }
        input { width: 100%; padding: 8px; margin-top: 5px; }
        button { margin-top: 20px; padding: 10px 20px; background-color: #4CAF50; color: white; border: none; }
    </style>
</head>
<body>
    <h2>Ajouter un livre</h2>
    
    <form action="../ajouter" method="post">
        <label>Titre:</label>
        <input type="text" name="titre" required>
        
        <label>Auteur:</label>
        <input type="text" name="auteur" required>
        
        <label>Stock:</label>
        <input type="number" name="stock" required>
        
        <button type="submit">Ajouter</button>
    </form>
    
    <br><a href="../livres">Retour à la liste</a>
</body>
</html>
