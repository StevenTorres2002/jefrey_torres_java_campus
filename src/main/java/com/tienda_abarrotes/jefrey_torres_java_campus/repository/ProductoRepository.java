package com.tienda_abarrotes.jefrey_torres_java_campus.repository;

import com.tienda_abarrotes.jefrey_torres_java_campus.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Filtrar por nombre de categoría
    List<Producto> findByNombreCategoria(String nombreCategoria);

}
