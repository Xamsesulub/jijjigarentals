const form = document.getElementById("loadForm");
const gallery = document.getElementById("gallery");

form.addEventListener("submit", async function (e) {
    e.preventDefault();
    const propertyId = document.getElementById("propertyId").value.trim();
    if (!propertyId) return;

    gallery.innerHTML = "Laster bilder...";

    try {
        const res = await fetch(`http://localhost:8080/api/properties/${propertyId}`);
        if (!res.ok) throw new Error("Eiendom ikke funnet");
        const property = await res.json();

        const imgRes = await fetch(`http://localhost:8080/api/images/${propertyId}`);
        const images = await imgRes.json();

        gallery.innerHTML = "";
        if (images.length === 0) {
            gallery.innerHTML = "<p>Ingen bilder funnet for denne eiendommen.</p>";
        } else {
            images.forEach(img => {
                const imgEl = document.createElement("img");
                imgEl.src = img.url;
                gallery.appendChild(imgEl);
            });
        }

    } catch (err) {
        gallery.innerHTML = `<p>Feil: ${err.message}</p>`;
    }
});
