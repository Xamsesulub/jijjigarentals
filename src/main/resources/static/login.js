document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  try {
    const response = await fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email, password })
    });

    if (response.ok) {
      const data = await response.json();
      alert("Velkommen!");
      // Lagre token / rolle / redirect til dashboard?
    } else {
      alert("Feil e-post eller passord");
    }
  } catch (error) {
    console.error(error);
    alert("En feil oppstod under innlogging");
  }
});
