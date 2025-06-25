package com.tienda_abarrotes.jefrey_torres_java_campus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long id;
    private String nombre;
    private String sku;
    private Double precioUnitario;
    private Integer cantidadEnStock;
    private String nombreCategoria;

}
