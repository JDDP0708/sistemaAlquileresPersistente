function cambiarCampos() {
    const tipo = document.getElementById("tipo").value;
    const label1 = document.getElementById("label1");
    const label2 = document.getElementById("label2");
    const input1 = document.getElementById("extra1"); 
    const input2 = document.getElementById("extra2");

    if (tipo === "PELICULA") {
        label1.textContent = "Formato (DVD/Digital)";
        input1.name = "formato";
        input1.placeholder = "Ej: BluRay";
        
        label2.textContent = "Duración";
        input2.name = "duracion";
        input2.placeholder = "Ej: 140 min";
    } else {
        label1.textContent = "Plataforma";
        input1.name = "plataforma";
        input1.placeholder = "Ej: PS5/Xbox";
        
        label2.textContent = "Formato (Físico/Digital)";
        input2.name = "formato";
        input2.placeholder = "Ej: Físico";
    }
}