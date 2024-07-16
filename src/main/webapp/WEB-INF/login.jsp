<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="../style.css">
</head>
<body>
<%@include file="header.jsp" %>
<main>
    <h2>Login</h2>
    <form action="<%= request.getContextPath() %>/patient/login" method="post">
        <div>
            <label for="username">Username</label> <br>
            <input type="text" name="username" id="username">
        </div>
        <div>
            <label for="password">Password</label> <br>
            <input type="password" name="password" id="password">
        </div>
        <button type="submit">Login</button>
    </form>
</main>
<%@include file="footer.jsp" %>
</body>
</html>
