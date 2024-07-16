<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.exohopital.models.Patient" %>

<html>
<head>
    <title>Détail du patient</title>
    <link rel="stylesheet" href="../style.css">
</head>
<body>
<%@include file="header.jsp" %>

<main>
    <div class="card-detail">
        <h2>Détails du patient <%=((Patient) request.getAttribute("patient")).getId() %> :</h2>
        <p><%=((Patient) request.getAttribute("patient")).getLastname() %>
        </p>
        <p><%=((Patient) request.getAttribute("patient")).getFirstname() %>
        </p>
        <p><%=((Patient) request.getAttribute("patient")).getBirthDate() %>
        </p>
        <img src="<%=((Patient) request.getAttribute("patient")).getImagePath()%>" alt="">
    </div>
</main>

<%@include file="footer.jsp" %>
</body>
</html>


