const token = localStorage.getItem("token");

const welcomeMsg = document.getElementById("welcomeMsg");
const bookingList = document.getElementById("bookingList");
const propertyList = document.getElementById("propertyList");
const utleierBookingList = document.getElementById("utleierBookingList");

const sectionBookings = document.getElementById("leietaker-bookings");
const sectionProperties = document.getElementById("utleier-properties");
const sectionUtleierBookings = document.getElementById("utleier-bookings");

async function fetchUser() {
    const res = await fetch("http://localhost:8080/api/users/me", {
        headers: { "Authorization": `Bearer ${token}` }
    });
    return await res.json();
}

async function fetchData(endpoint) {
    const res = await fetch(`http://localhost:8080/api/${endpoint}`, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    return await res.json();
}

function createItem(text) {
    const div = document.createElement("div");
    div.className = "item";
    div.textContent = text;
    return div;
}

async function initDashboard() {
    try {
        const user = await fetchUser();
        welcomeMsg.textContent = `Logget inn som ${user.name} (${user.role})`;

        if (user.role === "leietaker") {
            sectionBookings.classList.remove("hidden");
            const bookings = await fetchData("bookings/mine");
            bookings.forEach(b => {
                const text = `Bolig: ${b.property.title} – ${b.startDate} til ${b.endDate} (${b.status})`;
                bookingList.appendChild(createItem(text));
            });
        }

        if (user.role === "utleier") {
            sectionProperties.classList.remove("hidden");
            sectionUtleierBookings.classList.remove("hidden");

            const properties = await fetchData("properties");
            const myProps = properties.filter(p => p.owner && p.owner.id === user.id);
            myProps.forEach(p => {
                propertyList.appendChild(createItem(`${p.title} (${p.type}) – ${p.location}`));
            });

            const bookings = await fetchData("bookings/utleier");
            bookings.forEach(b => {
                const text = `Forespørsel fra ${b.user.name} på ${b.property.title} – ${b.startDate} til ${b.endDate} (${b.status})`;
                utleierBookingList.appendChild(createItem(text));
            });
        }

    } catch (err) {
        welcomeMsg.textContent = "Kunne ikke laste brukerinfo.";
        console.error(err);
    }
}

initDashboard();
