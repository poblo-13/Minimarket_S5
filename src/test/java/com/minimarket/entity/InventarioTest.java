package com.minimarket.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class InventarioTest {

    private Inventario inventario;
    private Producto productoMock;
    private LocalDateTime fechaPrueba;

    @BeforeEach
    public void setUp() {
        // Inicializamos el objeto principal
        inventario = new Inventario();
        
        // Creamos un producto simulado
        productoMock = new Producto();
        productoMock.setId(100L);
        productoMock.setNombre("Bebida Cola");

        // Fijamos una fecha exacta para la prueba usando el formato moderno
        fechaPrueba = LocalDateTime.now();
    }

    @Test
    public void testGettersYSetters() {
        // Act: Asignamos todos los valores al inventario
        inventario.setId(15L);
        inventario.setProducto(productoMock);
        inventario.setCantidad(50);
        inventario.setTipoMovimiento("Entrada");
        inventario.setFechaMovimiento(fechaPrueba); // Usamos la variable para asegurar coincidencia exacta
        
        // Assert: Validamos los campos basicos
        assertEquals(15L, inventario.getId(), "El ID debe ser 15L");
        assertEquals(50, inventario.getCantidad(), "La cantidad debe ser 50");
        assertEquals("Entrada", inventario.getTipoMovimiento(), "El tipo de movimiento debe ser 'Entrada'");
        assertEquals(fechaPrueba, inventario.getFechaMovimiento(), "La fecha de movimiento debe coincidir exactamente");
        
        // Assert: Validamos la relacion con Producto
        assertNotNull(inventario.getProducto(), "El producto no debe ser nulo");
        assertEquals(100L, inventario.getProducto().getId(), "El ID del producto asociado debe coincidir");
    }
}