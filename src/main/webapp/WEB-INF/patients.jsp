<%@ page import="org.example.exohopital.models.Patient" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="patient" class="org.example.exohopital.models.Patient" scope="request"/>
<jsp:useBean id="isLogged" class="java.lang.String" scope="request"/>


<html>
<head>
    <title>Patients</title>
    <link rel="stylesheet" href="../style.css">
</head>
<body>

<%@include file="header.jsp" %>

<main>


    <form action="<%= request.getContextPath() %>/patient/add" method="post" enctype="multipart/form-data">
        <h2>Ajouter un patient</h2>
        <div>
            <label for="lastname">Lastname</label> <br>
            <input type="text" name="lastname" id="lastname">
        </div>
        <div>
            <label for="firstname">Firstname</label> <br>
            <input type="text" name="firstname" id="firstname">
        </div>
        <div>
            <label for="birthDate">BirthDate</label> <br>
            <input type="date" name="birthDate" id="birthDate">
        </div>
        <div>
            <label for="imagePath">Upload Image</label> <br>
            <input type="file" name="imagePath" id="imagePath">
        </div>
        <% if (isLogged.equals("test")) { %>

        <button type="submit">Créer patient</button>

<%--TODO: passer par ce lien --%>
        <% } else { %>
        <p class="connexion"><a class="lienconnexion" href="<%= request.getContextPath() %>/patient/login">Connexion</a></p>

        <% } %>
    </form>

    <h2>Liste des patients :</h2>

    <section class="liste">

        <% List<Patient> patients = (List<Patient>) request.getAttribute("patients"); %>
        <% if (patients != null && !patients.isEmpty()) { %>
        <% for (Patient p : patients) { %>
        <div class="card">
            <h3><%= p.getFirstname() %>
            </h3>
            <h3><%= p.getLastname() %>
            </h3>
            <p><a href="<%= request.getContextPath() %>/patient/detail?id=<%= p.getId() %>">Détails du patient</a></p>
        </div>
        <% } %>
        <% } else { %>
        <p>Aucun patient créé.</p>
        <% } %>

    </section>

</main>

<%@include file="footer.jsp" %>


</body>
</html>