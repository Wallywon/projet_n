<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Students</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            line-height: 1.6;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .empty-message {
            padding: 20px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 4px;
            color: #666;
            text-align: center;
        }
        .links {
            margin-top: 20px;
        }
        .links a {
            display: inline-block;
            margin-right: 15px;
            color: #0066cc;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>All Students</h1>
        
        <c:choose>
            <c:when test="${empty students}">
                <div class="empty-message">
                    <p>No students found in the database.</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Student Number</th>
                            <th>Birth Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.firstName}</td>
                                <td>${student.lastName}</td>
                                <td>${student.studentNumber}</td>
                                <td>
                                    <fmt:formatDate value="${student.birthDate}" pattern="yyyy-MM-dd" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        
        <div class="links">
            <a href="index.html">Back to Upload</a>
        </div>
    </div>
</body>
</html>