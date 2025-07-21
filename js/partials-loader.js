window.addEventListener("DOMContentLoaded", () => {
    loadPartial("navbar", "partials/navbar.html");
    loadPartial("footer", "partials/footer.html");
});

function loadPartial(id, url) {
    fetch(url)
        .then((response) => {
            if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
            return response.text();
        })
        .then((html) => {
            document.getElementById(id).innerHTML = html;
        })
        .catch((err) => {
            console.error(`Feil ved lasting av ${url}:`, err);
        });
}
