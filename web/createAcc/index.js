function createAccount() {
    var email = document.getElementById("userEmail").value;
    var username = document.getElementById("userName").value;
    var password = document.getElementById("userPass").value;
    var passwordRe = document.getElementById("userPassRe").value;

    if (password != passwordRe) {
        alert("\nPassword did not match: Please try again...")
    }

}