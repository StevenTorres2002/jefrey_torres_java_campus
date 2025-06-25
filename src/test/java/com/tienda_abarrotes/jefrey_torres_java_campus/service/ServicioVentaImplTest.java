package com.tienda_abarrotes.jefrey_torres_java_campus.service;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaRespuestaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.model.Producto;
import com.tienda_abarrotes.jefrey_torres_java_campus.model.Venta;
import com.tienda_abarrotes.jefrey_torres_java_campus.repository.ProductoRepository;
import com.tienda_abarrotes.jefrey_torres_java_campus.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioVentaImplTest {

    private ProductoRepository productoRepository;
    private VentaRepository ventaRepository;
    private ServicioVentaImpl servicioVenta;


    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        ventaRepository = mock(VentaRepository.class);
        servicioVenta = new ServicioVentaImpl(ventaRepository, productoRepository);
    }

    @Test
    void registrarVenta_actualizaStockYRetornaVenta() {
        // Arrange
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setCantidadEnStock(10);
        producto.setPrecioUnitario(5.0);

        VentaDTO ventaDTO = new VentaDTO("cliente1", 1L, 2);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> {
            Venta v = inv.getArgument(0);
            v.setId(100L);
            return v;
        });

        // Act
        VentaRespuestaDTO respuesta = servicioVenta.registrarVenta(ventaDTO);

        // Assert
        assertNotNull(respuesta);
        assertEquals("cliente1", respuesta.getIdCliente());
        assertEquals(2, respuesta.getCantidadComprada());
        assertEquals(10.0, respuesta.getTotalVenta());
        verify(productoRepository).save(any(Producto.class));
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    void obtenerTodasLasVentas_devuelveListaDeVentas() {
        // Arrange
        Venta venta1 = new Venta();
        venta1.setId(1L);
        venta1.setIdCliente("cliente1");
        venta1.setCantidadComprada(2);
        venta1.setTotalVenta(10.0);
        venta1.setProducto(new Producto() {{ setId(1L); }});

        Venta venta2 = new Venta();
        venta2.setId(2L);
        venta2.setIdCliente("cliente2");
        venta2.setCantidadComprada(1);
        venta2.setTotalVenta(5.0);
        venta2.setProducto(new Producto() {{ setId(2L); }});

        when(ventaRepository.findAll()).thenReturn(List.of(venta1, venta2));

        // Act
        List<VentaRespuestaDTO> resultado = servicioVenta.obtenerTodasLasVentas();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("cliente1", resultado.get(0).getIdCliente());
        assertEquals("cliente2", resultado.get(1).getIdCliente());
    }

    @Test
    void obtenerVentasPorCliente_devuelveSoloVentasDelCliente() {
        // Arrange
        Venta venta1 = new Venta();
        venta1.setId(1L);
        venta1.setIdCliente("cliente1");
        venta1.setCantidadComprada(3);
        venta1.setTotalVenta(15.0);
        venta1.setProducto(new Producto() {{ setId(1L); }});

        when(ventaRepository.findByIdCliente("cliente1")).thenReturn(List.of(venta1));

        // Act
        List<VentaRespuestaDTO> resultado = servicioVenta.obtenerVentasPorCliente("cliente1");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("cliente1", resultado.get(0).getIdCliente());
        assertEquals(15.0, resultado.get(0).getTotalVenta());
    }

}
