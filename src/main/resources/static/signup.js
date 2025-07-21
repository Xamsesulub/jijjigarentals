document.getElementById("signupForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const name = document.getElementById("fullName").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;
  const role = document.getElementById("role").value;

  if (password !== confirmPassword) {
    alert("Passordene samsvarer ikke!");
    return;
  }

  try {
    const response = await fetch("/api/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password, role })
    });

    if (response.ok) {
      alert("Bruker registrert! Du kan nå logge inn.");
      window.location.href = "/login.html";
    } else {
      const error = await response.text();
      alert("Feil under registrering: " + error);
    }
  } catch (err) {
    alert("Serverfeil. Prøv igjen senere.");
    console.error(err);
  }
});
