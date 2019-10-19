<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>BlueJay Login</title>
</head>

<body>

    <body style="background-color:rgb(40, 43, 43);">
        <center>
            <div class="container" style="background-color:#ffffff; width: 30%">
                <h1>&nbsp</h1>
                <img src="data:image/jpeg;base64,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"
                    width="35%" height="35%" alt="#">

                <link rel="stylesheet" href="<c:url value = "/css/ACstyle.css"/>">
                <form action="action_page.php" method="post">
                    <br><label for="uname"><h2>Username</h2></label><br>
                    <input type="text" placeholder="Enter Username" name="uname" required><br><br>

                    <label for="psw"><h2>Password</h2></label><br>
                    <input type="password" placeholder="Enter Password" name="psw" required><br>

                    <button type="submit"><h3>Login</h3></button>
                    <br>
                    <label>
                    <input type="checkbox" checked="checked" name="remember"> Remember me
                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                </label>
                    <span class="psw"> <a href="<c:url value = "/forgotPass"/>">Forgot password?</a></span><br><br>
                    <span class="psw"> New User? <a href="<c:url value = "/createAcc"/>">Create New Account</a></span><br><br>
                    <h1>&nbsp</h1>
            </div>
            </form>
        </center>
        <script src="login.js"></script>
    </body>

</html>