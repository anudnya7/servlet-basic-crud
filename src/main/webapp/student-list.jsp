<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Student Management</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="container">

    <div class="header-row">

        <h1>Student Management</h1>

        <a href="${pageContext.request.contextPath}/student?action=new"
           class="btn btn-primary">
            + Add Student
        </a>

    </div>


    <!-- Success/Error Messages -->

    <c:if test="${param.status == 'added'}">
        <div class="alert alert-success">
            Student added successfully.
        </div>
    </c:if>

    <c:if test="${param.status == 'updated'}">
        <div class="alert alert-success">
            Student updated successfully.
        </div>
    </c:if>

    <c:if test="${param.status == 'deleted'}">
        <div class="alert alert-success">
            Student deleted successfully.
        </div>
    </c:if>

    <c:if test="${param.status == 'error'}">
        <div class="alert alert-error">
            Something went wrong. Please try again.
        </div>
    </c:if>


    <!-- Student Table -->

    <table class="employee-table">

        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Age</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>

        <c:choose>

            <c:when test="${empty studentList}">

                <tr>
                    <td colspan="5" class="empty-row">
                        No students found.
                        Click "Add Student" to create one.
                    </td>
                </tr>

            </c:when>


            <c:otherwise>

                <c:forEach var="student"
                           items="${studentList}">

                    <tr>

                        <td>
                            ${student.id}
                        </td>

                        <td>
                            ${student.name}
                        </td>

                        <td>
                            ${student.email}
                        </td>

                        <td>
                            ${student.age}
                        </td>

                        <td class="actions">

                            <a href="${pageContext.request.contextPath}/student?action=edit&id=${student.id}"
                               class="btn btn-edit">
                                Edit
                            </a>

                            <a href="${pageContext.request.contextPath}/student?action=delete&id=${student.id}"
                               class="btn btn-delete"
                               onclick="return confirm('Delete this student?');">
                                Delete
                            </a>

                        </td>

                    </tr>

                </c:forEach>

            </c:otherwise>

        </c:choose>

        </tbody>

    </table>

</div>

</body>
</html>