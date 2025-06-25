package com.tienda_abarrotes.jefrey_torres_java_campus.service;
import com.tienda_abarrotes.jefrey_torres_java_campus.dto.ProductoDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.model.Producto;
import com.tienda_abarrotes.jefrey_torres_java_campus.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioProductoImplTest {

    private ProductoRepository productoRepository;
    private ServicioProductoImpl servicioProducto;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        servicioProducto = new ServicioProductoImpl(productoRepository);
    }

    @Test
    void crearProducto_guardaYRetornaDTO() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Leche");
        producto.setSku("ABC123");
        producto.setPrecioUnitario(2.5);
        producto.setCantidadEnStock(10);
        producto.setNombreCategoria("Lácteos");

        ProductoDTO dto = new ProductoDTO(1L, "Leche", "ABC123", 2.5, 10, "Lácteos");

        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ProductoDTO resultado = servicioProducto.crearProducto(dto);

        assertNotNull(resultado);
        assertEquals("Leche", resultado.getNombre());
        assertEquals("ABC123", resultado.getSku());
    }

    @Test
    void obtenerTodosLosProductos_retornaLista() {
        Producto p1 = new Producto(1L, "Leche", "SKU1", 2.0, 10, "Lácteos");
        Producto p2 = new Producto(2L, "Pan", "SKU2", 1.0, 20, "Panadería");

        when(productoRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductoDTO> resultado = servicioProducto.obtenerTodosLosProductos();

        assertEquals(2, resultado.size());
        assertEquals("Pan", resultado.get(1).getNombre());
    }

    @Test
    void obtenerProductoPorId_retornaDTO() {
        Producto producto = new Producto(1L, "Jugo", "SKU3", 3.0, 15, "Bebidas");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ProductoDTO resultado = servicioProducto.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Jugo", resultado.getNombre());
    }

    @Test
    void actualizarProducto_actualizaYRetornaDTO() {
        Producto existente = new Producto(1L, "Jugo", "SKU3", 3.0, 15, "Bebidas");
        ProductoDTO actualizado = new ProductoDTO(1L, "Jugo de Naranja", "SKU3", 3.5, 20, "Bebidas");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        ProductoDTO resultado = servicioProducto.actualizarProducto(1L, actualizado);

        assertEquals("Jugo de Naranja", resultado.getNombre());
        assertEquals(3.5, resultado.getPrecioUnitario());
        assertEquals(20, resultado.getCantidadEnStock());
    }

    @Test
    void eliminarProducto_eliminaCorrectamente() {
        doNothing().when(productoRepository).deleteById(1L);
        servicioProducto.eliminarProducto(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void obtenerPorCategoria_filtraCorrectamente() {
        Producto p1 = new Producto(1L, "Queso", "SKU5", 5.0, 8, "Lácteos");

        when(productoRepository.findByNombreCategoria("Lácteos")).thenReturn(List.of(p1));

        List<ProductoDTO> resultado = servicioProducto.obtenerPorCategoria("Lácteos");

        assertEquals(1, resultado.size());
        assertEquals("Queso", resultado.get(0).getNombre());
    }
}
