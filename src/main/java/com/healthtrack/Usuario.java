package com.healthtrack; // Asegúrate de que este paquete coincida con tu estructura de carpetas

public class Usuario {
    private String nombre;
    private double peso;

    public Usuario(String nombre, double peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    // Este es el método 'actualizarPeso' DESCOMENTADO y CORREGIDO
    // Ya no resta 1kg, ahora asigna el 'nuevoPeso' directamente.
    public void actualizarPeso(double nuevoPeso) {
        this.peso = nuevoPeso; // ¡Esta es la línea CORRECTA y ACTIVA!
    }

    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + ", Peso Actual: " + peso + " kg");
    }
}