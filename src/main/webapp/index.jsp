<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Blue Jay</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>

<body>
    <%@ include file="navbar.jsp" %>
    <main class = "w3-container">
            <h2 class="w3-center">Welcome to BlueJay!</h2>
            <br>
            <div class="w3-row-padding">
                <div class="w3-col w3-third w3-hover-shadow w3-blue">
                    <header class="w3-container w3-blue"><h1>Most Viewed Posts</h1></header>
                    <div class="w3-container w3-white">
                        <p>POST 1</p>
                        <p>POST 2</p>
                        <p>POST 3</p>
                    </div>
                </div>
                <div class="w3-col w3-third">
                    <div class="w3-container w3-hover-shadow">
                        <header class="w3-panel w3-blue"><h1>Most Liked Posts</h1></header>
                        <div class="w3-panel w3-white">
                            <p>POST 1</p>
                            <p>POST 2</p>
                            <p>POST 3</p>
                        </div>
                    </div>
                </div>
                <div class="w3-col w3-third w3-hover-shadow w3-blue">
                    <header class="w3-container"><h1>Recent Posts</h1></header>
                    <div class="w3-container w3-white"><p>POST 1</p></div>
                    <div class="w3-container w3-white"><p>POST 2</p></div>
                    <div class="w3-container w3-white"><p>POST 3</p></div>
                </div>
            </div>
    </main>
</body>

</html>