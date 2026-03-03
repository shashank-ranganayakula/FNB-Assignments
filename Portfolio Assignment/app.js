const button = document.getElementById("themeBtn");
const body = document.body;

button.addEventListener("click", function() {
    body.classList.toggle("dark");
});