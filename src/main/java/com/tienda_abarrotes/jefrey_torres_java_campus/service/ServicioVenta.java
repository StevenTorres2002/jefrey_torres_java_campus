package com.tienda_abarrotes.jefrey_torres_java_campus.service;

import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaDTO;
import com.tienda_abarrotes.jefrey_torres_java_campus.dto.VentaRespuestaDTO;

import java.util.List;

public interface ServicioVenta {

    VentaRespuestaDTO registrarVenta(VentaDTO ventaDTO);

    List<VentaRespuestaDTO> obtenerTodasLasVentas();

    List<VentaRespuestaDTO> obtenerVentasPorCliente(String identificadorCliente);

}
