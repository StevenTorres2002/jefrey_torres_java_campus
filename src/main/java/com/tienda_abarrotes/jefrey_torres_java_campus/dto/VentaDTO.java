package com.tienda_abarrotes.jefrey_torres_java_campus.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private String idCliente;
    private Long productoId;
    private Integer cantidadComprada;

}
