<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Department</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
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
        font-family: 'Exo', sans-serif;
    }

</style>
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
            <h1 class="display-4">Departments Management</h1>
        </div>
    </section>
    <section class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <h2 align="center"><strong>Add Department</strong></h2>
                <div class="card">
                    <div class="card-body">
                <form action="addDepartment" method="post">
                    <div class="mb-3">
                        <label for="name" class="form-label">Department Name:</label>
                        <input type="text" id="name" name="name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <input type="submit" value="Submit" class="btn btn-primary">
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
