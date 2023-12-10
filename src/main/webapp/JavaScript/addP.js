// script.js
function validateForm() {
    var name = document.forms["myForm"]["name"].value;
    if (!name.match(/^[a-zA-Z\s]+$/)) {
        alert("Invalid name. Please enter a valid name.");
        return false;
    }
    var age = document.forms["myForm"]["age"].value;
    if (age < 0 || age > 120) {
        alert("Invalid age. Please enter a reasonable age.");
        return false;
    }
    var gender = document.forms["myForm"]["gender"].value.toLowerCase();
    if (gender !== "male" && gender !== "female") {
        alert("Invalid gender. Please enter either 'male' or 'female'.");
        return false;
    }
    return true;
}
