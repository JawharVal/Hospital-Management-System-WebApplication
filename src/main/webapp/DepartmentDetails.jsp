<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.models.Patient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="org.example.repositories.PatientRepository" %>
<%@ page import="org.example.DI.BeanFactory" %>

<!DOCTYPE html>
<html>
<head>
    <title>Department Details</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/Dirstyles.css">
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
            <h1 class="display-4">Department Details</h1>
        </div>
    </section>
    <section class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <h2 align="center"><strong>Patients Details</strong></h2>
                <div class="card">
                    <div class="card-body">
                        <table class="table table-bordered table-hover" align="center">
                            <thead>
                            <tr>
                                <th>Patient ID</th>
                                <th>Patient Name</th>
                                <th>Age</th>
                                <th>Gender</th>

                            </tr>
                            </thead>
                            <tbody>
                            <%
                                try {
                                    int departmentId = Integer.parseInt(request.getParameter("departmentId"));
                                    PatientRepository patientRepository = BeanFactory.getBean(PatientRepository.class);
                                    List<Patient> patients = patientRepository.getAllPatients();
                                    for (Patient patient : patients) {
                                        if (patient.getDepartment().getId() == departmentId) {
                            %>
                            <tr>
                                <td><%= patient.getId() %></td>
                                <td><%= patient.getFullName() %></td>
                                <td><%= patient.getAge() %></td>
                                <td><%= patient.getGender() %></td>

                            </tr>
                            <%
                                        }
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
</main>
</body>
</html>