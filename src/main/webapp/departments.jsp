<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.models.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="org.example.repositories.DepartmentRepository" %>
<%@ page import="org.example.DI.BeanFactory" %>

<!DOCTYPE html>
<html>
<head>
    <title>Departments</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
<style>
    @import url('https://fonts.googleapis.com/css?family=Exo:400,700');
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 0;
    }

    footer {
        position: fixed;
        bottom: 0;
        width: 100%;
    }
    .navbar-custom {
        background-color: #ffffff;
        box-shadow: 0px 4px 8px rgb(255, 255, 255);
    }

    .navbar-brand {
        color: #343a40;
        font-weight: bold;
        font-size: 1.5rem;
    }

    .navbar-toggler-icon {
        background-color: #ffffff;
    }

    .hero {
        background-color: #1976d2;
        color: #ffffff;
        padding: 1rem 0;
    }

    h1, h2, h3, h4, h5, h6 {
        color: #343a40;
        margin-bottom: 0.5rem;
    }

    .nav-link2 {
        color: #343a40;
        font-weight: bold;
        padding: 10px;
        text-decoration: none;
    }

    .nav-link2:hover {
        color: #1976d2;
    }

    .display-4 {
        white-space: nowrap;
        color: #ffffff;
    }

    main {
        padding-top: 10px;
    }

    .container {
        max-width: 1200px;
    }
    .card {
        border: none;
        border-radius: 10px;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    }

    .card-body {
        padding: 2rem;
    }

    body{
        background: #3399ff;
    }


    .circle{
        position: absolute;
        border-radius: 50%;
        background: white;
        animation: ripple 15s infinite;
        box-shadow: 0px 0px 1px 0px #508fb9;
    }

    .small{
        width: 200px;
        height: 200px;
        left: -100px;
        bottom: -100px;
    }

    .medium{
        width: 400px;
        height: 400px;
        left: -200px;
        bottom: -200px;
    }

    .large{
        width: 600px;
        height: 600px;
        left: -300px;
        bottom: -300px;
    }

    .xlarge{
        width: 800px;
        height: 800px;
        left: -400px;
        bottom: -400px;
    }

    .xxlarge{
        width: 1000px;
        height: 1000px;
        left: -500px;
        bottom: -500px;
    }

    .shade1{
        opacity: 0.2;
    }
    .shade2{
        opacity: 0.5;
    }

    .shade3{
        opacity: 0.7;
    }

    .shade4{
        opacity: 0.8;
    }

    .shade5{
        opacity: 0.9;
    }

    @keyframes ripple {
        0% {
            transform: scale(0.8);
        }
        50% {
            transform: scale(1.2);
        }
        100% {
            transform: scale(0.8);
        }
    }

    @keyframes ripples {
        0% {
            transform: scale(0.8);
        }
        50% {
            transform: scale(1.0);
        }
        100% {
            transform: scale(0.8);
        }
    }

    .btn-primary {
        animation: ripples 5s infinite 0s;
    }
    body{
        font-family: 'Exo', sans-serif;
    }
</style>
</head>
<body>
<div class="ripple-background">
    <div class="circle xxlarge shade1"></div>
    <div class="circle xlarge shade2"></div>
    <div class="circle large shade3"></div>
    <div class="circle medium shade4"></div>
    <div class="circle small shade5"></div>
</div>
<nav class="navbar navbar-expand-lg navbar-custom">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Hospital Management System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link2" href="departments.jsp">Departments</a>
                    <a class="nav-link2" href="patients.jsp">Patients</a>
                    <a class="nav-link2" href="index.jsp">Home</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main>
    <section class="hero text-center">
        <div class="container">
            <h1 class="display-4">Departments Management</h1>
        </div>
    </section>
    <section class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <h2 align="center"><strong>Departments</strong></h2>
                <a href="addDepartment.jsp" class="btn btn-primary">Add Department</a>
                <div class="card">
                    <div class="card-body">
                <table class="table table-bordered table-hover" align="center">
                    <thead>
                    <tr>
                        <th>Department ID</th>
                        <th>Department Name</th>
                        <th>Number of Patients</th>
                        <th>Action</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        try {
                            DepartmentRepository departmentRepository = BeanFactory.getBean(DepartmentRepository.class);
                            List<Department> departments = departmentRepository.getAllDepartments();
                            for (Department department : departments) {
                    %>
                    <tr>
                        <td><%= department.getId() %></td>
                        <td>
                            <a href="DepartmentDetails.jsp?departmentId=<%= department.getId() %>">
                                <%= department.getName() %>
                            </a>
                        </td>
                        <td><%= department.getNumberOfPatients() %></td>
                        <td>
                            <a href="EditDepartmentServlet?departmentId=<%= department.getId() %>" class="btn btn-warning">Edit</a>
                        </td>
                        <td>
                            <a href="DeleteDepartmentServlet?departmentId=<%= department.getId() %>" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this department?')">Delete</a>
                        </td>
                    </tr>
                    <%
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    %>
                    </tbody>
                </table>
                </div>
                </div>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp" />

</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
