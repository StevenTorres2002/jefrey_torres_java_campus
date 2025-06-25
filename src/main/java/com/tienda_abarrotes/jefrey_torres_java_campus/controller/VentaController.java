package com.tienda_abarrotes.jefrey_torres_java_campus.controller;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaRespuestaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.service.ServicioVenta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final ServicioVenta servicioVenta;

    @PostMapping
    public ResponseEntity<VentaRespuestaDTO> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        VentaRespuestaDTO respuesta = servicioVenta.registrarVenta(ventaDTO);
        return ResponseEntity.status(201).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<VentaRespuestaDTO>> obtenerTodas() {
        return ResponseEntity.ok(servicioVenta.obtenerTodasLasVentas());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<VentaRespuestaDTO>> obtenerPorCliente(@PathVariable String idCliente) {
        return ResponseEntity.ok(servicioVenta.obtenerVentasPorCliente(idCliente));
    }

}
