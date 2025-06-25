package com.tienda_abarrotes.jefrey_torres_java_campus.service;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.ProductoDTO;

import java.util.List;

public interface ServicioProducto {

    ProductoDTO crearProducto(ProductoDTO productoDTO);

    List<ProductoDTO> obtenerTodosLosProductos();

    ProductoDTO obtenerProductoPorId(Long id);

    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);

    void eliminarProducto(Long id);

    List<ProductoDTO> obtenerPorCategoria(String nombreCategoria);

}
