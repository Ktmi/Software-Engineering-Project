<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Create Account</title>
</head>

<body>

    <body style="background-color:rgb(40, 43, 43);">
        <center>
            <div class="container" style="background-color:#ffffff; width: 30%">
                <link rel="stylesheet" href="/assets/ACstyle.css">
                <h1>&nbsp <br> Create an Account</h1>
                <form action="action_page.php" method="post">
                    <br><br><br><label for="uname">
                        <h2>Email</h2>
                    </label><br>
                    <input type="text" placeholder="Enter Email" name="uname" id="userEmail" required><br>

                    <br><label for="uname">
                        <h2>Username</h2>
                    </label><br>
                    <input type="text" placeholder="Enter Username" name="uname" id="userName" required><br>

                    <br><label for="psw">
                        <h2>Password</h2>
                    </label><br>
                    <input type="password" placeholder="Enter Password" name="psw" id="userPass" required><br>

                    <br><label for="psw">
                        <h2>Re-Enter Password</h2>
                    </label><br>
                    <input type="password" placeholder="Re-Enter Password" name="psw" id="userPassRe" required><br>

                    <a href="/createAccount"><button type="submit" onclick="createAccount();">
                            <h3>Submit</h3>
                        </button>
                        <h1>&nbsp</h1>
            </div>
            </form>
        </center>
        <script src="index.js"></script>

        <?php
        $servername = "127.0.0.1";
        $username = "root";
        $password = "";

        // Create connection
        $conn = new mysqli($servername, $username, $password);

        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        echo nl2br("Connected successfully\n");

        // Create database
        $sql = "CREATE DATABASE myDB";
        if ($conn->query($sql) === TRUE) {
            echo "Database created successfully";
        } else {
            echo "Error creating database: " . $conn->error;
        }
        echo nl2br("\n");
        $sql = "CREATE TABLE myDB.users (
        username varchar(40) unique,
        email varchar(255),
        password varchar(255),
        PRIMARY KEY (username)
    );";

        if ($conn->query($sql) === TRUE) {
            echo "Tables created successfully";
        } else {
            echo "Error creating tables: " . $conn->error;
        }
        echo nl2br("\n");
        $sql = "CREATE TABLE myDB.posts (
        postID bigint unique,
        username varchar(40) unique,
        text varchar(255),
        replyTo bigint unique,
        FOREIGN KEY (username) REFERENCES users(username),
        PRIMARY KEY (postID)
    );";

        if ($conn->query($sql) === TRUE) {
            echo "Tables created successfully";
        } else {
            echo "Error creating tables: " . $conn->error;
        }

        $sql = "INSERT INTO myDB.users (username, email, password)
        VALUES ('acoll094', 'acoll094@fiu.edu', 'abc123')";

        if ($conn->query($sql) === TRUE) {
            echo "New user added successfully";
        } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }

        $sql = "INSERT INTO myDB.posts (postID, username, text, replyTo)
        VALUES ('20191001161123', 'acoll094', 'What the heck man!?', '20190702130829')";

        if ($conn->query($sql) === TRUE) {
            echo "New post created successfully";
        } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }

        $conn->close();
        ?>
    </body>

</html>