package com.healthtrack; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Inicializa un usuario antes de cada prueba
        usuario = new Usuario("Alice", 70.0);
    }

    @Test
    @DisplayName("Debería obtener el nombre del usuario correctamente")
    void testGetNombre() {
        assertEquals("Alice", usuario.getNombre());
    }

    @Test
    @DisplayName("Debería obtener el peso inicial del usuario correctamente")
    void testGetPesoInicial() {
        assertEquals(70.0, usuario.getPeso(), 0.001); // Usar delta para doubles
    }

    // --- IMPORTANTE: Esta prueba ha sido comentada/eliminada ---
    // Ya verificamos que el bug está corregido en Usuario.java.
    // Esta prueba estaba diseñada para fallar si el bug estaba corregido.
    // @Test
    // @DisplayName("Debería fallar al actualizar el peso debido al bug (resta 1kg)")
    // void testActualizarPeso_bugEsperado() {
    //     double pesoInicial = usuario.getPeso();
    //     double nuevoPesoIntento = 75.0;
    //     usuario.actualizarPeso(nuevoPesoIntento);
    //     assertEquals(pesoInicial - 1, usuario.getPeso(), 0.001);
    //     assertNotEquals(nuevoPesoIntento, usuario.getPeso(), 0.001);
    // }

    // --- Esta es la prueba que verifica la CORRECCIÓN del bug ---
    // ¡¡¡AHORA ESTÁ DESCOMENTADA Y ACTIVA!!!
    @Test
    @DisplayName("Debería actualizar el peso del usuario correctamente (después de la corrección)")
    void testActualizarPeso_corregido() {
        // Asegúrate de que en Usuario.java, el método actualizarPeso sea: this.peso = nuevoPeso;
        double nuevoPeso = 75.0;
        usuario.actualizarPeso(nuevoPeso);
        assertEquals(nuevoPeso, usuario.getPeso(), 0.001, "El peso debería ser exactamente el nuevo peso ingresado.");
    }
}