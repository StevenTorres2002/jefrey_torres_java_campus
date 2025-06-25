package com.tienda_abarrotes.jefrey_torres_java_campus.repository;

import com.tienda_abarrotes.jefrey_torres_java_campus.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Obtener ventas por identificador de cliente
    List<Venta> findByIdCliente(String identificadorCliente);
}
