const dashboard = document.getElementById("dashboard-content");
const statsBox = document.getElementById("stats");
const token = localStorage.getItem("token");

async function fetchData(endpoint) {
    const res = await fetch(`http://localhost:8080/api/${endpoint}`, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    return res.json();
}

function renderTable(headers, rows, onDelete) {
    const thead = headers.map(h => `<th>${h}</th>`).join("") + "<th>🗑️</th>";
    const tbody = rows.map((row, i) =>
        `<tr>${row.map(val => `<td>${val}</td>`).join("")}
         <td><button onclick="${onDelete.name}('${row[0]}')">🗑️</button></td></tr>`
    ).join("");

    return `<table><thead><tr>${thead}</tr></thead><tbody>${tbody}</tbody></table>`;
}

async function deleteUser(id) {
    await fetch(`http://localhost:8080/api/admin/users/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    });
    loadUsers();
}

async function deleteProperty(id) {
    await fetch(`http://localhost:8080/api/admin/properties/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    });
    loadProperties();
}

async function deleteBooking(id) {
    await fetch(`http://localhost:8080/api/admin/bookings/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    });
    loadBookings();
}

async function loadUsers() {
    const users = await fetchData("admin/users");
    const rows = users.map(u => [u.id, u.name, u.email, u.role]);
    dashboard.innerHTML = "<h2>Brukere</h2>" +
        renderTable(["ID", "Navn", "E-post", "Rolle"], rows, deleteUser);
}

async function loadProperties() {
    const props = await fetchData("admin/properties");
    const rows = props.map(p => [p.id, p.title, p.location, p.type, `${p.pricePerNight} kr`]);
    dashboard.innerHTML = "<h2>Eiendommer</h2>" +
        renderTable(["ID", "Tittel", "Lokasjon", "Type", "Pris"], rows, deleteProperty);
}

async function loadBookings() {
    const bookings = await fetchData("admin/bookings");
    const rows = bookings.map(b => [
        b.id,
        b.user.name,
        b.property.title,
        b.status,
        `${b.startDate} → ${b.endDate}`
    ]);
    dashboard.innerHTML = "<h2>Bookinger</h2>" +
        renderTable(["ID", "Bruker", "Bolig", "Status", "Datoer"], rows, deleteBooking);
}

async function loadStats() {
    const [users, properties, bookings] = await Promise.all([
        fetchData("admin/users"),
        fetchData("admin/properties"),
        fetchData("admin/bookings")
    ]);

    statsBox.innerHTML = `
        <div class="stat"><h3>${users.length}</h3><p>Brukere</p></div>
        <div class="stat"><h3>${properties.length}</h3><p>Eiendommer</p></div>
        <div class="stat"><h3>${bookings.length}</h3><p>Bookinger</p></div>
    `;
}

// Kjør statene med én gang
loadStats();
