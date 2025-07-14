const form = document.getElementById("uploadForm");
const preview = document.getElementById("preview");
const responseBox = document.getElementById("response");

document.getElementById("file").addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.innerHTML = `<img src="${e.target.result}" alt="preview">`;
        };
        reader.readAsDataURL(file);
    }
});

form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const propertyId = document.getElementById("propertyId").value;
    const file = document.getElementById("file").files[0];

    if (!propertyId || !file) return;

    const formData = new FormData();
    formData.append("file", file);

    const token = localStorage.getItem("token"); // JWT token, hvis du bruker auth

    try {
        const res = await fetch(`http://localhost:8080/api/properties/${propertyId}/images`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            body: formData
        });

        const text = await res.text();
        responseBox.textContent = text;
        form.reset();
        preview.innerHTML = "";
    } catch (err) {
        responseBox.textContent = "Opplasting feilet.";
        console.error(err);
    }
});
