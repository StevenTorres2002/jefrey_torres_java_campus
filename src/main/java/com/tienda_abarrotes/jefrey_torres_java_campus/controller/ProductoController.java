package com.tienda_abarrotes.jefrey_torres_java_campus.controller;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.ProductoDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.service.ServicioProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ServicioProducto servicioProducto;

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO creado = servicioProducto.crearProducto(productoDTO);
        return ResponseEntity.status(201).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicioProducto.obtenerTodosLosProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioProducto.obtenerProductoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(servicioProducto.actualizarProducto(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioProducto.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<ProductoDTO>> obtenerPorCategoria(@PathVariable String nombreCategoria) {
        return ResponseEntity.ok(servicioProducto.obtenerPorCategoria(nombreCategoria));
    }

}
