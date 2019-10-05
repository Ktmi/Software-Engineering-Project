<!DOCTYPE html>
<html lang="en">

<head>
    <?php
        if(!isset($_COOKIE["login"])){ ?>     
            <meta http-equiv="Refresh" content="0; url=/login"/>
        <?php }
    ?> 
    <meta charset="utf-8">
    <title>Blue Jay</title>
    <link rel="stylesheet" href="/assets/style.css">
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>

<body>
    <?php include('navbar.php'); ?>
    <main>
        <center>
            <h2>Welcome to BlueJay!</h2><br>
        </center>
        <div class="row" style="background-color: white; width: 100%; border:2px solid black;">
            <form action="action_page.php" method="post">
                    <div class="column" style="border:1px solid black;">
                    <center>Most Viewed Posts</center><br>
                &nbsp <br>
                &nbsp <br>
                <center>POST 1</center>
                &nbsp <br>
                &nbsp <br>
                <center>POST 2</center>
                &nbsp <br>
                &nbsp <br>
                <center>POST 3</center>
                &nbsp <br>
                &nbsp <br>
                    </div>
                    <div class="column" style="border:1px solid black;">
                    <center>Most Liked Posts</center><br>
                    &nbsp <br>
                &nbsp <br>
                <center>POST 1</center>
                &nbsp <br>
                &nbsp <br>
                <center>POST 2</center>
                &nbsp <br>
                &nbsp <br>
                <center>POST 3</center>
                &nbsp <br>
                &nbsp <br>
                    </div>
                    <div class="column" style="border:1px solid black;">
                    <center>Recent Posts</center><br>
                    &nbsp <br>
                &nbsp <br>
                <center>POST 1</center>
                &nbsp <br>
                &nbsp <br>
                <center>POST 2</center>
                &nbsp <br>
                &nbsp <br>
                <center>POST 3</center>
                &nbsp <br>
                &nbsp <br>
                    </div>
                </div>
    </main>

</body>

</html>