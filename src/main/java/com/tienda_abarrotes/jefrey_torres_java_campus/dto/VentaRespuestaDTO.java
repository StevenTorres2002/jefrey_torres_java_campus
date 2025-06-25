package com.tienda_abarrotes.jefrey_torres_java_campus.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaRespuestaDTO {

    private Long id;
    private String idCliente;
    private String nombreProducto;
    private Integer cantidadComprada;
    private Double totalVenta;
    private LocalDate fechaVenta;

}
