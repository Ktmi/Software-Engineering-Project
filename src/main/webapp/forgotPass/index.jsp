<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Create Account</title>
</head>

<body style="background-color:rgb(40, 43, 43);">
    <center>
        <div class="container" style="background-color:#ffffff; width: 30%">
            <link rel="stylesheet" href="<c:url value = "/css/ACstyle.css"/>">
            <h1>&nbsp <br> Forgot Password?</h1>
            <form action="action_page.php" method="post">
                <br><br><br><label for="uname"><h2>Email Recovery</h2></label><br>
                <input type="text" placeholder="Enter Email" name="uname" required><br><br><br>

                <button type="submit"><h3>Submit</h3></button>
                <h1>&nbsp</h1>

        </div>
        </form>
    </center>
</body>

</html>