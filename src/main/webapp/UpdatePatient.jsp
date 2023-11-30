<%@ page import="java.sql.SQLException" %>
<%@ page import="org.example.models.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.repositories.DepartmentRepository" %>
<%@ page import="org.example.DI.BeanFactory" %>
<%@ page import="org.example.DAO.PatientDAO" %>
<%@ page import="org.example.models.Patient" %>
<%@ page import="org.example.repositories.PatientRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>




<!DOCTYPE html>
<html>
<head>
  <title>Edit Patient</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css?family=Exo:400,700');

    body {
      font-family: 'Arial', sans-serif;
      background-color: #f8f9fa;
      margin: 0;
      padding: 0;
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
      background-color: #f66301;
      color: #ffffff;
      padding: 1rem 0;
    }

    h1, h2, h3, h4, h5, h6 {
      color: #343a40;
      margin-bottom: 0.5rem;
    }

    .nav-link {
      color: #cb650c;
      font-weight: bold;
    }

    .nav-link:hover {
      color: #cb7f0c;
    }

    .nav-link2 {
      color: #343a40;
      font-weight: bold;
      padding: 10px;
      text-decoration: none;
    }

    .nav-link2:hover {
      color: #cb7f0c;
    }

    .display-5 {
      white-space: nowrap;
      color: #1976D2;
    }

    .display-4 {
      white-space: nowrap;
      color: #ffffff;
    }


    .display-6 {
      white-space: nowrap;
      font-size: 2rem;
    }

    main {
      padding-top: 10px;
    }

    .container {
      max-width: 1200px;
    }

    .section-title {
      color: #1976D2;
      font-size: 1rem;
      font-weight: bold;
      margin-bottom: 2rem;
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
      font-family: 'Exo', sans-serif;
    }
  </style>
</head>
<body>
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
      <h1 class="display-4">Patients Management</h1>
    </div>
  </section>
  <section class="container py-5">
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <script>
          function validateForm() {
            var name = document.forms["myForm"]["name"].value;
            if (!name.match(/^[a-zA-Z\\s]+$/)) {
              alert("Invalid name. Please enter a valid name.");
              return false;
            }
            var age = document.forms["myForm"]["age"].value;
            if (age < 0 || age > 120) {
              alert("Invalid age. Please enter a reasonable age.");
              return false;
            }
            var gender = document.forms["myForm"]["gender"].value.toLowerCase();
            if (gender !== "male" && gender !== "female") {
              alert("Invalid gender. Please enter either 'male' or 'female'.");
              return false;
            }
            return true;
          }
        </script>

        <h2 align="center"><strong>Edit Patient</strong></h2>
        <div class="card">
          <div class="card-body">
            <form action="UpdatePatientServlet" method="POST" onsubmit="return validateForm()" name="myForm">
              <input type="hidden" name="patientId" value="${patientId}">
              <div class="mb-3">
                <label for="name" class="form-label">Name:</label>
                <input type="text" id="name" name="name" value="${patientName}" class="form-control" required>
              </div>
              <div class="mb-3">
                <label for="age" class="form-label">Age:</label>
                <input type="text" id="age" name="age" value="${patientAge}" class="form-control" required>
              </div>
              <div class="mb-3">
                <label for="gender" class="form-label">Gender:</label>
                <select id="gender" name="gender" class="form-control" required>
                  <option value="" disabled selected>Choose Gender</option>
                  <option value="Male" ${patientGender == 'Male' ? 'selected' : ''}>Male</option>
                  <option value="Female" ${patientGender == 'Female' ? 'selected' : ''}>Female</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="department" class="form-label">Department:</label>
                <select id="department" name="department" class="form-select">
                  <%
                    PatientRepository patientRepository = BeanFactory.getBean(PatientRepository.class);
                    int patientId = Integer.parseInt(request.getParameter("patientId"));
                    Patient patient = patientRepository.getPatientById(patientId);
                  %>
                  <%
                    try {
                      DepartmentRepository departmentRepository = BeanFactory.getBean(DepartmentRepository.class);
                      List<Department> departments = departmentRepository.getAllDepartments();
                      for (Department department : departments) {
                  %>
                  <option value="<%= department.getId() %>" <%= patient.getDepartment().getId() == department.getId() ? "selected" : "" %>><%= department.getName() %></option>
                  <%
                      }
                    } catch (SQLException e) {
                      e.printStackTrace();
                    }
                  %>
                </select>

                </select>

                </select>
              </div>
              <div class="mb-3">
                <input type="submit" value="Update" class="btn btn-primary">
              </div>
            </form>

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