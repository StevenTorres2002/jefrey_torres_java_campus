package com.tienda_abarrotes.jefrey_torres_java_campus.service;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.ProductoDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.model.Producto;
import com.tienda_abarrotes.jefrey_torres_java_campus.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioProductoImpl implements ServicioProducto {

    private final ProductoRepository productoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ServicioProductoImpl.class);


    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setSku(productoDTO.getSku());
        producto.setPrecioUnitario(productoDTO.getPrecioUnitario());
        producto.setCantidadEnStock(productoDTO.getCantidadEnStock());
        producto.setNombreCategoria(productoDTO.getNombreCategoria());

        Producto productoGuardado = productoRepository.save(producto);

        logger.info("Producto guardado con ID: {}", productoGuardado.getId());
        return new ProductoDTO(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getSku(),
                productoGuardado.getPrecioUnitario(),
                productoGuardado.getCantidadEnStock(),
                productoGuardado.getNombreCategoria()
        );
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getSku(),
                        p.getPrecioUnitario(),
                        p.getCantidadEnStock(),
                        p.getNombreCategoria()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        logger.warn("Producto con ID {} no encontrado", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getSku(),
                producto.getPrecioUnitario(),
                producto.getCantidadEnStock(),
                producto.getNombreCategoria()
        );
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        logger.warn("Producto con ID {} no encontrado para actualizar", productoDTO.getId());
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setSku(productoDTO.getSku());
        productoExistente.setPrecioUnitario(productoDTO.getPrecioUnitario());
        productoExistente.setCantidadEnStock(productoDTO.getCantidadEnStock());
        productoExistente.setNombreCategoria(productoDTO.getNombreCategoria());

        Producto productoGuardado = productoRepository.save(productoExistente);

        logger.info("Producto con ID: {} actualizado", productoGuardado.getId());
        return new ProductoDTO(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getSku(),
                productoGuardado.getPrecioUnitario(),
                productoGuardado.getCantidadEnStock(),
                productoGuardado.getNombreCategoria()
        );
    }

    @Override
    public void eliminarProducto(Long id) {
        logger.info("Producto con ID: {} eliminado", id);
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoDTO> obtenerPorCategoria(String nombreCategoria) {
        return productoRepository.findByNombreCategoria(nombreCategoria).stream()
                .map(p -> new ProductoDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getSku(),
                        p.getPrecioUnitario(),
                        p.getCantidadEnStock(),
                        p.getNombreCategoria()
                ))
                .collect(Collectors.toList());
    }

}
