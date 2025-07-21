// Dynamisk last inn navbar og footer
window.addEventListener("DOMContentLoaded", () => {
    loadComponent("navbar.html", "#navbar");
    loadComponent("footer.html", "#footer");

    const searchInput = document.querySelector(".search-box input");
    const searchBtn = document.querySelector(".search-box button");

    if (searchBtn) {
        searchBtn.addEventListener("click", () => {
            const query = searchInput.value.trim();
            if (query) {
                window.location.href = `/search.html?query=${encodeURIComponent(query)}`;
            }
        });
    }
});

function loadComponent(file, target) {
    fetch(file)
        .then(res => {
            if (!res.ok) throw new Error(`HTTP error! ${res.status}`);
            return res.text();
        })
        .then(data => {
            document.querySelector(target).innerHTML = data;
        })
        .catch(err => console.error("Component loading failed:", err));
}
