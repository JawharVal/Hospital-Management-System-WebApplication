<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Hospital Management System</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>

<style>
    @import url('https://fonts.googleapis.com/css?family=Exo:400,700');

    html, body {
        height: 100%;
    }

    .wrapper {
        min-height: 100%;
        position: relative;
    }

    footer {
        position: fixed;
        bottom: 0;
        width: 100%;
    }
    .navbar-custom {
        background-color: #ffffff;
        box-shadow: 0px 4px 8px rgb(255, 255, 255);
        font-family: 'Exo', sans-serif;
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
        background-color: #2e2d2b;
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
        color: #fdd56a;
    }

    .nav-link:hover, .nav-link:hover h2, .nav-link:hover h2 strong {
        color: #fdd56a !important;
    }

    .display-4 {
        white-space: nowrap;
        color: #e5c885;
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
        opacity:.9;
    }

    .card-body {
        padding: 2rem;
    }
    html {
        height:100%;
    }

    body {
        margin:0;
    }

    .bg {
        animation:slide 3s ease-in-out infinite alternate;
        background-image: linear-gradient(-20deg, #e5c885 50%, #000 50%);
        bottom:0;
        left:-100%;
        opacity:.5;
        position:fixed;
        right:-50%;
        top:0;
        z-index:-1;
    }

    .bg2 {
        animation-direction:alternate-reverse;
        animation-duration:4s;
    }

    .bg3 {
        animation-duration:5s;
    }

    .content {
        background-color:rgba(255,255,255,.8);
        border-radius:.25em;
        box-shadow:0 0 .25em rgba(0,0,0,.25);
        box-sizing:border-box;
        left:50%;
        padding:10vmin;
        position:fixed;
        text-align:center;
        top:50%;
        transform:translate(-50%, -50%);
    }

    h1 {
        font-family:monospace;
    }

    @keyframes slide {
        0% {
            transform:translateX(-25%);
        }
        100% {
            transform:translateX(25%);
        }
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
                </li>
            </ul>
        </div>
    </div>
</nav>
<main>
    <section class="hero text-center">
        <div class="container">
            <h1 class="display-4">Hospital Management System</h1>
            <p class="lead">Welcome to the Hospital Management System</p>
        </div>
    </section>

    <section class="container py-5">
        <div class="bg"></div>
        <div class="bg bg2"></div>
        <div class="bg bg3"></div>
        <div class="row justify-content-center">
            <div class="col-12">
                <div class="row">
                    <div class="col-6">
                        <div class="card">
                            <div class="card-body text-center"> <!-- Add text-center class here -->
                                <ul class="nav flex-column">
                                    <li class="nav-item">
                                        <a class="nav-link active" aria-current="page" href="departments.jsp">
                                            <h2><strong>Manage Departments</strong></h2>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="card">
                            <div class="card-body text-center"> <!-- Add text-center class here -->
                                <ul class="nav flex-column">
                                    <li class="nav-item">
                                        <a class="nav-link active" aria-current="page" href="patients.jsp">
                                            <h2><strong>Manage Patients</strong></h2>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="wrapper">
    <jsp:include page="footer.jsp" />
    </div>
</main>
</body>
</html>
