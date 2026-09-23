<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>
        ${empty student ? "Add Student" : "Edit Student"}
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="container narrow">

    <h1>
        ${empty student ? "Add Student" : "Edit Student"}
    </h1>

    <form action="${pageContext.request.contextPath}/student"
          method="post"
          class="employee-form">

        <c:choose>
            <c:when test="${empty student}">
                <input type="hidden"
                       name="action"
                       value="insert">
            </c:when>

            <c:otherwise>
                <input type="hidden"
                       name="action"
                       value="update">

                <input type="hidden"
                       name="id"
                       value="${student.id}">
            </c:otherwise>
        </c:choose>

        <label for="name">Name</label>

        <input type="text"
               id="name"
               name="name"
               value="${student.name}"
               required>


        <label for="email">Email</label>

        <input type="email"
               id="email"
               name="email"
               value="${student.email}"
               required>


        <label for="age">Age</label>

        <input type="number"
               id="age"
               name="age"
               value="${student.age}"
               required>


        <div class="form-actions">

            <button type="submit"
                    class="btn btn-primary">
                ${empty student ? "Add Student" : "Update Student"}
            </button>

            <a href="${pageContext.request.contextPath}/student?action=list"
               class="btn btn-secondary">
                Cancel
            </a>

        </div>

    </form>

</div>

</body>

</html>