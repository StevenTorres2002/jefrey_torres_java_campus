package com.tienda_abarrotes.jefrey_torres_java_campus.service;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaRespuestaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.model.Producto;
import com.tienda_abarrotes.jefrey_torres_java_campus.model.Venta;
import com.tienda_abarrotes.jefrey_torres_java_campus.repository.ProductoRepository;
import com.tienda_abarrotes.jefrey_torres_java_campus.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ServicioVentaImpl implements ServicioVenta {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ServicioVentaImpl.class);

    @Override
    public VentaRespuestaDTO registrarVenta(VentaDTO ventaDTO) {
        logger.warn("Producto con ID {} no encontrado para actualizar", ventaDTO.getProductoId());
        Producto producto = productoRepository.findById(ventaDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getCantidadEnStock() <= ventaDTO.getCantidadComprada()) {
            logger.warn("Stock insuficiente para producto {}", producto.getId());
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // Actualizar el stock del producto
        producto.setCantidadEnStock(producto.getCantidadEnStock() - ventaDTO.getCantidadComprada());
        productoRepository.save(producto);

        // Calcular total
        double total = ventaDTO.getCantidadComprada() * producto.getPrecioUnitario();

        // Crear y guardar venta
        Venta venta = new Venta();
        venta.setProducto(producto);
        venta.setIdCliente(ventaDTO.getIdCliente());
        venta.setCantidadComprada(ventaDTO.getCantidadComprada());
        venta.setFechaVenta(LocalDate.now());
        venta.setTotalVenta(total);

        Venta ventaGuardada = ventaRepository.save(venta);

        logger.info("Venta creada con ID: {}", ventaGuardada.getId());
        return new VentaRespuestaDTO(
                ventaGuardada.getId(),
                ventaGuardada.getIdCliente(),
                producto.getNombre(),
                ventaGuardada.getCantidadComprada(),
                ventaGuardada.getTotalVenta(),
                ventaGuardada.getFechaVenta()
        );
    }

    @Override
    public List<VentaRespuestaDTO> obtenerTodasLasVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(venta -> new VentaRespuestaDTO(
                        venta.getId(),
                        venta.getIdCliente(),
                        venta.getProducto().getNombre(),
                        venta.getCantidadComprada(),
                        venta.getTotalVenta(),
                        venta.getFechaVenta()
                )).collect(Collectors.toList());

    }

    @Override
    public List<VentaRespuestaDTO> obtenerVentasPorCliente(String idCliente) {
        return ventaRepository.findByIdCliente(idCliente)
                .stream()
                .map(venta -> new VentaRespuestaDTO(
                        venta.getId(),
                        venta.getIdCliente(),
                        venta.getProducto().getNombre(),
                        venta.getCantidadComprada(),
                        venta.getTotalVenta(),
                        venta.getFechaVenta()
                )).collect(Collectors.toList());
    }

}
