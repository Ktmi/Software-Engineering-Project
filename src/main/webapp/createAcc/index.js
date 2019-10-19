function createAccount() {
    var xhttp = new XMLHttpRequest();
    var formData = document.getElementById("createAccountForm");
    xhttp.open("POST", "/bluejay-server/test3", true);
    xhttp.send(formData);
}