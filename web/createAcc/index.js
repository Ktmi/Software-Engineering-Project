function createAccount() {
    var email = document.getElementById("userEmail").value;
    alert(email);
    var username = document.getElementById("userName").value;
    alert(username);
    var password = document.getElementById("userPass").value;
    alert(password);
    var passwordRe = document.getElementById("userPassRe").value;
    alert(passwordRe);

    if (password != passwordRe) {
        alert("\nPassword did not match: Please try again...")
        return false;
    }
}