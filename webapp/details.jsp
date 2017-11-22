<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User management. Details</title>
</head>
<body>
First name: ${user.firstName}<br>
Last name: ${user.lastName}<br>
<fmt:parseDate value="${user.dateOfBirth}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
Date of birth <fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy" dateStyle="medium"/><br>
<form action="<%=request.getContextPath()%>/details" method="get">
    <input type="submit" value="Ok"  name="okButton" />
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>