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
                <link rel="stylesheet" href="<c:url value = "/css/ACstyle.css"/>">
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

                    <link rel="stylesheet" href="<c:url value = "/createAcc"/>"><button type="submit" onclick="createAccount();">
                            <h3>Submit</h3>
                        </button>
                        <h1>&nbsp</h1>
            </div>
            </form>
        </center>
        <script src="index.js"></script>
    </body>

</html>